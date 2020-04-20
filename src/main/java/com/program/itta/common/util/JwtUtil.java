package com.program.itta.common.util;

import com.program.itta.domain.dto.UserDTO;
import com.program.itta.mapper.WxAccountRepository;

import javax.annotation.Resource;

/**
 * @program: itta
 * @description: 获取用户id
 * @author: Mr.Huang
 * @create: 2020-04-20 15:15
 **/
public class JwtUtil {
    private static final ThreadLocal<String> t1 = new ThreadLocal<>();

    @Resource
    private WxAccountRepository wxAccountRepository;

    public Integer getUserId() {
        String openid = t1.get();
        UserDTO wxAccount = wxAccountRepository.findByWxOpenid(openid);
        t1.remove();
        return wxAccount.getId();
    }
}
