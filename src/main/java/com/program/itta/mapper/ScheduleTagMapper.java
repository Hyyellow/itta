package com.program.itta.mapper;

import com.program.itta.domain.entity.ScheduleTag;
import com.program.itta.domain.entity.TaskTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ScheduleTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleTag record);

    ScheduleTag selectByPrimaryKey(Integer id);

    List<ScheduleTag> selectAll();

    int updateByPrimaryKey(ScheduleTag record);

    ScheduleTag selectByScheduleTag(ScheduleTag record);

    List<ScheduleTag> selectByScheduleId(Integer scheduleId);
}