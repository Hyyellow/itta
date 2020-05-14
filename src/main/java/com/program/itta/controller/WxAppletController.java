package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.jwt.JwtFilter;
import com.program.itta.service.WxAppletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: itta
 * @description: 小程序用户登录API
 * @author: Mr.Huang
 * @create: 2020-04-05 12:28
 **/
@Api(tags = "登录接口")
@RestController
public class WxAppletController {
    @Resource
    private WxAppletService wxAppletService;

    @Resource
    private JwtConfig jwtConfig;
    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @ApiOperation(value = "用户登录", notes = "(用于用户登录时，获取token)")
    @PostMapping("/api/wx/user/login")
    public ResponseEntity wxAppletLoginApi(@RequestBody Map<String, String> request) {
        if (!request.containsKey("code") || request.get("code") == null || "".equals(request.get("code"))) {
            Map<String, String> result = new HashMap<>();
            result.put("msg", "缺少参数code或code不合法");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(wxAppletService.wxUserLogin(request.get("code")), HttpStatus.OK);
        }
    }

}
