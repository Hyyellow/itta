package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class UserTag {
    private Integer id;

    private Integer tagId;

    private Integer userId;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public UserTag() {
    }
}