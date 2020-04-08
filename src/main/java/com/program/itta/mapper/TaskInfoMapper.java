package com.program.itta.mapper;

import com.program.itta.domain.entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskInfo record);

    TaskInfo selectByPrimaryKey(Integer id);

    List<TaskInfo> selectAll();

    int updateByPrimaryKey(TaskInfo record);
}