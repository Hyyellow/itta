package com.program.itta.mapper;

import com.program.itta.dto.UserDTO;
import com.program.itta.dto.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxAccountRepository extends JpaRepository<UserDTO, Integer> {
        /**
         * 根据OpenId查询用户信息
         */
        UserDTO findByWxOpenid(String wxOpenId);
}
