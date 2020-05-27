package com.program.itta.service;

import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;

import java.util.List;

public interface TaskService {
    // 添加任务
    Boolean addTask(Task task);

    // 删除任务
    Boolean deleteTask(Task task);

    // 更新任务
    Boolean updateTask(Task task);

    // 查看项目中的任务
    List<TaskDTO> selectByItemId(Integer itemId);

    // 查看所有我的任务
    List<TaskDTO> selectAllMyTask();

    // 查看所有我创建的任务
    List<TaskDTO> selectByUserId();

    // 查看所有该任务的子任务
    List<TaskDTO> selectBySuperId(Task task);

    // 查看该项目成员的所有任务
    List<TaskDTO> selectByItemMember(Integer itemId, Integer userId);

    // 查看该成员的所有关于该任务的子任务
    List<TaskDTO> selectAllSubTask(Integer taskId, List<Integer> taskIdList);

    // 删除该项目下的所有任务
    Boolean deleteByItemId(Integer itemId);
}
