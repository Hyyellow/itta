package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class UserItem  implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer itemId;

    private Boolean whetherLeader;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public UserItem() {
    }
}