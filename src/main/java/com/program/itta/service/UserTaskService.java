package com.program.itta.service;

import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.UserTask;

public interface UserTaskService {
    // 添加用户任务中间关系
    Boolean addUserTask(Task task);
}
