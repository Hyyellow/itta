package com.program.itta.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: itta
 * @description: 微信信息实体类
 * @author: Mr.Huang
 * @create: 2020-04-05 11:19
 **/
@Entity
@Table
@Data
public class WxAccount {
    @Id
    @GeneratedValue
    private Integer id;
    private String wxOpenid;
    private String sessionKey;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
