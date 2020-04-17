package com.program.itta.controller;

import com.program.itta.common.exception.user.UserDelFailException;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.common.exception.user.UserUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.User;
import com.program.itta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PutMapping("/updateUser")
    public HttpResult updateUser(@RequestBody @Valid UserDTO userDTO){
        User user = userDTO.convertToUser();
        Boolean updateUser = userService.updateUser(user);
        if(!updateUser){
            throw new UserUpdateFailException("用户信息更新失败");
        }
        return HttpResult.success();
    }

    @DeleteMapping("/deleteUser")
    public HttpResult deleteUser(@RequestBody @Valid UserDTO userDTO){
        User user = userDTO.convertToUser();
        // TODO 该用户的关联表数据仍需进行处理——项目， 任务， 日程
        Boolean deleteUser = userService.deleteUser(user);
        if (!deleteUser){
            throw new UserDelFailException("用户信息删除失败");
        }
        return HttpResult.success();
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
