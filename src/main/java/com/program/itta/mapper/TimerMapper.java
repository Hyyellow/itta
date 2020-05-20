package com.program.itta.mapper;

import com.program.itta.domain.entity.Timer;
import java.util.List;

public interface TimerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Timer record);

    Timer selectByPrimaryKey(Integer id);

    List<Timer> selectAll();

    int updateByPrimaryKey(Timer record);
}