package com.program.itta.service.impl;

import com.program.itta.domain.entity.Timer;
import com.program.itta.mapper.TimerMapper;
import com.program.itta.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: itta
 * @description: Timer业务层实现类
 * @author: Mr.Huang
 * @create: 2020-05-20 17:06
 **/
@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private TimerMapper timerMapper;

    @Override
    public List<Timer> selectAll() {
        List<Timer> timerList = timerMapper.selectAll();
        if (timerList != null && !timerList.isEmpty()) {
            return timerList;
        }
        return null;
    }

    @Override
    public Boolean addTimer(Timer timer) {
        int insert = timerMapper.insert(timer);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateTimer(Timer timer) {
        if (timer == null) {
            throw new RuntimeException("空");
        }
        int update = timerMapper.updateByPrimaryKey(timer);
        if (update != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTimer(Timer timer) {
        int delete = timerMapper.deleteByPrimaryKey(timer.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean addWorkDayTimer() {
        Timer timer = Timer.builder()
                .year(0)
                .month(0)
                .day(0)
                .build();
        for (int i = 1; i <= 5; i++) {
            timer.setWeek(i);
            int insert = timerMapper.insert(timer);
            if (insert == 0) {
                return false;
            }
        }
        return true;
    }
}
