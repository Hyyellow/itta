package com.program.itta.service.impl;

import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.TaskTag;
import com.program.itta.mapper.TagMapper;
import com.program.itta.mapper.TaskTagMapper;
import com.program.itta.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: itta
 * @description: 任务标签Service
 * @author: Mr.Huang
 * @create: 2020-05-13 21:23
 **/
@Service
public class TaskTagServiceImpl implements TaskTagService {
    @Autowired
    private TaskTagMapper taskTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Boolean addTaskTag(Integer taskId, String content) {
        Tag tag = tagMapper.selectByContent(content);
        TaskTag taskTag = TaskTag.builder()
                .taskId(taskId)
                .tagId(tag.getId())
                .build();
        Boolean judgeTaskTag = judgeTaskTag(taskTag);
        if (judgeTaskTag) {
            return true;
        }
        int insert = taskTagMapper.insert(taskTag);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    private Boolean judgeTaskTag(TaskTag taskTag) {
        TaskTag selectByTaskTag = taskTagMapper.selectByTaskTag(taskTag);
        if (selectByTaskTag != null) {
            return true;
        }
        return false;
    }
}
