package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Timer {
    private Integer id;

    private Integer scheduleId;

    private String year;

    private String month;

    private String week;

    private String day;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public Timer() {
    }
}