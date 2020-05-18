package com.program.itta.domain.entity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import	java.io.DataOutput;

import java.util.Date;

@Data
@Builder
public class News {
    private Integer id;

    private Integer senderId;

    private Integer recipientId;

    private Boolean isUser;

    private String content;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public News() {
    }
}