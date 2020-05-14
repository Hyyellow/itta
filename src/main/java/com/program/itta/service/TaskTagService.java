package com.program.itta.service;

import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.TaskTag;

import java.util.List;

public interface TaskTagService {
    // 添加任务标签中间关系
    Boolean addTaskTag(Integer taskId, String content);

    // 查找该任务的所有标签
    List<Integer> selectAllTag(Integer taskId);
}
