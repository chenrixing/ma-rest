package com.ma.rest.base.model;

import java.util.Date;

/**
 * @Author 施龙飞
 * Description
 * 轮播图的业务实体
 * Create in2018-07-09
 */
public class Round {

    private String rouUuid;
    private String rouDetai;
    private String rouPicurl;
    private String rouStatus;
    private Date rouUploadtime;
    private Date rouUpdatetime;
    private Integer goodsId;


    public String getRouUuid() {
        return rouUuid;
    }

    public void setRouUuid(String rouUuid) {
        this.rouUuid = rouUuid;
    }

    public String getRouDetai() {
        return rouDetai;
    }

    public void setRouDetai(String rouDetai) {
        this.rouDetai = rouDetai;
    }

    public String getRouPicurl() {
        return rouPicurl;
    }

    public void setRouPicurl(String rouPicurl) {
        this.rouPicurl = rouPicurl;
    }

    public String getRouStatus() {
        return rouStatus;
    }

    public void setRouStatus(String rouStatus) {
        this.rouStatus = rouStatus;
    }

    public Date getRouUploadtime() {
        return rouUploadtime;
    }

    public void setRouUploadtime(Date rouUploadtime) {
        this.rouUploadtime = rouUploadtime;
    }

    public Date getRouUpdatetime() {
        return rouUpdatetime;
    }

    public void setRouUpdatetime(Date rouUpdatetime) {
        this.rouUpdatetime = rouUpdatetime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
}
