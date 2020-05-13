package com.program.itta.service;

import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.User;

import java.util.List;

public interface UserService {
    // 更新用户
    Boolean updateUser(User user);
    // 判断用户是否存在
    Boolean judgeUser(User user);
    // 更新用户头像
    Boolean updateUserHead(String url);
    // 查找模块——后期可考虑ES
    // 查找用户
    UserDTO selectUser();
    // 查找用户列表
    List<UserDTO> selectUserByIdList(List<Integer> userIds);
}
