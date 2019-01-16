package com.ma.rest.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:17 2018/7/3 0003
 **/
public class ReplyDTO implements Serializable {
    private Integer id;

    private Integer commentId;

    private Integer replyId;

    private String replyType;

    private String content;

    private Integer fromUid;

    private Integer toUid;

    private Date replyTime;
    List<ReplyDTO> next;

    public List<ReplyDTO> getNext() {
        return next;
    }

    public void setNext(List<ReplyDTO> next) {
        this.next = next;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}
