package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.schedule.ScheduleNameExistsException;
import com.program.itta.common.exception.schedule.ScheduleNotExistsException;
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
        Boolean judgeScheduleExists = judgeScheduleName(schedule);
        if (judgeScheduleExists) {
            throw new ScheduleNameExistsException("该任务名称已存在");
        }
        int insert = scheduleMapper.insert(schedule);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSchedule(Schedule schedule) {
        Boolean judgeScheduleExists = judgeScheduleExists(schedule);
        if (!judgeScheduleExists) {
            throw new ScheduleNotExistsException("该任务不存在");
        }
        int delete = scheduleMapper.deleteByPrimaryKey(schedule.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSchedule(Schedule schedule) {
        Boolean judgeScheduleExists = judgeScheduleExists(schedule);
        if (!judgeScheduleExists) {
            throw new ScheduleNotExistsException("该任务不存在");
        }
        int update = scheduleMapper.updateByPrimaryKey(schedule);
        if (update != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Schedule> selectByUserId() {
        Integer userId = jwtConfig.getUserId();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            return scheduleList;
        }
        return null;
    }

    @Override
    public List<Schedule> selectNotFinishSchedule() {
        Integer userId = jwtConfig.getUserId();
        Date time = new Date();
        List<Schedule> scheduleList = scheduleMapper.selectByUserId(userId);
        if (scheduleList != null && !scheduleList.isEmpty()) {
            List<Schedule> schedules = getFinishSchedule(scheduleList);
            return schedules;
        }
        return null;
    }

    private Boolean judgeScheduleName(Schedule schedule) {
        List<Schedule> scheduleList = selectByUserId();
        for (Schedule schedule1 : scheduleList) {
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

    private List<Schedule> getFinishSchedule(List<Schedule> scheduleList) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            Calendar calendar = assignmentCalendar(new Date());
            Calendar startCalendar = assignmentCalendar(schedule.getStartTime());
            Calendar endCalendar = assignmentCalendar(schedule.getEndTime());
            int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            boolean before = calendar.before(endCalendar);
            if ((startDay == day) && (before)) {
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
}
