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

    // 查找项目中的任务
    List<Task> selectTaskByItemId(Integer itemId);

    // 查找所有我的任务
    List<Task> selectTaskByLeaderId();

    // 查看所有我创建的任务
    List<Task> selectTaskByFounderId();

    // 查看新建任务——用于建立后编辑使用
    Task selectTaskToEdit(Task task);
}
