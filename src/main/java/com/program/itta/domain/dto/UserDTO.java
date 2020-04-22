package com.program.itta.domain.dto;

import com.program.itta.common.convert.BaseDTOConvert;
import com.program.itta.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @program: itta
 * @description: 用户DTO
 * @author: Mr.Huang
 * @create: 2020-04-04 16:00
 **/
@Data
@Builder
@Entity
@Table(name = "user")
public class UserDTO {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String email;

    private String phone;

    @NotBlank(message = "openId不可为空")
    private String wxOpenid;

    @NotBlank(message = "sessionKey不可为空")
    private String sessionKey;

    private Date lastTime;

    private String markId;

    @Tolerate
    public UserDTO() {
    }

    public User convertToUser() {
        UserBaseDTOConvert userDTOConvert = new UserBaseDTOConvert();
        User convert = userDTOConvert.doForward(this);
        return convert;
    }

    public UserDTO convertFor(User user) {
        UserBaseDTOConvert userDTOConvert = new UserBaseDTOConvert();
        UserDTO convert = userDTOConvert.doBackward(user);
        return convert;
    }

    private static class UserBaseDTOConvert extends BaseDTOConvert<UserDTO, User> {
        @Override
        protected User doForward(UserDTO userDTO) {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            return user;
        }

        @Override
        protected UserDTO doBackward(User user) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }

        @Override
        public User apply(UserDTO userDTO) {
            return null;
        }
    }
}
