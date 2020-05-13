package com.program.itta.mapper;


import com.program.itta.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxAccountRepository extends JpaRepository<User, Integer> {
        /**
         * 根据OpenId查询用户信息
         */
        User findByWxOpenid(String wxOpenId);
}
