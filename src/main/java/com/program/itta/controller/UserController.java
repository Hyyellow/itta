package com.program.itta.controller;
import	java.awt.font.NumericShaper.Range;

import com.program.itta.common.exception.user.UserExistsException;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.dto.UserDTO;
import com.program.itta.entity.User;
import com.program.itta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        User user = userDTO.convertToUser();
        // 判断openID是否存在
        Boolean judgeUser = userService.judgeUser(user);
        if (judgeUser){
            throw new UserExistsException("该用户已存在");
        }
        userService.addUser(user);
        return HttpResult.success(userDTO);
    }

    @PutMapping("/updateUser")
    public HttpResult updateUser(@RequestBody UserDTO userDTO){
        User user = userDTO.convertToUser();
        // 判断openID是否存在
        Boolean judgeUser = userService.judgeUser(user);
        if (!judgeUser){
            throw new UserNotExistsException("该用户不存在");
        }
        userService.updateUser(user);
        return HttpResult.success(userDTO);
    }

    @DeleteMapping("/deleteUser")
    public HttpResult deleteUser(@RequestBody UserDTO userDTO){
        User user = userDTO.convertToUser();
        // 判断openID是否存在
        Boolean judgeUser = userService.judgeUser(user);
        if (!judgeUser){
            throw new UserNotExistsException("该用户不存在");
        }
        // TODO 该用户的关联表数据仍需进行处理
        userService.updateUser(user);
        return HttpResult.success(userDTO);
    }

    @GetMapping("/seletUser")
    public HttpResult selectUser(@RequestParam(value = "userId") Integer userId){
        User user = userService.selectUser(userId);
        if (user == null){
            throw new UserNotExistsException("该用户不存在");
        }
        return HttpResult.success(user);
    }
}
