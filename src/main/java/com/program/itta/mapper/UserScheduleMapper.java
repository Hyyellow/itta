package com.program.itta.mapper;

import com.program.itta.entity.UserSchedule;
import java.util.List;

public interface UserScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSchedule record);

    UserSchedule selectByPrimaryKey(Integer id);

    List<UserSchedule> selectAll();

    int updateByPrimaryKey(UserSchedule record);
}