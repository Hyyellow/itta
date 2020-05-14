package com.program.itta.mapper;

import com.program.itta.domain.entity.TaskTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskTag record);

    TaskTag selectByPrimaryKey(Integer id);

    List<TaskTag> selectAll();

    int updateByPrimaryKey(TaskTag record);

    TaskTag selectByTaskTag(TaskTag record);

    List<TaskTag> selectAllTag(Integer taskId);
}