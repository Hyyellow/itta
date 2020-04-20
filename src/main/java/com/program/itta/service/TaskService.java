package com.program.itta.service;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;

import java.util.List;

public interface TaskService {
 /*   // 添加项目
    Boolean addItem(Item item);

    // 删除项目
    Boolean deleteItem(Item item);

    // 更新项目
    Boolean updateItem(Item item);

    // 判断项目是否存在
    Boolean judgeItem(Item item);

    // 查找该用户的所有项目
    List<Item> selectAllItem(List<Integer> itemIdList);*/

    // 添加任务
    Boolean addTask(Task task);

    // 删除任务
    Boolean deleteTask(Task task);

    // 更新任务
    Boolean updateTask(Task task);

    // 查找任务
    List<Task> selectTask(Integer itemId);
}
