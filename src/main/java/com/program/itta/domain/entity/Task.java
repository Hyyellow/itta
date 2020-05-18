package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;
@Data
@Builder
public class Task {
    private Integer id;

    private Integer itemId;

    private Integer userId;

    private String name;

    private Integer status;

    private Integer priority;

    private Integer superId;

    private String markId;

    private Date startTime;

    private Date endTime;

    private Date completionTime;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Task() {
    }
}