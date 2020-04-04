package com.program.itta.dto;

import com.program.itta.common.convert.DTOConvert;
import com.program.itta.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @program: itta
 * @description: 用户DTO
 * @author: Mr.Huang
 * @create: 2020-04-04 16:00
 **/
@Data
public class UserDTO {
    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String openid;

    private String markId;

    public User convertToUser(){
        UserDTOConvert userDTOConvert = new UserDTOConvert();
        User convert = userDTOConvert.doForward(this);
        return convert;
    }

    public UserDTO convertFor(User user){
        UserDTOConvert userDTOConvert = new UserDTOConvert();
        UserDTO convert = userDTOConvert.doBackward(user);
        return convert;
    }

    private static class UserDTOConvert extends DTOConvert<UserDTO, User> {
        @Override
        protected User doForward(UserDTO userDTO) {
            User user = new User();
            BeanUtils.copyProperties(userDTO,user);
            return user;
        }

        @Override
        protected UserDTO doBackward(User user) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            return userDTO;
        }

        @Override
        public User apply(UserDTO userDTO) {
            return null;
        }
    }
}