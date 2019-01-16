package com.ma.rest.service.impl;


import com.ma.rest.mapper.dao.ReplyMapper;
import com.ma.rest.pojo.Reply;
import com.ma.rest.pojo.ReplyDTO;
import com.ma.rest.pojo.ReplyType;
import com.ma.rest.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in10:11 2018/7/3 0003
 **/
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Override
    public List<ReplyDTO> getReplyListByRid(Integer cid) {
        List<Reply> ReplyList = replyMapper.queryReplyByCid(cid);
        if (ReplyList == null || ReplyList.size() == 0) {
            return new ArrayList<>();
        }

        List<ReplyDTO> replyDTOList = new ArrayList<ReplyDTO>();
        List<ReplyDTO> parentList = new ArrayList<ReplyDTO>();
        for (Reply Reply : ReplyList) {
            ReplyDTO replyDTO = convertReplyToDTO(Reply);
            if (replyDTO.getReplyType().equals(ReplyType.COMMENT)) {
                replyDTOList.add(replyDTO);
                parentList.add(replyDTO);
            } else {
                boolean foundParent = false;
                if (replyDTOList.size() > 0) {
                    for (ReplyDTO parent : parentList) {
                    if (parent.getId().equals(replyDTO.getReplyId())) {
                        if (parent.getNext() == null) {
                            parent.setNext(new ArrayList<ReplyDTO>());
                        }
                        parent.getNext().add(replyDTO);
                        parentList.add(replyDTO);
                        foundParent = true;
                        break;
                    }
                }
            }
                if (!foundParent) {
                    throw new RuntimeException("sort reply error,should not go here.");
                }
            }
        }
        return replyDTOList;
    }

    @Override
    public int addComment(Reply reply) {
        return replyMapper.insertSelective(reply);
    }

    private ReplyDTO convertReplyToDTO(Reply reply){
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setCommentId(reply.getCommentId());
        replyDTO.setContent(reply.getContent());
        replyDTO.setFromUid(reply.getFromUid());
        replyDTO.setId(reply.getId());
        replyDTO.setReplyId(reply.getReplyId());
        replyDTO.setReplyTime(reply.getReplyTime());
        replyDTO.setReplyType(reply.getReplyType());
        replyDTO.setToUid(reply.getToUid());
        return replyDTO;
    }
}
