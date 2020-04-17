package com.program.itta.mapper;

import com.program.itta.domain.entity.Task;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);
}