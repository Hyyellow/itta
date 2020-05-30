package com.program.itta.mapper;

import com.program.itta.domain.entity.Timer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TimerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Timer record);

    Timer selectByPrimaryKey(Integer id);

    List<Timer> selectAll();

    int updateByPrimaryKey(Timer record);

    Timer selectByScheduleId(Integer scheduleId);
}