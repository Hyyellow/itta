package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.schedule.ScheduleNameExistsException;
import com.program.itta.common.exception.schedule.ScheduleNotExistsException;
import com.program.itta.common.exception.schedule.ScheduleTimeException;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.mapper.ScheduleMapper;
import com.program.itta.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ScheduleMapper scheduleMapper;
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addSchedule(Schedule schedule) {
        Integer userId = jwtConfig.getUserId();
        schedule.setUserId(userId);
        judgeScheduleNameExists(schedule);
        if (schedule.getStartTime() != null) {
            judgeScheduleTime(schedule);
        }
        int insert = scheduleMapper.insert(schedule);
        if (insert != 0) {
            logger.info("用户" + userId + "增加日程" + schedule.getName());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSchedule(Schedule schedule) {
        judgeScheduleNotExists(schedule);
        int delete = scheduleMapper.deleteByPrimaryKey(schedule.getId());
        if (delete != 0) {
            logger.info("用户" + schedule.getUserId() + "删除日程" + schedule.getName());
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSchedule(Schedule schedule) {
        judgeScheduleNotExists(schedule);
        if (schedule.getName() != null) {
            judgeScheduleNameExists(schedule);
        }
        if (schedule.getStartTime() != null) {
            judgeScheduleTime(schedule);
        }
        int update = scheduleMapper.updateByPrimaryKey(schedule);
        if (update != 0) {
            logger.info("用户" + schedule.getUserId() + "编辑日程" + schedule);
            return true;
        }
        return false;
    }

    @Override
    public List<ScheduleDTO> selectAll() {
        List<Schedule> scheduleList = scheduleMapper.selectAll();
        if (scheduleList != null && !scheduleList.isEmpty()) {
            return convertToScheduleDTOList(scheduleList);
        }
        return null;
    }

    @Override
    public Schedule selectByPrimaryKey(Integer id) {
        return scheduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ScheduleDTO> selectByUserId(Integer month) {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        List<Schedule> schedules = new ArrayList<>();
        if (month != 0) {
            for (Schedule schedule : scheduleList) {
                Calendar calendar = assignmentCalendar(schedule.getStartTime());
                if (month == calendar.get(Calendar.MONTH) + 1) {
                    schedules.add(schedule);
                }
            }
            return convertToScheduleDTOList(schedules);
        }
        return convertToScheduleDTOList(scheduleList);
    }

    @Override
    public List<ScheduleDTO> selectNotFinishSchedule() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<Schedule> schedules = getSchedulesByCalendar(scheduleList, false);
            return convertToScheduleDTOList(schedules);
        }
        return null;
    }

    @Override
    public List<ScheduleDTO> selectFinishSchedule() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<Schedule> schedules = getSchedulesByCalendar(scheduleList, true);
            return convertToScheduleDTOList(schedules);
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
        return update != 0;
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

    private void judgeScheduleNotExists(Schedule schedule) {
        boolean judgeScheduleExists = false;
        Schedule select = scheduleMapper.selectByPrimaryKey(schedule.getId());
        if (select != null) {
            judgeScheduleExists = true;
        }
        if (!judgeScheduleExists) {
            throw new ScheduleNotExistsException("该任务不存在");
        }
    }

    private void judgeScheduleNameExists(Schedule schedule) {
        boolean judgeScheduleName = false;
        List<ScheduleDTO> scheduleList = selectByUserId(0);
        if (scheduleList == null) {
            return;
        }
        for (ScheduleDTO schedule1 : scheduleList) {
            if (schedule.getName().equals(schedule1.getName())) {
                judgeScheduleName = true;
            }
        }
        if (judgeScheduleName) {
            throw new ScheduleNameExistsException("该任务名称已存在");
        }
    }

    private void judgeScheduleTime(Schedule schedule) {
        boolean judgeScheduleTime = false;
        Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
        Calendar endCalendar = assignmentCalendar(schedule.getEndTime());
        if (!startCalendar.before(endCalendar)) {
            judgeScheduleTime = true;
        }
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
