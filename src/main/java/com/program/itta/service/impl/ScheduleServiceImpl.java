package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.mapper.ScheduleMapper;
import com.program.itta.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        // TODO Schedule的判断
        int insert = scheduleMapper.insert(schedule);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSchedule(Schedule schedule) {
        int delete = scheduleMapper.deleteByPrimaryKey(schedule.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateSchedule(Schedule schedule) {
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
}
