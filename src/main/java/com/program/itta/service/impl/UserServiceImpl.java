package com.program.itta.service.impl;

import com.program.itta.common.exception.TokenVerificationException;
import com.program.itta.dto.UserDTO;
import com.program.itta.entity.User;
import com.program.itta.mapper.UserMapper;
import com.program.itta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: itta
 * @description: 用户Service实现类
 * @author: Mr.Huang
 * @create: 2020-04-04 16:15
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public Boolean addUser(User user) {
        int insert = userMapper.insert(user);
        if (insert != 0){
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @Override
    public Boolean deleteUser(User user) {
        int delete = userMapper.deleteByPrimaryKey(user.getId());
        if (delete != 0){
            return true;
        }
        return false;
    }
}
