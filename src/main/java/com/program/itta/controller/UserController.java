package com.program.itta.controller;

import com.program.itta.common.exception.TokenVerificationException;
import com.program.itta.common.exception.user.UserNullException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.result.ResultCodeEnum;
import com.program.itta.dto.UserDTO;
import com.program.itta.entity.User;
import com.program.itta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: itta
 * @description: 用户Web交互控制层
 * @author: Mr.Huang
 * @create: 2020-04-04 10:40
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public HttpResult addUser(@RequestBody UserDTO userDTO) {
        // TODO 起始判断
        User user = userDTO.convertToUser();
        userService.addUser(user);
        return HttpResult.success(userDTO);
    }
}
