package com.ma.rest.base.model;

/**
 * Created by Administrator on 2019/1/16.
 */



public class Collection {
    //数据索引
    private Long id;
    //用户
    private Long userid;
    //商家
    private Long businessid;
    //关注/收藏
    private Integer state;

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

    public Long getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Long businessid) {
        this.businessid = businessid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
