package com.ma.rest.service;




import com.ma.rest.pojo.Reply;
import com.ma.rest.pojo.ReplyDTO;

import java.util.List;
import java.util.Map;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:10 2018/7/3 0003
 **/
public interface ReplyService {
    List<ReplyDTO> getReplyListByRid(Integer cid);

    int addComment(Reply reply);
}
