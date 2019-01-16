package com.ma.rest.base.model;

/**
 * Created by Administrator on 2019/1/16.
 */



public class Evaluate {
    //数据索引
    private Long id;
    //用户id
    private Long userid;
    //商品
    private Long goodid;
    //评价内容
    private String text;
    //评价等级
    private Integer grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getGoodid() {
        return goodid;
    }

    public void setGoodid(Long goodid) {
        this.goodid = goodid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
