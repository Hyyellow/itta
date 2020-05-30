package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.UserTask;
import com.program.itta.mapper.TaskMapper;
import com.program.itta.mapper.UserTaskMapper;
import com.program.itta.service.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: itta
 * @description: Service UserTask实现类
 * @author: Mr.Huang
 * @create: 2020-04-18 11:24
 **/
@Service
public class UserTaskServiceImpl implements UserTaskService {

    private static final Logger logger = LoggerFactory.getLogger(UserTaskServiceImpl.class);

    @Autowired
    private UserTaskMapper userTaskMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addUserTask(Task task) {
        Task task1 = getTask(task);
        taskMapper.selectByItemId(task.getItemId());
        UserTask userTask = UserTask.builder()
                .userId(task1.getUserId())
                .taskId(task1.getId())
                .whetherLeader(true)
                .build();
        int insert = userTaskMapper.insert(userTask);
        if (insert != 0) {
            logger.info("用户：" + userTask.getUserId() + "添加任务：" + userTask.getTaskId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserTask(Task task) {
        int delete = 0;
        Integer userId = jwtConfig.getUserId();
        List<UserTask> userTaskList = userTaskMapper.selectByTaskId(task.getId());
        for (UserTask userTask : userTaskList) {
            if (userTask.getUserId().equals(userId)) {
                logger.info("用户：" + userTask.getUserId() + "删除任务：" + userTask.getTaskId());
                // 负责人删除
                if (userTask.getWhetherLeader()) {
                    delete = deleteUserTaskList(userTaskList);
                } else {
                    // 正常删除
                    delete = userTaskMapper.deleteByPrimaryKey(userTask.getId());
                }
            }
        }
        if (delete != 0) {
            logger.info("用户：" + userId + "删除任务：" + task.getId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteMemberUserTask(List<TaskDTO> taskList, Integer userId) {
        for (TaskDTO taskDTO : taskList) {
            UserTask userTask = UserTask.builder()
                    .taskId(taskDTO.getId())
                    .userId(taskDTO.getUserId())
                    .build();
            if (userId != 0) {
                userTask.setUserId(userId);
            }
            UserTask userTask1 = userTaskMapper.selectByUserTask(userTask);
            int delete = userTaskMapper.deleteByPrimaryKey(userTask1.getId());
            if (delete == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Integer> selectByTaskId(Integer taskId) {
        List<UserTask> userTaskList = userTaskMapper.selectByTaskId(taskId);
        List<Integer> userIdList = userTaskList.stream()
                .map(UserTask -> UserTask.getUserId())
                .collect(Collectors.toList());
        if (!userIdList.isEmpty()) {
            return userIdList;
        }
        return null;
    }

    @Override
    public List<Integer> selectByUserId(Integer userId) {
        List<UserTask> userTaskList = userTaskMapper.selectByUserId(userId);
        List<Integer> taskIdList = userTaskList.stream()
                .map(UserTask -> UserTask.getTaskId())
                .collect(Collectors.toList());
        if (!taskIdList.isEmpty()) {
            return taskIdList;
        }
        return null;
    }

    private int deleteUserTaskList(List<UserTask> userTaskList) {
        for (UserTask userTask : userTaskList) {
            int delete = userTaskMapper.deleteByPrimaryKey(userTask.getId());
            if (delete == 0) {
                return 0;
            }
        }
        return 1;
    }

    private Task getTask(Task task) {
        List<Task> taskList = taskMapper.selectByItemId(task.getItemId());
        for (Task task1 : taskList) {
            Boolean judgeUserId = task.getUserId().equals(task1.getUserId());
            Boolean judgeTaskName = task.getName().equals(task1.getName());
            if (judgeUserId && judgeTaskName) {
                return task1;
            }
        }
        return null;
    }
}
