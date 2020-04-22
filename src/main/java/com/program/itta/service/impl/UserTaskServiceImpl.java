package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.config.ShiroRealmConfig;
import com.program.itta.common.jwt.JwtFilter;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.User;
import com.program.itta.domain.entity.UserTask;
import com.program.itta.mapper.UserTaskMapper;
import com.program.itta.service.UserItemServive;
import com.program.itta.service.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addUserTask(Task task) {
        UserTask userTask = UserTask.builder()
                .userId(task.getLeaderId())
                .taskId(task.getId())
                .leader(true)
                .build();
        int insert = userTaskMapper.insert(userTask);
        if (insert != 0) {
            logger.info("用户：" + userTask.getUserId() + "添加任务：" + userTask.getTaskId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean addUserTask(Integer taskId, List<Integer> userIdList) {
        for (int i = 0; i < userIdList.size(); i++) {
            UserTask userTask = UserTask.builder()
                    .userId(userIdList.get(i))
                    .taskId(taskId)
                    .build();
            logger.info("用户：" + userTask.getUserId() + "加入任务：" + userTask.getTaskId());
            int insert = userTaskMapper.insert(userTask);
            if (insert == 0) {
                return false;
            }
        }
        return true;
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
                if (userTask.getLeader()) {
                    delete = deleteUserTaskList(userTaskList);
                } else {
                    // 正常删除
                    delete = userTaskMapper.deleteByPrimaryKey(userTask.getId());
                }
            }
        }
        if (delete != 0) {
            return true;
        }
        return false;
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
}
