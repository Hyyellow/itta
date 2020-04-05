package com.program.itta.dto;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: itta
 * @description: DTO 鉴权用的token vo ，实现 AuthenticationToken
 * @author: Mr.Huang
 * @create: 2020-04-05 11:35
 **/
public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
