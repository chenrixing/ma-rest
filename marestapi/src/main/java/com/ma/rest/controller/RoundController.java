package com.ma.rest.controller;

import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Round;
import com.ma.rest.service.RoundService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @Author 施龙飞
 * Description
 * 轮播图
 * Create in2018-07-09
 */
@RestController
@RequestMapping("/app")
public class RoundController extends BaseController{
    private static Logger logger = Logger.getLogger(ShoppingCartController.class);
    @Autowired
    private RoundService roundService;



    @ApiOperation(value = "获取展示的轮播图")
    @RequestMapping(value = "/queryAllShowRound",method = RequestMethod.GET)
    public WebJsonResult queryAllShouwRound() {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        WebJsonResult result = success();
        List<Round> roundList= null;
        try {
            roundList = roundService.queryAllShowRound();
//            for(Round round:roundList){
//                StringBuilder max = new StringBuilder();
//                String img=round.getRouPicurl();
//                max.append(img.substring(0, img.lastIndexOf(".")))
//                    .append(Constants.IMAGE_1000x1000)
//                    .append(img.substring(img.lastIndexOf(".") + 1));
//                round.setRouPicurl(max.toString());
//            }
            result.setData(roundList);
        } catch (Exception e) {
            logger.error("获取轮播图失败", e);
            result=error("获取轮播图失败");
        }
        return result;
    }
}
