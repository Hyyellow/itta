package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class TaskInfo {
    private Integer id;

    private Integer taskId;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date completionTime;

    private Integer priority;

    private Integer label;

    private String description;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public TaskInfo() {
    }
}