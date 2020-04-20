package com.program.itta.service.impl;

import com.program.itta.common.exception.task.TaskNameExistsException;
import com.program.itta.common.exception.task.TaskNotExistsException;
import com.program.itta.domain.entity.Task;
import com.program.itta.mapper.TaskMapper;
import com.program.itta.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        Boolean judgeTaskName = judgeTaskName(task);
        if (judgeTaskName) {
            throw new TaskNameExistsException("该任务名称已存在于此项目中");
        }
        int insert = taskMapper.insert(task);
        if (insert != 0) {
            logger.info("用户：" + task.getLeaderId() + "添加任务" + task.getName());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTask(Task task) {
        Boolean judgeTaskExists = judgeTaskExists(task);
        if (!judgeTaskExists) {
            throw new TaskNotExistsException("该任务不存在，任务id查找为空");
        }
        int delete = taskMapper.deleteByPrimaryKey(task.getId());
        if (delete != 0) {
            logger.info("删除任务：" + task.getName() + "任务id为：" + task.getId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateTask(Task task) {
        Boolean judgeTaskExists = judgeTaskExists(task);
        if (!judgeTaskExists) {
            throw new TaskNotExistsException("该任务不存在，任务id查找为空");
        }
        task.setUpdateTime(new Date());
        int update = taskMapper.updateByPrimaryKey(task);
        if (update != 0) {
            logger.info("任务：" + task.getId() + "更新任务信息为：" + task);
            return true;
        }
        return false;
    }

    @Override
    public List<Task> selectTask(Integer itemId) {
        List<Task> taskList = taskMapper.selectByItemId(itemId);
        if (taskList != null && !taskList.isEmpty()) {
            return taskList;
        }
        return null;
    }

    private Boolean judgeTaskName(Task task) {
        List<Task> taskList = taskMapper.selectByItemId(task.getItemId());
        for (Task task1 : taskList) {
            if (task.getName().equals(task1.getName())) {
                return true;
            }
        }
        return false;
    }

    private Boolean judgeTaskExists(Task task) {
        Task select = taskMapper.selectByPrimaryKey(task.getId());
        if (select != null) {
            return true;
        }
        return false;
    }
}
