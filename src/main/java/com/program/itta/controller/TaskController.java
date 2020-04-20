package com.program.itta.controller;

import java.awt.Desktop.Action;
import java.util.List;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemAddFailException;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.task.TaskAddFailException;
import com.program.itta.common.exception.task.TaskDelFailException;
import com.program.itta.common.exception.task.TaskUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.TaskService;
import com.program.itta.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 4.查看该用户的所有任务
     * 5.查看该项目下的所有任务
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

    @GetMapping("/selectTaskByItemId")
    public HttpResult selectTaskByItemId(@RequestParam(value = "itemId") Integer itemId) {
        List<Task> taskList = taskService.selectTask(itemId);
        if(taskList.size()!=0){
            return HttpResult.success(taskList);
        }else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }
}
