package com.program.itta.mapper;

import com.program.itta.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Schedule record);

    Schedule selectByPrimaryKey(Integer id);

    List<Schedule> selectAll();

    int updateByPrimaryKey(Schedule record);
}