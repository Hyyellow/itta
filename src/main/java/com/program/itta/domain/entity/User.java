package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
@Data
@Builder
/*@Document(indexName = "user",
        useServerConfiguration = true, createIndex = false)*/
public class User {
    private Integer id;

    private String name;

    private String picture;

    private String wxOpenid;

    private String sessionKey;

    private Date lastTime;

    private String markId;

    private Date createTime;

    private Date updateTime;

    @Tolerate
    public User() {
    }
}