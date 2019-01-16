package com.ma.rest.controller;

import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Collection;
import com.ma.rest.base.model.GoodsCategory;
import com.ma.rest.service.CollectionService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2019/1/16.
 */
@Controller
public class CollectionController extends BaseController{
    @Autowired
    private CollectionService collectionService;
    @ApiOperation(value = "查询单个用户收藏的所有商品")
    @RequestMapping(value = "/getUserCollectionCommodity", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getCollectionList(@ApiParam(name = "userid", value = "用户id", required = true)Long userid) {
        WebJsonResult result = success();
        try {
            List<Collection> collectionList=collectionService.selectCollections(userid);
            result.setData(collectionList);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
        }
        return result;
    }
    @ApiOperation(value = "查询单个用户关注的所有商家")
    @RequestMapping(value = "/getUserCollectionMerchant", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getFollowList(@ApiParam(name = "userid", value = "用户id", required = true)Long userid) {
        WebJsonResult result = success();
        try {
            List<Collection> collectionList=collectionService.selectFollow(userid);
            result.setData(collectionList);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
        }
        return result;
    }
    @ApiOperation(value = "取消关注/收藏")
    @RequestMapping(value = "/delCollection", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult delCollection(@ApiParam(name = "id", value = "数据id", required = true)Long id) {
        WebJsonResult result = success();
        try {
            collectionService.deleteByKey(id);
            result.setMessage("删除成功");
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("系统异常");
        }
        return result;
    }
    @ApiOperation(value = "添加关注/收藏")
    @RequestMapping(value = "/insertCollection", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult insertCollection(@ApiParam(name = "userid", value = "用户id", required = true)Long userid,@ApiParam(name = "businessid", value = "商家/商品id", required = true)Long businessid,@ApiParam(name = "state", value = "1:收藏，2：关注", required = true)Integer state) {
        WebJsonResult result = success();
        try {
            Collection collection = new Collection();
            collection.setUserid(userid);
            collection.setBusinessid(businessid);
            collection.setState(state);
            collectionService.insert(collection);
            result.setMessage("添加成功");
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("系统异常");
        }
        return result;
    }
}
