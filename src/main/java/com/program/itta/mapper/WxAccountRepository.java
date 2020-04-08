package com.program.itta.mapper;

import com.program.itta.domain.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxAccountRepository extends JpaRepository<UserDTO, Integer> {
        /**
         * 根据OpenId查询用户信息
         */
        UserDTO findByWxOpenid(String wxOpenId);
}
