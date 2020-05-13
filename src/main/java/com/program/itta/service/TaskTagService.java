package com.program.itta.service;

import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.TaskTag;

public interface TaskTagService {
    // 添加任务标签中间关系
    Boolean addTaskTag(Integer taskId, String content);
}
