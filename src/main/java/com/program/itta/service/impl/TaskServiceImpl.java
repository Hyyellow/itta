package com.program.itta.service.impl;

import java.util.ArrayList;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemNotExistsException;
import com.program.itta.common.exception.permissions.NotTaskFoundException;
import com.program.itta.common.exception.task.TaskNameExistsException;
import com.program.itta.common.exception.task.TaskNotExistsException;
import com.program.itta.common.util.fineGrainedPermissions.TaskPermissionsUtil;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.UserTask;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.mapper.TaskMapper;
import com.program.itta.mapper.UserTaskMapper;
import com.program.itta.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserTaskMapper userTaskMapper;

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private TaskPermissionsUtil taskPermissionsUtil;

    @Override
    public Boolean addTask(Task task) {
        Integer userId = jwtConfig.getUserId();
        task.setUserId(userId);
        Boolean judgeItemExists = judgeItemExists(task.getItemId());
        if (!judgeItemExists) {
            throw new ItemNotExistsException("该项目不存在");
        }
        Boolean judgeTaskName = judgeTaskName(task);
        if (judgeTaskName) {
            throw new TaskNameExistsException("该任务名称已存在于此项目中");
        }
        int insert = taskMapper.insert(task);
        if (insert != 0) {
            logger.info("用户：" + task.getUserId() + "添加任务" + task.getName());
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
        Integer userId = jwtConfig.getUserId();
        Boolean judgeTaskExists = judgeTaskExists(task);
        if (!judgeTaskExists) {
            throw new TaskNotExistsException("该任务不存在，任务id查找为空");
        }
        /*Boolean judgeTaskLeader = judgeTaskLeader(userId, task);
        if (!judgeTaskLeader) {
            throw new NotTaskFoundException("无设置该任务负责人的相关权限");
        }*/
        task.setUpdateTime(new Date());
        int update = taskMapper.updateByPrimaryKey(task);
        if (update != 0) {
            logger.info("任务：" + task.getId() + "更新任务信息为：" + task);
            return true;
        }
        return false;
    }


    @Override
    public List<Task> selectByItemId(Integer itemId) {
        List<Task> taskList = taskMapper.selectByItemId(itemId);
        if (taskList != null && !taskList.isEmpty()) {
            return taskList;
        }
        return null;
    }

    @Override
    public List<Task> selectAllMyTask() {
        Integer userId = jwtConfig.getUserId();
        List<UserTask> userTaskList = userTaskMapper.selectByUserId(userId);
        List<Task> taskList = convertToTaskList(userTaskList);
        if (taskList != null && !taskList.isEmpty()) {
            return taskList;
        }
        return null;
    }

    @Override
    public List<Task> selectByUserId() {
        Integer userId = jwtConfig.getUserId();
        List<Task> taskList = taskMapper.selectByUserId(userId);
        if (taskList != null && !taskList.isEmpty()) {
            return taskList;
        }
        return null;
    }

    private List<Task> convertToTaskList(List<UserTask> userTaskList) {
        List<Task> taskList = new ArrayList<>();
        if (userTaskList != null && !userTaskList.isEmpty()) {
            List<Integer> taskIdList = userTaskList.stream()
                    .map(UserTask -> UserTask.getTaskId())
                    .collect(Collectors.toList());
            for (Integer taskId : taskIdList) {
                Task task = taskMapper.selectByPrimaryKey(taskId);
                taskList.add(task);
            }
            return taskList;
        }
        return null;
    }

    private Boolean judgeTaskLeader(Integer userId, Task task) {
        if (task.getUserId() != null) {
            Boolean updatePermissions = taskPermissionsUtil.UpdatePermissions(userId, task);
            if (!updatePermissions) {
                return false;
            }
        }
        return true;
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

    private Boolean judgeItemExists(Integer itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item != null) {
            return true;
        }
        return false;
    }
}
