package com.program.itta.service.impl;


import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.user.UserNotExistsException;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.User;
import com.program.itta.mapper.UserMapper;
import com.program.itta.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: itta
 * @description: 用户Service实现类
 * @author: Mr.Huang
 * @create: 2020-04-04 16:15
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Resource
    private JwtConfig jwtConfig;
    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Boolean updateUser(User user) {
        Boolean judgeUser = judgeUser(user);
        if (!judgeUser){
            throw new UserNotExistsException("该用户不存在");
        }
        user.setUpdateTime(new Date());
        int update = userMapper.updateByPrimaryKey(user);
        if (update != 0) {
            logger.info("用户：" + user.getId() + "更新用户信息为：" + user);
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @Override
    public Boolean deleteUser(User user) {
        Boolean judgeUser = judgeUser(user);
        if (!judgeUser){
            throw new UserNotExistsException("该用户不存在");
        }
        int delete = userMapper.deleteByPrimaryKey(user.getId());
        if (delete != 0) {
            logger.info("用户：" + user.getId() + "删除用户信息为：" + user);
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否存在
     *
     * @param user
     * @return
     */
    @Override
    public Boolean judgeUser(User user) {
        List<User> userList = userMapper.selectAll();
        for (User user1 : userList) {
            if (user1.getWxOpenid().equals(user.getWxOpenid())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找用户
     *
     * @return
     */
    @Override
    public UserDTO selectUser() {
        Integer userId = jwtConfig.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.convertFor(user);
            return userDTO;
        }
        return null;
    }

    @Override
    public Boolean updateUserHead(String url) {
        Integer userId = jwtConfig.getUserId();
        User user = User.builder()
                .id(userId)
                .picture(url)
                .build();
        int update = userMapper.updateByPrimaryKey(user);
        if (update != 0) {
            logger.info("用户：" + user.getId() + "更新用户头像为：" + url);
            return true;
        }
        return false;
    }
}
