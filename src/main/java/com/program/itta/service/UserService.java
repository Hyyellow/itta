package com.program.itta.service;

import com.program.itta.dto.UserDTO;
import com.program.itta.entity.User;

public interface UserService {
    // 添加用户
    Boolean addUser(User user);
    // 删除用户(尚完善)
    Boolean deleteUser(User user);
    // 查找模块——后期可考虑ES

}