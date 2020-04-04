package com.program.itta.mapper;

import com.program.itta.entity.TaskInfo;
import java.util.List;

public interface TaskInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskInfo record);

    TaskInfo selectByPrimaryKey(Integer id);

    List<TaskInfo> selectAll();

    int updateByPrimaryKey(TaskInfo record);
}