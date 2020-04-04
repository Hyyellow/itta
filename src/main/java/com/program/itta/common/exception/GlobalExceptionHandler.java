package com.program.itta.common.exception;

import com.program.itta.common.exception.user.UserExistsException;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.result.ResultCodeEnum;
import com.program.itta.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.program.itta.common.result.ResultCodeEnum.User_Exists_Exception;
import static com.program.itta.common.result.ResultCodeEnum.User_Not_Exists_Exception;

/**
 * @program: itta
 * @description: 统一异常处理器
 * @author: Mr.Huang
 * @create: 2020-04-04 15:30
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常捕获
     *
     * @param e 捕获的异常
     * @return 封装的返回对象
     **/
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
}
