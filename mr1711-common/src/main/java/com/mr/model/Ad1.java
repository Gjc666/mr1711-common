package com.mr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首页轮播图的vo类
 * Created by ydd on 2018/5/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad1 implements Serializable{

    private String srcB;
    private String alt;
    private String src;
    private String href;


    private Integer height;
    private Integer width;
    private Integer widthB;
    private Integer heightB;

}
