package com.program.itta.service;

import com.program.itta.domain.entity.Timer;

import java.util.List;

public interface TimerService {
    List<Timer> selectAll();
}
