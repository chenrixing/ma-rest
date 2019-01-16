package com.ma.rest.controller;


import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.GoodsShopCar;
import com.ma.rest.base.model.Order;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.pojo.Reply;
import com.ma.rest.pojo.ReplyDTO;
import com.ma.rest.service.OrderService;
import com.ma.rest.service.ReplyService;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李光明
 * @Title: 商品管理
 * @date 2018/6/5 9:09
 */
@Controller
@RequestMapping(value = "/comment")
public class ReplyController extends BaseController {

    @Autowired
    private ReplyService replyService;

    private static Logger logger = Logger
        .getLogger(ReplyController.class);

    @Resource
    private UserInfoService userInfoService;
    @Autowired
    private OrderService orderService;

    @Value("${MA_FILE_BASE_URL}")
    private String MA_FILE_BASE_URL;

    /**
     * 查询回复列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRepliesList", method = RequestMethod.GET)
    public List<ReplyDTO> getRepliesList(HttpServletRequest request, @ApiParam(name = "cid", value = "商品ID", required = false) @RequestParam(value = "cid", required = true) Integer cid) {
        return replyService.getReplyListByRid(cid);
    }

    @ApiOperation(value = "评价商品")
    @ResponseBody
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public WebJsonResult addComment(@ApiParam(name = "commentId", value = "商品ID", required = false) @RequestParam(value = "commentId", required = true) Integer commentId,
                                    @ApiParam(value = "订单Id", required = true) @RequestParam(value = "orderId", required = true) Integer orderId,
                                    @ApiParam(name = "replyType", value = "0:评论 1 ：子评论", required = false) @RequestParam(value = "replyType", required = true) String replyType,
                                    @ApiParam(name = "content", value = "评论内容", required = false) @RequestParam(value = "content", required = true) String content,
                                    @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
                                    @ApiParam(name = "toUid", value = "目标用户ID", required = false) @RequestParam(value = "toUid", required = true) Integer toUid,
                                    @ApiParam(name = "goodsStar", value = "产品星级：最多5颗星", required = false) @RequestParam(value = "goodsStar", required = true) Integer goodsStar,
                                    @ApiParam(name = "serviceStar", value = "服务星级：最多5颗星", required = false) @RequestParam(value = "serviceStar", required = true) Integer serviceStar,
                                    @ApiParam(name = "matchStar", value = "相似度星级：最多5颗星", required = false) @RequestParam(value = "matchStar", required = true) Integer matchStar,
                                    @ApiParam(name = "commentImg", value = "图片路径", required = false) @RequestParam(value = "commentImg", required = true) String commentImg) {
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Reply reply = new Reply();
                reply.setCommentId(commentId);
                reply.setReplyType(replyType);
                reply.setContent(content);
                reply.setFromUid(maUserInfo.getUserId());
                reply.setToUid(toUid);
                reply.setGoodsStar(goodsStar);
                reply.setServiceStar(serviceStar);
                reply.setMatchStar(matchStar);
                reply.setCommentImg(commentImg);
                int addResult = replyService.addComment(reply);
                if (addResult > 0) {
                    logger.info("add comment success;");
                    Order order = new Order();
                    order.setId(orderId);
                    order.setStatus("3");
                    Integer integer = orderService.updateOrder(order);
                  if(integer>0){
                      return result;
                  }
                } else {
                    result = error("添加失败");
                }
            }
        } catch (Exception ex) {
            logger.error("add comment Exception {}", ex);
            result = error("添加出现异常");
        }
        return result;
    }

    /**
     * 图片上传
     *
     * @return WebJsonResult
     */
    @ApiOperation(value = "图片上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public WebJsonResult uploadFile(@ApiParam(name = "uploadFile", value = "文件", required = true) @RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile) {
        WebJsonResult result = success();
        try {
            String results = userInfoService.uploadFile(uploadFile);

            if (results == null) {
                result = error("上传图片失败，请稍后再试！");
            } else {
                results = MA_FILE_BASE_URL + results;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("pic_url", results);
            result.setData(map);
        } catch (Exception ex) {
            logger.error("add comment Exception {}", ex);
            result = error("上传图片失败，请稍后再试！");
        }
        return result;
    }


}
