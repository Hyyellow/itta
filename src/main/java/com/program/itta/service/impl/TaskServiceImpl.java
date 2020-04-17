package com.program.itta.service.impl;

import com.program.itta.domain.entity.Task;
import com.program.itta.mapper.TaskMapper;
import com.program.itta.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: itta
 * @description: Service Task实现类
 * @author: Mr.Huang
 * @create: 2020-04-17 15:47
 **/
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Boolean addTask(Task task) {
        int insert = taskMapper.insert(task);
        // TODO Task名称是否重复
        if (insert!=0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTask(Task task) {
        int delete = taskMapper.deleteByPrimaryKey(task.getId());
        // TODO 中间表的处理
        if (delete!=0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateTask(Task task) {
        int update = taskMapper.updateByPrimaryKey(task);
        if (update!=0){
            return true;
        }
        return false;
    }

    @Override
    public Task selectTask() {
        return null;
    }
}
