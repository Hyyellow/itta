package com.program.itta.common.exception;

import com.program.itta.common.exception.item.*;
import com.program.itta.common.exception.permissions.NotItemLeaderException;
import com.program.itta.common.exception.permissions.NotTaskFoundException;
import com.program.itta.common.exception.schedule.ScheduleNameExistsException;
import com.program.itta.common.exception.schedule.ScheduleNotExistsException;
import com.program.itta.common.exception.task.*;
import com.program.itta.common.exception.user.UserDelFailException;
import com.program.itta.common.exception.user.UserExistsException;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.common.exception.user.UserUpdateFailException;
import com.program.itta.common.result.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.program.itta.common.result.ResultCodeEnum.*;

/**
 * @program: itta
 * @description: 统一异常处理器
 * @author: Mr.Huang
 * @create: 2020-04-04 15:30
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*** 这里可以根据不同模块分开错误***/
    // 用户模块
    @ExceptionHandler(value = UserExistsException.class)
    public HttpResult userExistsExceptionnHandler(UserExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(User_Exists_Exception);
    }

    @ExceptionHandler(value = UserNotExistsException.class)
    public HttpResult userNotExistsExceptionHandler(UserNotExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(User_Not_Exists_Exception);
    }

    @ExceptionHandler(value = UserUpdateFailException.class)
    public HttpResult userUpdateFailExceptionHandler(UserUpdateFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(User_Update_Fail_Exception);
    }

    @ExceptionHandler(value = UserDelFailException.class)
    public HttpResult userDelFailExceptionHandler(UserDelFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(User_Del_Fail_Exception);
    }

    // 项目模块
    @ExceptionHandler(value = ItemNameExistsException.class)
    public HttpResult itemNameExistsExceptionHandler(ItemNameExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Item_Name_Exists_Exception);
    }

    @ExceptionHandler(value = ItemAddFailException.class)
    public HttpResult itemAddFailExceptionHandler(ItemAddFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Item_Add_Fail_Exception);
    }

    @ExceptionHandler(value = ItemDelFailException.class)
    public HttpResult itemDelFailExceptionHandler(ItemDelFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Item_Del_Fail_Exception);
    }

    @ExceptionHandler(value = ItemUpdateFailException.class)
    public HttpResult itemUpdateFailExceptionHandler(ItemUpdateFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Item_Update_Fail_Exception);
    }

    @ExceptionHandler(value = ItemNotExistsException.class)
    public HttpResult itemNotExistsExceptionHandler(ItemNotExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Item_Not_Exists_Exception);
    }

    // 任务模块
    @ExceptionHandler(value = TaskAddFailException.class)
    public HttpResult taskAddFailExceptionHandler(TaskAddFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Task_Add_Fail_Exception);
    }

    @ExceptionHandler(value = TaskNameExistsException.class)
    public HttpResult taskNameExistsExceptionHandler(TaskNameExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Task_Name_Exists_Exception);
    }

    @ExceptionHandler(value = TaskUpdateFailException.class)
    public HttpResult taskUpdateFailExceptionHandler(TaskUpdateFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Task_Update_Fail_Exception);
    }

    @ExceptionHandler(value = TaskDelFailException.class)
    public HttpResult taskDelFailExceptionHandler(TaskDelFailException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Task_Del_Fail_Exception);
    }

    @ExceptionHandler(value = TaskNotExistsException.class)
    public HttpResult taskNotExistsExceptionHandler(TaskNotExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Task_Not_Exists_Exception);
    }

    // 日程模块
    @ExceptionHandler(value = ScheduleNameExistsException.class)
    public HttpResult scheduleNameExistsExceptionHandler(ScheduleNameExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Schedule_Name_Exists_Exception);
    }

    @ExceptionHandler(value = ScheduleNotExistsException.class)
    public HttpResult scheduleNotExistsExceptionHandler(ScheduleNotExistsException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Schedule_Not_Exists_Exception);
    }

    // 权限控制模块
    @ExceptionHandler(value = NotItemLeaderException.class)
    public HttpResult notItemLeaderExceptionHandler(NotItemLeaderException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Not_Item_Leader_Exception);
    }

    @ExceptionHandler(value = NotTaskFoundException.class)
    public HttpResult notTaskFoundExceptionHandler(NotTaskFoundException e) {
        logger.error("发生业务异常！原因是：{}", e.getMsg());
        return HttpResult.failure(Not_Task_Found_Exception);
    }
}
