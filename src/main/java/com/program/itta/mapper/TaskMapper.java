package com.program.itta.mapper;

import com.program.itta.domain.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);

    List<Task> selectByItemId(Integer itemId);

    List<Task> selectByLeaderId(Integer itemId);
}