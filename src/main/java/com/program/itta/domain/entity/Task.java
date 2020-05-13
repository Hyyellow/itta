package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Builder
/*@Document(indexName = "task",
        useServerConfiguration = true, createIndex = false)*/
public class Task {
    private Integer id;

    private String name;

    private Integer itemId;

    private Integer founderId;

    private Integer leaderId;

    private Integer securityLevel;

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
    public Task() {
    }
}