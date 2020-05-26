package com.program.itta.mapper;

import com.program.itta.domain.entity.Schedule;

import java.util.List;

public interface ScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Schedule record);

    Schedule selectByPrimaryKey(Integer id);

    List<Schedule> selectAll();

    int updateByPrimaryKey(Schedule record);

    List<Schedule> selectByUserId(Integer userId);

}