package com.program.itta.controller;

import java.util.List;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.task.TaskAddFailException;
import com.program.itta.common.exception.task.TaskDelFailException;
import com.program.itta.common.exception.task.TaskUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.TaskService;
import com.program.itta.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.program.itta.common.result.ResultCodeEnum.*;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: itta
 * @description: Web层 Task
 * @author: Mr.Huang
 * @create: 2020-04-14 15:00
 **/
@RestController
@RequestMapping("/task")
public class TaskController {
    /**
     * TODO
     * 1.任务的添加 完成
     * 2.任务的删除 完成
     * 3.任务的更新 完成
     * 4.查看该用户的所有任务 完成
     * 5.查看该项目下的所有任务 完成
     */
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserTaskService userTaskService;

    @Resource
    private JwtConfig jwtConfig;

    @PostMapping("/addTask")
    public HttpResult addTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean addTask = taskService.addTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task);
        if (!(addTask && addUserTask)) {
            throw new TaskAddFailException("任务添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @DeleteMapping("/deleteTask")
    public HttpResult deleteTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean deleteTask = taskService.deleteTask(task);
        Boolean deleteUserTask = userTaskService.deleteUserTask(task);
        if (!(deleteTask && deleteUserTask)) {
            throw new TaskDelFailException("任务删除失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @PutMapping("/updateTask")
    public HttpResult updateTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        List<Integer> userIdList = taskDTO.getUserIdList();
        Boolean updateTask = taskService.updateTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task.getId(), userIdList);
        if (!(updateTask && addUserTask)) {
            throw new TaskUpdateFailException("任务更新失败");
        }
        return HttpResult.success();
    }

    @GetMapping("/selectTaskToEdit")
    public HttpResult selectTaskToEdit(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Task taskToEdit = taskService.selectTaskToEdit(task);
        if (taskToEdit != null) {
            return HttpResult.success(taskToEdit);
        } else {
            return HttpResult.failure(NOT_FOUND);
        }
    }

    @GetMapping("/selectTaskByItemId")
    public HttpResult selectTaskByItemId(@RequestParam(value = "itemId") Integer itemId) {
        List<Task> taskList = taskService.selectTaskByItemId(itemId);
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }

    @GetMapping("/selectMyTask")
    public HttpResult selectAllTask() {
        List<Task> taskList = taskService.selectTaskByLeaderId();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }

    @GetMapping("/selectMyCreateTask")
    public HttpResult selectMyCreateTask() {
        List<Task> taskList = taskService.selectTaskByFounderId();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }
}
