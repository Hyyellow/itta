package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Item {
    private Integer id;

    private String name;

    private String serialNumber;

    private String taskPrefix;

    private String color;

    private String actionScope;

    private String groupName;

    private String description;

    private Integer userId;

    private String markId;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Item() {
    }
}