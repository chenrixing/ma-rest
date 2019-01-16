package com.ma.rest.base.model;

import com.ma.rest.pojo.MaUserInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.order_number
     *
     * @mbggenerated
     */
    private String orderNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.uid
     *
     * @mbggenerated
     */
    private Integer uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.pay_price
     *
     * @mbggenerated
     */
    private BigDecimal payPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.is_pay
     *
     * @mbggenerated
     */
    private String isPay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.pay_time
     *
     * @mbggenerated
     */
    private Date payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.is_ship
     *
     * @mbggenerated
     */
    private String isShip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.ship_time
     *
     * @mbggenerated
     */
    private Date shipTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.is_receipt
     *
     * @mbggenerated
     */
    private String isReceipt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.receipt_time
     *
     * @mbggenerated
     */
    private Date receiptTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.ship_nmber
     *
     * @mbggenerated
     */
    private String shipNmber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ma_order.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ma_order
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.id
     *
     * @return the value of ma_order.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.id
     *
     * @param id the value for ma_order.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.order_number
     *
     * @return the value of ma_order.order_number
     *
     * @mbggenerated
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.order_number
     *
     * @param orderNumber the value for ma_order.order_number
     *
     * @mbggenerated
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.uid
     *
     * @return the value of ma_order.uid
     *
     * @mbggenerated
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.uid
     *
     * @param uid the value for ma_order.uid
     *
     * @mbggenerated
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.is_pay
     *
     * @return the value of ma_order.is_pay
     *
     * @mbggenerated
     */
    public String getIsPay() {
        return isPay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.is_pay
     *
     * @param isPay the value for ma_order.is_pay
     *
     * @mbggenerated
     */
    public void setIsPay(String isPay) {
        this.isPay = isPay == null ? null : isPay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.pay_time
     *
     * @return the value of ma_order.pay_time
     *
     * @mbggenerated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.pay_time
     *
     * @param payTime the value for ma_order.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.is_ship
     *
     * @return the value of ma_order.is_ship
     *
     * @mbggenerated
     */
    public String getIsShip() {
        return isShip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.is_ship
     *
     * @param isShip the value for ma_order.is_ship
     *
     * @mbggenerated
     */
    public void setIsShip(String isShip) {
        this.isShip = isShip == null ? null : isShip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.ship_time
     *
     * @return the value of ma_order.ship_time
     *
     * @mbggenerated
     */
    public Date getShipTime() {
        return shipTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.ship_time
     *
     * @param shipTime the value for ma_order.ship_time
     *
     * @mbggenerated
     */
    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.is_receipt
     *
     * @return the value of ma_order.is_receipt
     *
     * @mbggenerated
     */
    public String getIsReceipt() {
        return isReceipt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.is_receipt
     *
     * @param isReceipt the value for ma_order.is_receipt
     *
     * @mbggenerated
     */
    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt == null ? null : isReceipt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.receipt_time
     *
     * @return the value of ma_order.receipt_time
     *
     * @mbggenerated
     */
    public Date getReceiptTime() {
        return receiptTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.receipt_time
     *
     * @param receiptTime the value for ma_order.receipt_time
     *
     * @mbggenerated
     */
    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.ship_nmber
     *
     * @return the value of ma_order.ship_nmber
     *
     * @mbggenerated
     */
    public String getShipNmber() {
        return shipNmber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.ship_nmber
     *
     * @param shipNmber the value for ma_order.ship_nmber
     *
     * @mbggenerated
     */
    public void setShipNmber(String shipNmber) {
        this.shipNmber = shipNmber == null ? null : shipNmber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.status
     *
     * @return the value of ma_order.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.status
     *
     * @param status the value for ma_order.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.create_time
     *
     * @return the value of ma_order.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.create_time
     *
     * @param createTime the value for ma_order.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ma_order.update_time
     *
     * @return the value of ma_order.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ma_order.update_time
     *
     * @param updateTime the value for ma_order.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    private Integer addressId;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address address;
    private Goods goods;


    private String invoiceInfo;
    private String goodsOtherInfo;
    private String expressStatus;
    private Integer goodsNumber;
    private Integer sellerUid;
    private String nickname;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(Integer sellerUid) {
        this.sellerUid = sellerUid;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getGoodsOtherInfo() {
        return goodsOtherInfo;
    }

    public void setGoodsOtherInfo(String goodsOtherInfo) {
        this.goodsOtherInfo = goodsOtherInfo;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    private Integer goodsTotalNumber;
    private BigDecimal TotalPrice;

    public Integer getGoodsTotalNumber() {
        return goodsTotalNumber;
    }

    public void setGoodsTotalNumber(Integer goodsTotalNumber) {
        this.goodsTotalNumber = goodsTotalNumber;
    }

    public BigDecimal getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        TotalPrice = totalPrice;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_order
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getPayPrice() == null ? other.getPayPrice() == null : this.getPayPrice().equals(other.getPayPrice()))
            && (this.getIsPay() == null ? other.getIsPay() == null : this.getIsPay().equals(other.getIsPay()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getIsShip() == null ? other.getIsShip() == null : this.getIsShip().equals(other.getIsShip()))
            && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
            && (this.getIsReceipt() == null ? other.getIsReceipt() == null : this.getIsReceipt().equals(other.getIsReceipt()))
            && (this.getReceiptTime() == null ? other.getReceiptTime() == null : this.getReceiptTime().equals(other.getReceiptTime()))
            && (this.getShipNmber() == null ? other.getShipNmber() == null : this.getShipNmber().equals(other.getShipNmber()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_order
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getPayPrice() == null) ? 0 : getPayPrice().hashCode());
        result = prime * result + ((getIsPay() == null) ? 0 : getIsPay().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getIsShip() == null) ? 0 : getIsShip().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getIsReceipt() == null) ? 0 : getIsReceipt().hashCode());
        result = prime * result + ((getReceiptTime() == null) ? 0 : getReceiptTime().hashCode());
        result = prime * result + ((getShipNmber() == null) ? 0 : getShipNmber().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ma_order
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", uid=").append(uid);
        sb.append(", payPrice=").append(payPrice);
        sb.append(", isPay=").append(isPay);
        sb.append(", payTime=").append(payTime);
        sb.append(", isShip=").append(isShip);
        sb.append(", shipTime=").append(shipTime);
        sb.append(", isReceipt=").append(isReceipt);
        sb.append(", receiptTime=").append(receiptTime);
        sb.append(", shipNmber=").append(shipNmber);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}