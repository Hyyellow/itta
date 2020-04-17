package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Task {
    private Integer id;

    private String name;

    private Integer itemId;

    private Integer leaderId;

    private Integer securityLevel;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Task() {
    }
}