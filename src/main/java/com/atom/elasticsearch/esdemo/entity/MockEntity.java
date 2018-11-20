package com.atom.elasticsearch.esdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @program: es-demo
 * @description:
 * @author: Maxxx.Yg
 * @create: 2018-11-01 14:06
 **/
@Document(
        indexName = "mock_index_name", // 必须小写
        type = "mock_type",
        shards = 1,
        replicas = 1,
        refreshInterval = "-1"
)
public class MockEntity {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String userName;

    @Field(type = FieldType.Date)
    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
