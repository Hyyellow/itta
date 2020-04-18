package com.program.itta.service;

import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.UserTask;

import java.util.List;

public interface UserTaskService {
    // 添加用户任务中间关系
    Boolean addUserTask(Task task);

    // 批量添加用户任务中间关系
    Boolean addUserTask(Integer taskId, List<Integer> userIdList);
}
