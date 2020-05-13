package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Tag {
    private Integer id;

    private String content;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Tag() {
    }
}

