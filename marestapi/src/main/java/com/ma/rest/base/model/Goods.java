package com.ma.rest.base.model;

import com.ma.rest.pojo.Comment;
import com.ma.rest.pojo.MaUserInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李光明
 * @Title: 商品bean
 * @date 2018/7/18 16:54
 *
 **/
@Data
public class Goods {
    //商品ID
    private Integer goodsId;
    //商品名称
    private String goodsName;
    //副标题
    private String goodsTitle;
    //库存
    private Integer amount;
    //图片
    private String goodsImg;
    //状态
    private Byte status;
    //添加时间
    private String addTime;
    //分类
    private String categoryId;
    //价格
    private BigDecimal price;
    //商品图片详情
    private String picDetails;
    //商品参数
    private String paramDetails;
    //优惠价
    private BigDecimal discountPrice;
    //商品类型
    private Byte memberType;
    //是否精选
    private Byte isChoice;
    //修改时间
    private String modTime;
    //商品图片
    private List<String> goodsImgArr;
    //商户Id
    private Integer userId;
    //一对一对应商户
    private MaUserInfo maUserInfo;
    //一对多对应评论
    private List<Comment> comments;
    //商品数量
    private Integer goodsNumber;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicDetails() {
        return picDetails;
    }

    public void setPicDetails(String picDetails) {
        this.picDetails = picDetails;
    }

    public String getParamDetails() {
        return paramDetails;
    }

    public void setParamDetails(String paramDetails) {
        this.paramDetails = paramDetails;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Byte getIsChoice() {
        return isChoice;
    }

    public void setIsChoice(Byte isChoice) {
        this.isChoice = isChoice;
    }

    public String getModTime() {
        return modTime;
    }

    public void setModTime(String modTime) {
        this.modTime = modTime;
    }

    public List<String> getGoodsImgArr() {
        return goodsImgArr;
    }

    public void setGoodsImgArr(List<String> goodsImgArr) {
        this.goodsImgArr = goodsImgArr;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public MaUserInfo getMaUserInfo() {
        return maUserInfo;
    }

    public void setMaUserInfo(MaUserInfo maUserInfo) {
        this.maUserInfo = maUserInfo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
