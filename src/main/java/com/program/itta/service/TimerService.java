package com.program.itta.service;

import com.program.itta.domain.entity.Timer;

import java.util.List;

public interface TimerService {
    List<Timer> selectAll();

    Boolean addTimer(Timer timer);

    Boolean updateTimer(Timer timer);

    Boolean deleteTimer(Timer timer);

    Boolean addWorkDayTimer();
}
