package com.program.itta.service;

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
    List<Task> selectByItemId(Integer itemId);

    // 查看所有我的任务
    List<Task> selectAllMyTask();

    // 查看所有我创建的任务
    List<Task> selectByUserId();
}
