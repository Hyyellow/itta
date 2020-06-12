package com.program.itta.controller;

import java.util.List;

import com.program.itta.common.annotation.RequestLog;
import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemFindUserListException;
import com.program.itta.common.exception.task.TaskAddFailException;
import com.program.itta.common.exception.task.TaskDelFailException;
import com.program.itta.common.exception.task.TaskFindUserListException;
import com.program.itta.common.exception.task.TaskUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.valid.Delete;
import com.program.itta.common.valid.Update;
import com.program.itta.domain.dto.TaskDTO;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private TaskTagService taskTagService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Resource
    private JwtConfig jwtConfig;

    @RequestLog(module = "任务模块", operationDesc = "添加任务")
    @ApiOperation(value = "添加任务", notes = "(添加此任务，团队任务，个人任务皆为此接口)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30001, message = "任务添加失败")})
    @PostMapping("/addTask")
    public HttpResult addTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                              @RequestBody
                              @Validated TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean addTask = taskService.addTask(task);
        Boolean addUserTask = userTaskService.addUserTask(task);
        if (!(addTask && addUserTask)) {
            throw new TaskAddFailException("任务添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @RequestLog(module = "任务模块", operationDesc = "删除任务")
    @ApiOperation(value = "删除任务", notes = "(删除该任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30004, message = "任务删除失败")})
    @DeleteMapping("/deleteTask")
    public HttpResult deleteTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                 @RequestBody
                                 @Validated(Delete.class) TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        Boolean deleteTask = taskService.deleteTask(task);
        Boolean deleteUserTask = userTaskService.deleteUserTask(task);
        Boolean deleteTaskTag = taskTagService.deleteAllTaskTag(task);
        if (!(deleteTask && deleteUserTask && deleteTaskTag)) {
            throw new TaskDelFailException("任务删除失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @RequestLog(module = "任务模块", operationDesc = "编辑任务")
    @ApiOperation(value = "编辑任务", notes = "(编辑该任务内容)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30003, message = "任务更新失败")})
    @PutMapping("/updateTask")
    public HttpResult updateTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                 @RequestBody
                                 @Validated(Update.class) TaskDTO taskDTO) {
        Integer userId = jwtConfig.getUserId();
        Task task = taskDTO.convertToTask();
        Boolean updateTask = taskService.updateTask(task);
        newsService.addTaskNews(task, userId);
        jwtConfig.removeThread();
        if (!updateTask) {
            throw new TaskUpdateFailException("任务更新失败");
        }
        return HttpResult.success();
    }

    @RequestLog(module = "任务模块", operationDesc = "查找项目任务")
    @ApiOperation(value = "查找项目任务", notes = "(查看该项目的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该项目尚无添加任务")})
    @GetMapping("/selectTaskByItemId")
    public HttpResult selectTaskByItemId(@ApiParam(name = "项目id", value = "传入Json格式", required = true)
                                         @RequestParam(value = "itemId") Integer itemId) {
        List<TaskDTO> taskList = taskService.selectByItemId(itemId);
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该项目尚无添加任务");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找我的任务")
    @ApiOperation(value = "查找我的任务", notes = "(查看我的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户尚无任务")})
    @GetMapping("/selectMyTask")
    public HttpResult selectAllTask() {
        List<TaskDTO> taskList = taskService.selectAllMyTask();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该用户尚无任务");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找我创建的任务")
    @ApiOperation(value = "查找我创建的任务", notes = "(查看我创建的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户尚无创建任务")})
    @GetMapping("/selectMyCreateTask")
    public HttpResult selectMyCreateTask() {
        List<TaskDTO> taskList = taskService.selectByUserId();
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该用户尚无创建任务");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找该任务的子任务")
    @ApiOperation(value = "查找该任务的子任务", notes = "(查找该任务的所有子任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该任务尚无子任务")})
    @GetMapping("/selectSubTask")
    public HttpResult selectSubTask(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                    @RequestBody
                                    @Validated TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        List<TaskDTO> taskList = taskService.selectBySuperId(task);
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该任务尚无子任务");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找该任务的所有参与者")
    @ApiOperation(value = "查找该任务的所有参与者", notes = "(查找该任务的所有参与者)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 30006, message = "任务用户成员查找失败")})
    @GetMapping("/selectTaskMember")
    public HttpResult selectTaskMember(@ApiParam(name = "任务DTO类", value = "传入Json格式", required = true)
                                       @RequestBody
                                       @Validated TaskDTO taskDTO) {
        Task task = taskDTO.convertToTask();
        List<Integer> userIdList = userTaskService.selectByTaskId(task.getId());
        List<UserDTO> userList = userService.selectUserList(userIdList);
        if (userList != null && !userList.isEmpty()) {
            return HttpResult.success(userList);
        } else {
            throw new TaskFindUserListException("任务用户成员查找失败");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找该参与者的关于父任务的所有相关子任务")
    @ApiOperation(value = "查找该参与者的关于父任务的所有相关子任务", notes = "(查找该参与者的关于父任务的所有相关子任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该成员尚无关于该任务的子任务")})
    @GetMapping("/selectMemberTask")
    public HttpResult selectMemberTask(@ApiParam(name = "父任务id", value = "传入Json格式", required = true)
                                       @RequestParam(value = "taskId") Integer taskId,
                                       @ApiParam(name = "用户id", value = "传入Json格式", required = true)
                                       @RequestParam(value = "userId") Integer userId) {
        List<Integer> taskIdList = userTaskService.selectByUserId(userId);
        List<TaskDTO> subTaskList = taskService.selectAllSubTask(taskId, taskIdList);
        if (subTaskList != null && !subTaskList.isEmpty()) {
            return HttpResult.success(subTaskList);
        } else {
            return HttpResult.success("该成员尚无关于该任务的子任务");
        }
    }

    @RequestLog(module = "任务模块", operationDesc = "查找该项目成员的所有任务")
    @ApiOperation(value = "查找该项目成员的所有任务", notes = "(查找该项目成员的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该成员尚无任务")})
    @GetMapping("/selectItemMemberTask")
    public HttpResult selectItemMemberTask(@ApiParam(name = "项目id", value = "传入Json格式", required = true)
                                           @RequestParam(value = "itemId") Integer itemId,
                                           @ApiParam(name = "用户id", value = "传入Json格式", required = true)
                                           @RequestParam(value = "userId") Integer userId) {
        List<TaskDTO> taskList = taskService.selectByItemMember(itemId, userId);
        if (taskList.size() != 0) {
            return HttpResult.success(taskList);
        } else {
            return HttpResult.success("该成员尚无任务");
        }
    }

}
