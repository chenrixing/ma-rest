package com.ma.rest.controller;

import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Collection;
import com.ma.rest.base.model.Evaluate;
import com.ma.rest.service.CollectionService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/16.
 */
@Controller
public class EvaluateController extends BaseController{

    @ApiOperation(value = "查询单个商品评价")
    @RequestMapping(value = "/getGoodsEvaluate", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult getGoodsEvaluate(@ApiParam(name = "goodid", value = "商品id", required = true)Long goodid) {
        WebJsonResult result = success();
        try {
            Evaluate evaluate1 = new Evaluate();
            evaluate1.setId(1L);
            evaluate1.setUserid(16L);
            evaluate1.setText("aaaaaaaaaaaaaaaaaaaaaaaaa");
            evaluate1.setGrade(5);
            Evaluate evaluate2 = new Evaluate();
            evaluate2.setId(1L);
            evaluate2.setUserid(16L);
            evaluate2.setText("aaaaaaaaaaaaaaaaaaaaaaaaa");
            evaluate2.setGrade(5);
            Evaluate evaluate3 = new Evaluate();
            evaluate3.setId(1L);
            evaluate3.setUserid(16L);
            evaluate3.setText("aaaaaaaaaaaaaaaaaaaaaaaaa");
            evaluate3.setGrade(5);
            List<Evaluate> list = new ArrayList<Evaluate>();
            list.add(evaluate1);
            list.add(evaluate2);
            list.add(evaluate3);
            result.setData(list);
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("查询数据异常");
        }
        return result;
    }

    @ApiOperation(value = "删除评价")
    @RequestMapping(value = "/delEvaluate", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult delEvaluate(@ApiParam(name = "id", value = "数据id", required = true)Long id) {
        WebJsonResult result = success();
        try {

            result.setMessage("删除成功");
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("系统异常");
        }
        return result;
    }
    @ApiOperation(value = "添加关注/收藏")
    @RequestMapping(value = "/insertCollections", method = RequestMethod.GET)
    @ResponseBody
    public WebJsonResult insertEvaluate(@ApiParam(name = "userid", value = "用户id", required = true)Long userid,@ApiParam(name = "goodsid", value = "商品id", required = true)Long goodsid,@ApiParam(name = "text", value = "评价内容", required = true)String text,@ApiParam(name = "grade", value = "评价等级", required = true)Integer grade) {
        WebJsonResult result = success();
        try {
            Evaluate evaluate = new Evaluate();
            evaluate.setUserid(userid);
            evaluate.setGoodid(goodsid);
            evaluate.setText(text);
            evaluate.setGrade(grade);
            System.out.println(evaluate);
            result.setMessage("添加成功");
            return result;
        }catch (Exception ex){
            result.setReturnCode(Constants.RESULT_CODE_2001);
            result.setMessage("系统异常");
        }
        return result;
    }
}
