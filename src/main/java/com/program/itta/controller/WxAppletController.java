package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.jwt.JwtFilter;
import com.program.itta.service.WxAppletService;
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
    @PostMapping("/api/wx/user/login")
    public ResponseEntity wxAppletLoginApi(@RequestBody Map<String, String> request) {
        if (!request.containsKey("code") || request.get("code") == null || request.get("code").equals("")) {
            Map<String, String> result = new HashMap<>();
            result.put("msg", "缺少参数code或code不合法");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(wxAppletService.wxUserLogin(request.get("code")), HttpStatus.OK);
        }
    }
    /**
     * 需要认证的测试接口  需要 @RequiresAuthentication 注解，则调用此接口需要 header 中携带自定义登陆态 authorization
     */
    @RequiresAuthentication
    @PostMapping("/sayHello")
    public ResponseEntity sayHello() {
        Integer userId = jwtConfig.getUserId();
        Map<String, String> result = new HashMap<>();
        result.put("words", "hello World");
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
