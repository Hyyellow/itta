package com.program.itta.controller;

import java.awt.Desktop.Action;

import com.program.itta.common.exception.item.ItemAddFailException;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.task.TaskAddFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.TaskService;
import com.program.itta.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 1.任务的添加
     * 2.任务的删除
     * 3.任务的更新
     * 4.查看该用户的所有任务
     * 5.查看该项目下的所有任务
     */
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserTaskService userTaskService;

    @PostMapping("/addTask")
    public HttpResult addTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean addTask = taskService.addTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task);
        // TODO 参与人的判断（更新）
        if (!(addTask && addUserTask)) {
            throw new TaskAddFailException("任务添加失败");
        }
        return HttpResult.success();
    }


}
