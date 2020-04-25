package com.program.itta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Builder
@Document(indexName = "item",
        useServerConfiguration = true, createIndex = false)
public class Item {
    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String serialNumber;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String taskPrefix;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String color;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String actionScope;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String groupName;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String description;

    @Field(type = FieldType.Integer, analyzer = "ik_max_work")
    private Integer leaderId;

    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String markId;

    @Field(type = FieldType.Date, format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.custom,
            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;

    @Tolerate
    public Item() {
    }
}