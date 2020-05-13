package com.program.itta.mapper;

import com.program.itta.domain.entity.TaskTag;
import java.util.List;

public interface TaskTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskTag record);

    TaskTag selectByPrimaryKey(Integer id);

    List<TaskTag> selectAll();

    int updateByPrimaryKey(TaskTag record);
}