package com.mongodb.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;


@Document(collection = "blog")
@CompoundIndex(def = "{'userid': 1, 'likenum': -1}")
@Data
public class Blog implements Serializable {


    @Id
    private String id;

    @Field("content")
    private String content;

    @Indexed
    private String userid;

    private Integer starnum;

    private Date createtime;


}
