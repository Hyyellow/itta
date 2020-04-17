package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class UserTask {
    private Integer id;

    private Integer userId;

    private Integer taskId;

    private Boolean leader;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public UserTask() {
    }
}