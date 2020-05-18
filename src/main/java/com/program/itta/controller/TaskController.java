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
import com.program.itta.service.TaskTagService;
import com.program.itta.service.UserTaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.web.bind.annotation.*;

import static com.program.itta.common.result.ResultCodeEnum.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: itta
 * @description: 任务API
 * @author: Mr.Huang
 * @create: 2020-04-14 15:00
 **/
@Api(tags = "任务接口")
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

    @Autowired
    private TaskTagService taskTagService;

    @Resource
    private JwtConfig jwtConfig;

    @ApiOperation(value = "添加任务", notes = "(添加此任务，团队任务，个人任务皆为此接口)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30001, message = "任务添加失败")})
    @PostMapping("/addTask")
    public HttpResult addTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                              @RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean addTask = taskService.addTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task);
        if (!(addTask && addUserTask)) {
            throw new TaskAddFailException("任务添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @ApiOperation(value = "删除任务", notes = "(删除该任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30004, message = "任务删除失败")})
    @DeleteMapping("/deleteTask")
    public HttpResult deleteTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                 @RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean deleteTask = taskService.deleteTask(task);
        Boolean deleteUserTask = userTaskService.deleteUserTask(task);
        Boolean deleteTaskTag = taskTagService.deleteTaskTag(task);
        if (!(deleteTask && deleteUserTask && deleteTaskTag)) {
            throw new TaskDelFailException("任务删除失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑任务", notes = "(编辑该任务内容)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30003, message = "任务更新失败")})
    @PutMapping("/updateTask")
    public HttpResult updateTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                 @RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        List<Integer> userIdList = taskDTO.getUserIdList();
        Boolean updateTask = taskService.updateTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task.getId(), userIdList);
        if (!(updateTask && addUserTask)) {
            throw new TaskUpdateFailException("任务更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "查找项目任务", notes = "(查看该项目的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该项目尚无添加任务")})
    @GetMapping("/selectTaskByItemId")
    public HttpResult selectTaskByItemId(@ApiParam(name = "项目id", value = "传入Json格式", required = true)
                                         @RequestParam(value = "itemId") Integer itemId) {
        List<Task> taskList = taskService.selectTaskByItemId(itemId);
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }

    @ApiOperation(value = "查找我的任务", notes = "(查看我的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户尚无任务")})
    @GetMapping("/selectMyTask")
    public HttpResult selectAllTask() {
        List<Task> taskList = taskService.selectAllMyTask();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该用户尚无任务");
        }
    }

    @ApiOperation(value = "查找我创建的任务", notes = "(查看我创建的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户尚无创建任务")})
    @GetMapping("/selectMyCreateTask")
    public HttpResult selectMyCreateTask() {
        List<Task> taskList = taskService.selectTaskByUserId();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该用户尚无创建任务");
        }
    }
}
