package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.schedule.ScheduleNameExistsException;
import com.program.itta.common.exception.schedule.ScheduleNotExistsException;
import com.program.itta.common.exception.schedule.ScheduleTimeException;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.mapper.ScheduleMapper;
import com.program.itta.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: itta
 * @description:
 * @author: Mr.Huang
 * @create: 2020-04-25 11:17
 **/
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addSchedule(Schedule schedule) {
        Integer userId = jwtConfig.getUserId();
        schedule.setUserId(userId);
        judgeScheduleNameExistsException(schedule);
        if (schedule.getStartTime() != null) {
            judgeScheduleTimeException(schedule);
        }
        int insert = scheduleMapper.insert(schedule);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSchedule(Schedule schedule) {
        judgeScheduleNotExistsException(schedule);
        int delete = scheduleMapper.deleteByPrimaryKey(schedule.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSchedule(Schedule schedule) {
        judgeScheduleNotExistsException(schedule);
        if (schedule.getName() != null) {
            judgeScheduleNameExistsException(schedule);
        }
        if (schedule.getStartTime() != null) {
            judgeScheduleTimeException(schedule);
        }
        int update = scheduleMapper.updateByPrimaryKey(schedule);
        if (update != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ScheduleDTO> selectAll() {
        List<Schedule> scheduleList = scheduleMapper.selectAll();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<ScheduleDTO> scheduleDTOList = convertToScheduleDTOList(scheduleList);
            return scheduleDTOList;
        }
        return null;
    }

    @Override
    public Schedule selectByPrimaryKey(Integer id) {
        Schedule schedule = scheduleMapper.selectByPrimaryKey(id);
        if (schedule != null) {
            return schedule;
        }
        return null;
    }

    @Override
    public List<ScheduleDTO> selectByUserId() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<ScheduleDTO> scheduleDTOList = convertToScheduleDTOList(scheduleList);
            return scheduleDTOList;
        }
        return null;
    }

    @Override
    public List<ScheduleDTO> selectNotFinishSchedule() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<Schedule> schedules = getSchedulesByCalendar(scheduleList, false);
            List<ScheduleDTO> scheduleDTOList = convertToScheduleDTOList(schedules);
            return scheduleDTOList;
        }
        return null;
    }

    @Override
    public List<ScheduleDTO> selectFinishSchedule() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<Schedule> schedules = getSchedulesByCalendar(scheduleList, true);
            List<ScheduleDTO> scheduleDTOList = convertToScheduleDTOList(schedules);
            return scheduleDTOList;
        }
        return null;
    }

    @Override
    public Boolean setScheduleStatus(Schedule schedule) {
        if (schedule.getWhetherFinish()) {
            schedule.setWhetherFinish(false);
        } else {
            schedule.setWhetherFinish(true);
        }
        schedule.setCompletionTime(new Date());
        schedule.setUpdateTime(new Date());
        int update = scheduleMapper.updateByPrimaryKey(schedule);
        if (update != 0) {
            return true;
        }
        return false;
    }

    private Boolean judgeScheduleName(Schedule schedule) {
        List<ScheduleDTO> scheduleList = selectByUserId();
        if (scheduleList == null) {
            return false;
        }
        for (ScheduleDTO schedule1 : scheduleList) {
            if (schedule.getName().equals(schedule1.getName())) {
                return true;
            }
        }
        return false;
    }

    private Boolean judgeScheduleExists(Schedule schedule) {
        Schedule select = scheduleMapper.selectByPrimaryKey(schedule.getId());
        if (select != null) {
            return true;
        }
        return false;
    }

    private Boolean judgeScheduleTime(Schedule schedule) {
        Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
        Calendar endCalendar = assignmentCalendar(schedule.getEndTime());
        if (startCalendar.before(endCalendar)) {
            return true;
        }
        return false;
    }

    private List<Schedule> getSchedulesByCalendar(List<Schedule> scheduleList, Boolean flag) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            Calendar calendar = assignmentCalendar(new Date());
            Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
            int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Boolean judge = null;
            if (flag) {
                judge = schedule.getWhetherFinish();
            } else {
                judge = !schedule.getWhetherFinish();
            }
            if ((startDay == day) && (judge)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    private Calendar assignmentCalendar(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }

    private void judgeScheduleNotExistsException(Schedule schedule) {
        Boolean judgeScheduleExists = judgeScheduleExists(schedule);
        if (!judgeScheduleExists) {
            throw new ScheduleNotExistsException("该任务不存在");
        }
    }

    private void judgeScheduleNameExistsException(Schedule schedule) {
        Boolean judgeScheduleName = judgeScheduleName(schedule);
        if (judgeScheduleName) {
            throw new ScheduleNameExistsException("该任务名称已存在");
        }
    }

    private void judgeScheduleTimeException(Schedule schedule) {
        Boolean judgeScheduleTime = judgeScheduleTime(schedule);
        if (judgeScheduleTime) {
            throw new ScheduleTimeException("该日程的结束时间不可早于开始时间");
        }
    }

    private List<ScheduleDTO> convertToScheduleDTOList(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO = scheduleDTO.convertFor(schedule);
            scheduleDTOList.add(scheduleDTO);
        }
        return scheduleDTOList;
    }
}
