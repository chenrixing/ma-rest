package com.ma.rest.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 李光明
 * @Title: 用户实体
 * @date 2018/5/28 15:25
 */
@Data
public class MaUserInfo {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户账号
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户token
     */
    private String userToken;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * app版本
     */
    private String appVersion;

    /**
     * 是否启用,0启用 ,1禁用
     */
    private Byte status;

    /**
     * 头像
     */
    private String headImg;


    /**
     * 印象图
     */
    private String backImg;

    /**
     * 用户平台类型
     */
    private  Integer platform;
    /**
     * 用户签名
     */
    private String sign;
    /**
     * 分类Id
     */
    private String categoryId;
    /**
     * 用户类型
     */
    private String type;
    /**
     * 商家介绍
     */
    private String businessIntroduction;
    /**
     * 是否精选
     */
    private Byte isChoice;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessIntroduction() {
        return businessIntroduction;
    }

    public void setBusinessIntroduction(String businessIntroduction) {
        this.businessIntroduction = businessIntroduction;
    }

    public Byte getIsChoice() {
        return isChoice;
    }

    public void setIsChoice(Byte isChoice) {
        this.isChoice = isChoice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}