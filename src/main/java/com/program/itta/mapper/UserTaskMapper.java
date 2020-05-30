package com.program.itta.mapper;

import com.program.itta.domain.entity.UserTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTask record);

    UserTask selectByPrimaryKey(Integer id);

    List<UserTask> selectAll();

    int updateByPrimaryKey(UserTask record);

    List<UserTask> selectByTaskId(Integer taskId);

    List<UserTask> selectByUserId(Integer userId);

    UserTask selectByUserTask(UserTask record);

}