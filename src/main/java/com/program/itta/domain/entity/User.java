package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@Builder
@Entity
/*@Document(indexName = "user",
        useServerConfiguration = true, createIndex = false)*/
public class User  implements Serializable {
    @Id
    @GeneratedValue
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