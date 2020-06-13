package com.program.itta.service;

import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.TaskTag;

import java.util.List;

public interface TaskTagService {
    // 添加任务标签中间关系
    Boolean addTaskTag(Integer taskId, String content);

    // 添加任务标签中间关系
    Boolean addTaskTag(Task task, String content);

    // 查找该任务的所有标签
    List<Integer> selectByTaskId(Integer taskId);

    // 删除该任务的所有标签
    Boolean deleteAllTaskTag(Task task);

    // 删除该任务的标签
    Boolean deleteTaskTag(TaskTag taskTag);

    // 删除该用户下的所有任务标签
    Boolean deleteMemberTaskTag(List<TaskDTO> taskList);
}
