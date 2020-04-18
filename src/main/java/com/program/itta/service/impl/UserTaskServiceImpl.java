package com.program.itta.service.impl;

import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.User;
import com.program.itta.domain.entity.UserTask;
import com.program.itta.mapper.UserTaskMapper;
import com.program.itta.service.UserItemServive;
import com.program.itta.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: itta
 * @description: Service UserTask实现类
 * @author: Mr.Huang
 * @create: 2020-04-18 11:24
 **/
@Service
public class UserTaskServiceImpl implements UserTaskService {
    @Autowired
    private UserTaskMapper userTaskMapper;

    @Override
    public Boolean addUserTask(Task task) {
        UserTask userTask = UserTask.builder()
                .userId(task.getLeaderId())
                .taskId(task.getId())
                .leader(true)
                .build();
        int insert = userTaskMapper.insert(userTask);
        if (insert!=0){
            return true;
        }
        return false;
    }
}
