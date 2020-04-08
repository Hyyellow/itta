package com.program.itta.domain.dto;

import lombok.Data;

/**
 * @program: itta
 * @description: 微信小程序 Code2Session 接口返回值 对象
 * @author: Mr.Huang
 * @create: 2020-04-05 12:17
 **/
@Data
public class Code2SessionResponse {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode = "0";
    private String errmsg;
    private int expires_in;
}
