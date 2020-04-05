package com.program.itta.dto;

import lombok.Data;

/**
 * @program: itta
 * @description: DTO 返回Token对象
 * @author: Mr.Huang
 * @create: 2020-04-05 11:27
 **/
@Data
public class Token {
    private String token;
    public Token(String token) {
        this.token = token;
    }
}

