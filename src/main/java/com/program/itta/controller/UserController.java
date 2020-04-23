package com.program.itta.controller;

import com.program.itta.common.exception.user.UserDelFailException;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.common.exception.user.UserUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.util.COSClientUtil;
import com.program.itta.common.util.SslUtil;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.User;
import com.program.itta.service.UserService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

import static com.program.itta.common.result.ResultCodeEnum.SERVER_ERROR;

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

    @Resource
    private COSClientUtil cosClientUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PutMapping("/updateUser")
    public HttpResult updateUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userDTO.convertToUser();
        Boolean updateUser = userService.updateUser(user);
        if (!updateUser) {
            throw new UserUpdateFailException("用户信息更新失败");
        }
        return HttpResult.success();
    }

    @DeleteMapping("/deleteUser")
    public HttpResult deleteUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userDTO.convertToUser();
        // TODO 该用户的关联表数据仍需进行处理——项目， 任务， 日程
        Boolean deleteUser = userService.deleteUser(user);
        if (!deleteUser) {
            throw new UserDelFailException("用户信息删除失败");
        }
        return HttpResult.success();
    }

    @GetMapping("/seletUser")
    public HttpResult selectUser(@RequestParam(value = "userId") Integer userId) {
        User user = userService.selectUser(userId);
        if (user == null) {
            throw new UserNotExistsException("该用户不存在");
        }
        return HttpResult.success(user);
    }

    @PostMapping("/upload")
    public HttpResult uploadGoodsPic(@RequestParam(value = "file") MultipartFile file) {
        CloseableHttpResponse response = null;
        String uuidFlag = UUID.randomUUID().toString();
        String url = cosClientUtil.uploadGoodsPic(file, "goods/", uuidFlag);
        if (url != null) {
            return HttpResult.success(url);
        }
        return HttpResult.failure(SERVER_ERROR);
    }
}
