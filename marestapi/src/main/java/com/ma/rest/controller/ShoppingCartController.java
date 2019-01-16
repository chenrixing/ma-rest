package com.ma.rest.controller;

import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.GoodsShopCar;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.GoodsService;
import com.ma.rest.service.ShoppingCartService;
import com.ma.rest.service.UserInfoService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 李光明
 * @Title: 购物车处理类
 * @date 2018/6/2 16:29
 */
@RestController
@RequestMapping(value = "/app")
public class ShoppingCartController extends BaseController {

    private static Logger logger = Logger.getLogger(ShoppingCartController.class);
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "购物车列表")
    @RequestMapping(value = "/getShoppingCarList", method = RequestMethod.POST)
    public WebJsonResult getShoppingCartInfo(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken) {
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            WebJsonResult result = success();
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                List<GoodsShopCar> allGoods = shoppingCartService.queryShopCarGoodsList(maUserInfo.getUserId(), null);
                if (allGoods.size() > 0) {
                    for (int i = 0; i < allGoods.size(); i++) {
                        GoodsShopCar goodsShopCar = allGoods.get(i);
                        if (StringUtils.isNotEmpty(goodsShopCar.getGood().getGoodsImg())) {
                            String[] split = goodsShopCar.getGood().getGoodsImg().split(",");
                            if (CollectionUtils.isNotEmpty(Arrays.asList(split))) {
                                goodsShopCar.getGood().setGoodsImgArr(Arrays.asList(split));
                            }
                        } else {
                            goodsShopCar.getGood().setGoodsImgArr(new ArrayList<>());
                        }
                    }
                }
                result.setData(allGoods);
                return result;
            }
        } catch (Exception ex) {
            logger.error("query shopping Cart Exception {}", ex);
        }
        return error("query shopping Cart Exception {}");
    }

    @ApiOperation(value = "添加购物车")
    @RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
    public WebJsonResult addShoppingCart(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
                                         @ApiParam(value = "商品Id", required = true) @RequestParam(value = "goodsId", required = true) Integer goodsId) {
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            WebJsonResult result = success();
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                List<GoodsShopCar> goodsShopCar1 = shoppingCartService.queryShopCarGoodsList(maUserInfo.getUserId(), goodsId);
                if (goodsShopCar1.size() > 0) {
                    List<GoodsShopCar> allShopCar = shoppingCartService.getAllShopCar(maUserInfo.getUserId(), goodsId);
                    allShopCar.get(0).setNum(allShopCar.get(0).getNum() + 1);
                    shoppingCartService.updateShopCar(allShopCar.get(0));
                } else {
                    GoodsShopCar goodsShopCar = new GoodsShopCar();
                    goodsShopCar.setUid(maUserInfo.getUserId());
                    goodsShopCar.setGoodsId(goodsId);
                    goodsShopCar.setCreateTime(new Date());
                    Integer integer = shoppingCartService.addShopCar(goodsShopCar);
                }
                return result;
            }
        } catch (Exception ex) {
            logger.error("add shopping Cart Exception {}", ex);
        }
        return error("add shopping Cart Exception {}");
    }

    @ApiOperation(value = "更改购物车列表中商品数量")
    @RequestMapping(value = "/updateShoppingCartGoodsNumber", method = RequestMethod.POST)
    public WebJsonResult updateShoppingCartGoodsNumber(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
                                                       @ApiParam(value = "商品Id", required = true) @RequestParam(value = "goodsId", required = true) Integer goodsId,
                                                       @ApiParam(value = "增减", required = true) @RequestParam(value = "change", required = true) String change) {
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            WebJsonResult result = success();
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Goods goods = goodsService.selectgoodsById(goodsId);
                List<GoodsShopCar> allShopCar = shoppingCartService.getAllShopCar(maUserInfo.getUserId(), goodsId);
                Integer integer = 0;
                if (StringUtils.equals(change, Constants.SHOPCAR_ADD)) {
                    if (allShopCar.get(0).getNum() < goods.getAmount()) {
                        allShopCar.get(0).setNum(allShopCar.get(0).getNum() + 1);
                        integer = shoppingCartService.updateShopCar(allShopCar.get(0));
                    }
                }
                if (StringUtils.equals(change, Constants.SHOPCAR_JIAN)) {
                    if (allShopCar.get(0).getNum() > 1) {
                        allShopCar.get(0).setNum(allShopCar.get(0).getNum() - 1);
                        integer = shoppingCartService.updateShopCar(allShopCar.get(0));
                    }
                }
                if (integer > 0) {
                    logger.info("ADDORJIAN success;");
                    return result;
                }
            }
        } catch (Exception ex) {
            logger.error("add shopping Cart Exception {}", ex);
        }
        return error("改变数量出现异常");
    }

    @ApiOperation(value = "删除购物车商品")
    @RequestMapping(value = "/delShoppingCart", method = RequestMethod.POST)
    public WebJsonResult delShoppingCart(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
                                         @RequestParam(value = "shoppingCartGoodIds", required = true) String shoppingCartGoodIds) {
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
        MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
        if(maUserInfo==null){
            result.setMessage("用户Token失效，请重新登录");
            result.setReturnCode(Constants.RESULT_CODE_1406);
            return result;
        }else{
            Integer integer = shoppingCartService.deleteBatch(shoppingCartGoodIds, maUserInfo.getUserId());
            logger.info("delete shopping cart success;");
            return result;}
        } catch (Exception ex) {
            logger.error("delete shopping cart Exception {}", ex);
        }
        return error("delete shopping cart Exception {}");
    }
}
