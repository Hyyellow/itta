package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class TaskTag  implements Serializable {
    private Integer id;

    private Integer taskId;

    private Integer tagId;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public TaskTag() {
    }
}