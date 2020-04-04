package com.program.itta.controller;

import com.program.itta.common.exception.TokenVerificationException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: itta
 * @description: 用户Web交互控制层
 * @author: Mr.Huang
 * @create: 2020-04-04 10:40
 **/
@RestController
@RequestMapping("/httpRest")
public class UserController {
    //@ApiOperation(value = "通用返回成功（没有返回结果）", httpMethod = "GET")
    @GetMapping("/success")
    public HttpResult success(){
        return HttpResult.success();
    }

    //@ApiOperation(value = "返回成功（有返回结果）", httpMethod = "GET")
    @GetMapping("/successWithData")
    public HttpResult successWithData(){
        throw new TokenVerificationException("自定义异常");
    }

    //@ApiOperation(value = "通用返回失败", httpMethod = "GET")
    @GetMapping("/failure")
    public HttpResult failure(){
        return HttpResult.failure(ResultCodeEnum.NOT_FOUND);
    }

}
