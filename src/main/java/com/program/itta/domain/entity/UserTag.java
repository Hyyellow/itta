package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class UserTag implements Comparable<UserTag>, Serializable {
    private Integer id;

    private Integer tagId;

    private Integer userId;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public UserTag() {
    }

    @Override
    public int compareTo(UserTag o) {
        if (o.number > this.number) {
            return 1;
        } else if (o.number.equals(this.number)) {
            return 0;
        } else {
            return -1;
        }
    }
}