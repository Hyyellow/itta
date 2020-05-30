package com.program.itta.service;

import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.UserTask;

import java.util.List;

public interface UserTaskService {
    // 添加用户任务中间关系
    Boolean addUserTask(Task task);

    // 删除用户任务中间关系
    Boolean deleteUserTask(Task task);

    // 删除用户的所有任务
    Boolean deleteMemberUserTask(List<TaskDTO> taskList);

    // 查看该任务的所有参与人
    List<Integer> selectByTaskId(Integer taskId);

    // 查看该用户的所以后任务
    List<Integer> selectByUserId(Integer userId);


}
