package com.program.itta.service;

import com.program.itta.domain.entity.User;

public interface UserService {
    // 更新用户
    Boolean updateUser(User user);
    // 删除用户(尚完善)
    Boolean deleteUser(User user);
    // 判断用户是否存在
    Boolean judgeUser(User user);
    // 查找模块——后期可考虑ES
    User selectUser(Integer userId);
    // 更新用户头像
    Boolean updateUserHead(String url);

    Boolean insert(User user);



}
