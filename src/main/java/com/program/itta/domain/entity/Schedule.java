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
/*@Document(indexName = "schedule",
        useServerConfiguration = true, createIndex = false)*/
public class Schedule {
    @Id
    private Integer id;

//    @Field(type = FieldType.Integer, analyzer = "ik_max_work")
    private Integer userId;

//    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String name;

//    @Field(type = FieldType.Text, analyzer = "ik_max_work")
    private String place;

//    @Field(type = FieldType.Date, format = DateFormat.custom,
//            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date startTime;

//    @Field(type = FieldType.Date, format = DateFormat.custom,
//            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date endTime;

//    @Field(type = FieldType.Date, format = DateFormat.custom,
//            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date completionTime;

//    @Field(type = FieldType.Date, format = DateFormat.custom,
//            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

//    @Field(type = FieldType.Date, format = DateFormat.custom,
//            pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;

    @Tolerate
    public Schedule() {
    }
}