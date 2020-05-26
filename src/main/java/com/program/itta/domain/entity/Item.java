package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class Item  implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Integer actionScope;

    private String markId;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Item() {
    }
}