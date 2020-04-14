package com.program.itta.common.exception;

import com.program.itta.common.exception.item.ItemAddFailException;
import com.program.itta.common.exception.item.ItemDelFailException;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.user.UserExistsException;
import com.program.itta.common.exception.user.UserNotExistsException;
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
}
