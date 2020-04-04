package com.program.itta.mapper;

import com.program.itta.entity.UserTask;
import java.util.List;

public interface UserTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTask record);

    UserTask selectByPrimaryKey(Integer id);

    List<UserTask> selectAll();

    int updateByPrimaryKey(UserTask record);
}