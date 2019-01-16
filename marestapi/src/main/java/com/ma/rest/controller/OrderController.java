package com.ma.rest.controller;


import com.ma.common.utils.AESUtils;
import com.ma.common.utils.Constants;
import com.ma.common.utils.StringUtils;
import com.ma.common.utils.WebJsonResult;
import com.ma.rest.base.model.Goods;
import com.ma.rest.base.model.Order;
import com.ma.rest.base.model.Serial;
import com.ma.rest.dao.JedisClient;
import com.ma.rest.pojo.MaUserInfo;
import com.ma.rest.service.*;
import com.ma.rest.utils.DateUtil;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 施龙飞
 * Description
 * 订单
 * Create in2018-07-09
 */
@RestController
@RequestMapping("/app")
public class OrderController extends BaseController {
    private static Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private RelOrderGoodsService relOrderGoodsService;
    @Autowired
    private SerialService serialService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private JedisClient jedis;

    @RequestMapping(value = "/toordermanager", method = RequestMethod.POST)
    public String tousermanage() {
        return "power/order";
    }

    @ResponseBody
    @RequestMapping(value = "/getOrderNumber", method = RequestMethod.GET)
    public String getShoppingCartInfo() {
        Serial serial = serialService.selectSerial();
        int maxNum = 0;
        if (serial == null) {

        } else {
            maxNum = serial.getSerialNumber();
        }
        return serialService.getSerialNumber(maxNum);
    }

    @ApiOperation(value = "获取用户订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public WebJsonResult getOrderList(
        @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
        @ApiParam(value = "订单状态", required = false) @RequestParam(value = "status", required = false) String status,
        @ApiParam(value = "当前页", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @ApiParam(value = "条数", required = false) @RequestParam(value = "limit", required = false, defaultValue = "10000") Integer limit) {
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Integer start = (page - 1) * limit;
                List<Order> allOrder = orderService.getAllOrder(maUserInfo.getUserId(), start, limit, status);
                for (Order order : allOrder) {
                    Goods goods1 = order.getGoods();
                    List<Goods> goodsList = new ArrayList<Goods>();
                    goodsList.add(goods1);
                    BigDecimal sumPrice = BigDecimal.ZERO;
                    for (Goods goods : goodsList) {
                        if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
                            String[] split = goods.getGoodsImg().split(",");
                            if (CollectionUtils.isNotEmpty(Arrays.asList(split))) {
                                goods.setGoodsImgArr(Arrays.asList(split));
                            }
                        } else {
                            goods.setGoodsImgArr(new ArrayList<>());
                        }
                        sumPrice = sumPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getGoodsNumber())));
                        //保留两位小数
                        order.setTotalPrice(sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                        order.setGoodsTotalNumber(goods.getGoodsNumber());
                    }
                }
                Map<String, Object> params1 = new HashMap<>();
                params1.put("pageSize", limit);
                params1.put("nowPage", page);
                params1.put("totalPages", (orderService.queryAllRows(maUserInfo.getUserId(), status) - 1) / limit + 1);
                params1.put("data", allOrder);
                result.setData(params1);
                logger.info("getOrderList success;");
                return result;
            }
        } catch (Exception ex) {
            logger.error("query Order Exception {}", ex);
        }
        return error("获取用户订单列表出现异常");
    }

    @ApiOperation(value = "获取商家消费列表")
    @RequestMapping(value = "/getSellerOrderList", method = RequestMethod.POST)
    public WebJsonResult getSellerOrderList(
        @ApiParam(value = "商家用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
        @ApiParam(value = "订单状态", required = false) @RequestParam(value = "status", required = false) String status,
        @ApiParam(value = "日期筛选条件", required = false) @RequestParam(value = "dateTime", required = false) String dateTime,
        @ApiParam(value = "当前页", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @ApiParam(value = "条数", required = false) @RequestParam(value = "limit", required = false, defaultValue = "10000") Integer limit) {
        WebJsonResult result = success();
        List<Order> allOrder=new ArrayList<>();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            Map<String,Object> map=new HashMap<>();
            if(StringUtils.isEmpty(dateTime)){
                dateTime="0";
            }
           map = getTime(dateTime);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Integer start = (page - 1) * limit;
                Object beginTime = map.get("beginTime");
                Object endTime = map.get("endTime");
                if(beginTime!=null&&endTime!=null){
                    allOrder = orderService.getSellerAllOrder(maUserInfo.getUserId(), start, limit, status,map.get("beginTime").toString(),map.get("endTime").toString());
                }else{
                    allOrder = orderService.getSellerAllOrder(maUserInfo.getUserId(), start, limit, status,null,null);
                }
                for (Order order : allOrder) {
                    Goods goods1 = order.getGoods();
                    List<Goods> goodsList = new ArrayList<Goods>();
                    goodsList.add(goods1);
                    BigDecimal sumPrice = BigDecimal.ZERO;
                    for (Goods goods : goodsList) {
                        if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
                            String[] split = goods.getGoodsImg().split(",");
                            if (CollectionUtils.isNotEmpty(Arrays.asList(split))) {
                                goods.setGoodsImgArr(Arrays.asList(split));
                            }
                        } else {
                            goods.setGoodsImgArr(new ArrayList<>());
                        }
                        sumPrice = sumPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getGoodsNumber())));
                        //保留两位小数
                        order.setTotalPrice(sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                        order.setGoodsTotalNumber(goods.getGoodsNumber());
                    }
                }
                Map<String, Object> params1 = new HashMap<>();
                params1.put("pageSize", limit);
                params1.put("nowPage", page);
                params1.put("totalPages", 0);
                params1.put("data", allOrder);
                result.setData(params1);
                logger.info("getOrderList success;");
                return result;
            }
        } catch (Exception ex) {
            logger.error("query Order Exception {}", ex);
        }
        return error("获取商家消费列表出现异常");
    }

    @ApiOperation(value = "获取用户下订单详情")
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    public WebJsonResult getOrderDetail(@ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
                                        @ApiParam(value = "订单Id", required = true) @RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Order order = orderService.selectOrderDetailById(maUserInfo.getUserId(), orderId);
                if(order.getGoods()!=null &&order.getGoods().getGoodsImg()!=null){
                    String[] split = order.getGoods().getGoodsImg().split(",");
                    if(CollectionUtils.isNotEmpty(Arrays.asList(split))){
                        order.getGoods().setGoodsImgArr(Arrays.asList(split));
                    }
                }

                order.setGoodsTotalNumber(order.getGoodsNumber());
                order.setTotalPrice(order.getPayPrice());

                result.setData(order);
                return result;
            }
        } catch (Exception ex) {
            logger.error("query user order Deatils Exception {}", ex);
        }
        return error("获取用户下订单详情出现异常");
    }

    @ApiOperation(value = "获取用户订单商品列表")
    @RequestMapping(value = "/getOrderGoodsList", method = RequestMethod.GET)
    public Map<String, Object> getOrderGoodsList(@ApiParam(value = "订单Id", required = true) @RequestParam(value = "orderId", required = true) Integer orderId) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            List<Goods> goodsList = goodsService.selectOrderGoodsList(orderId);
            for (Goods goods : goodsList) {
                if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
                    String[] split = goods.getGoodsImg().split(",");
                    if (CollectionUtils.isNotEmpty(Arrays.asList(split))) {
                        goods.setGoodsImgArr(Arrays.asList(split));
                    }
                } else {
                    goods.setGoodsImgArr(new ArrayList<>());
                }
            }
            resultmap.put("code", 200);
            resultmap.put("message", "success");
            resultmap.put("count", goodsList.size());
            resultmap.put("data", goodsList);
            logger.info("getOrderList success;");
        } catch (Exception ex) {
            logger.error("query Order Exception {}", ex);
        }

        return resultmap;
    }


    @ApiOperation(value = "生成订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public WebJsonResult ceateOrder(
        @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
        @ApiParam(value = "商品Id", required = true) @RequestParam(value = "goodsIds", required = true) String goodsIds,
        @ApiParam(value = "商品数量Id", required = true) @RequestParam(value = "goodsNumbers", required = true) String goodsNumbers,
        @ApiParam(value = "收货地址Id", required = true) @RequestParam(value = "addressId", required = true) Integer addressId) {
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                Map<String, Object> map = relOrderGoodsService.insertRelOrderGoods(goodsIds, goodsNumbers, maUserInfo.getUserId(), addressId);
                if (Integer.parseInt(map.get("result").toString()) > 0) {
                    Object orderNumber = map.get("orderNumber");
                    Object allOrderNumber = map.get("allOrderNumber");
                    Object toralPrice = map.get("toralPrice");
                    result.setMessage("订单生成成功");
                    result.setData(map);
                    return result;
                } else {
                    result.setMessage("订单生成失败");
                }
                logger.info("createOrder success;");
            }
        } catch (Exception ex) {
            logger.error("createOrder Exception {}", ex);
        }
        return error("生成订单列表出现异常");
    }

    @ApiOperation(value = "生成商品列表缓存")
    @RequestMapping(value = "/orderCache", method = RequestMethod.POST)
    public WebJsonResult orderCache(
        @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken,
        @ApiParam(value = "商品Id", required = true) @RequestParam(value = "goodsIds", required = true) String goodsIds,
        @ApiParam(value = "商品数量Id", required = true) @RequestParam(value = "goodsNumbers", required = true) String goodsNumbers,
        @ApiParam(value = "缓存类型", required = true) @RequestParam(value = "cacheType", required = true) String cacheType) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            boolean set = false;
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(goodsIds).append("_").append(goodsNumbers).append("_").append(cacheType);
                set = jedis.set(maUserInfo.getUserId().toString(), sb.toString());
                if (set) {
                    result.setMessage("商品列表缓存成功");
                    return result;
                } else {
                    return error("商品列表缓存失败");
                }
            }
        } catch (Exception ex) {
            logger.error("createOrder Exception {}", ex);
        }
        return error("商品列表缓存发生异常");
    }

    @ApiOperation(value = "取出商品列表缓存")
    @RequestMapping(value = "/getOrderCache", method = RequestMethod.POST)
    public WebJsonResult orderCache(
        @ApiParam(value = "用户Token", required = true) @RequestParam(value = "userToken", required = true) String userToken) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        WebJsonResult result = success();
        try {
            userToken = AESUtils.aesDecrypt(userToken, Constants.IOS_UserInfo_TOKEN_PWD);
            MaUserInfo maUserInfo = userInfoService.queryByToken(userToken);
            if (maUserInfo == null) {
                result.setMessage("用户Token失效，请重新登录");
                result.setReturnCode(Constants.RESULT_CODE_1406);
                return result;
            } else {
                String s = jedis.get(maUserInfo.getUserId().toString());
                String[] split = s.split("_");
                List<String> strings = Arrays.asList(split);
                String goodsIds = strings.get(0);
                String goodsNumbers = strings.get(1);
                String cacheType = strings.get(2);
                String[] split3 = goodsNumbers.split(",");
                List<String> strings2 = Arrays.asList(split3);
                List<Goods> goodsList = new ArrayList<Goods>();
                String[] split1 = goodsIds.split(",");
                List<String> strings1 = Arrays.asList(split1);
                BigDecimal sumPrice = BigDecimal.ZERO;
                for (int i = 0; i < strings1.size(); i++) {
                    Goods goods = goodsService.selectgoodsById(Integer.parseInt(strings1.get(i)));
                    sumPrice = sumPrice.add(goods.getPrice().multiply(new BigDecimal(strings2.get(i))));
                    goods.setGoodsNumber(Integer.parseInt(strings2.get(i)));
                    if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
                        String[] split2 = goods.getGoodsImg().split(",");
                        if (CollectionUtils.isNotEmpty(Arrays.asList(split2))) {
                            goods.setGoodsImgArr(Arrays.asList(split2));
                        }
                    } else {
                        goods.setGoodsImgArr(new ArrayList<>());
                    }
                    goodsList.add(goods);
                }
                resultmap.put("goodsList", goodsList);
                //保留两位小数
                resultmap.put("totalPrice", sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                if (userToken.length() > 0) {
                    result.setMessage("取出商品列表缓存成功");
                    result.setData(resultmap);
                    return result;
                } else {
                    return error("取出商品列表缓存失败");
                }
            }
        } catch (Exception ex) {
            logger.error("createOrder Exception {}", ex);
        }
        return error("取出商品列表缓存出现异常");
    }

    @ResponseBody
    @RequestMapping(value = "/order/updateOrder", method = RequestMethod.POST)
    public Map<String, Object> updateOrder(Integer id, String orderNumber, String payPrice, String payTime, String isReceipt, String receiptTime, String isShip, String shipTime, String status, String createTime, String updateTime) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        Order order = new Order();
        order.setId(id);
        order.setOrderNumber(orderNumber);
        order.setPayPrice(BigDecimal.valueOf(Double.valueOf(payPrice)));
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            order.setCreateTime(simpleDateFormat.parse(createTime));
            order.setPayTime(simpleDateFormat.parse(payTime));
            order.setReceiptTime(simpleDateFormat.parse(receiptTime));
            order.setUpdateTime(simpleDateFormat.parse(updateTime));
            order.setShipTime(simpleDateFormat.parse(shipTime));
            order.setIsPay(isShip);
            order.setIsReceipt(isReceipt);
            order.setStatus(status);
            Integer integer = orderService.updateOrder(order);
            if (integer > 0) {
                resultmap.put("data", integer);
                resultmap.put("code", 200);
                resultmap.put("message", "更新订单成功");
                logger.info("cancelOrder success;");
            } else {
                resultmap.put("code", -1);
                resultmap.put("message", "更新订单失败");
            }
            logger.info("createOrder success;");
        } catch (Exception ex) {
            logger.error("createOrder Exception {}", ex);
        }
        return resultmap;
    }

    @ApiOperation(value = "取消订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public WebJsonResult cancelOrder(@ApiParam(value = "订单Id", required = true) @RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        try {
            Order order = new Order();
            order.setId(orderId);
            order.setStatus("5");
            Integer integer = orderService.updateOrder(order);
            if (integer > 0) {
                result.setMessage("取消订单成功");
                logger.info("cancelOrder success;");
            } else {
                result.setMessage("取消订单失败");
            }
        } catch (Exception ex) {
            logger.error("cancelOrder Exception {}", ex);
        }
        return result;
    }

    @ApiOperation(value = "确认收货")
    @RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
    public WebJsonResult confirmReceipt(@ApiParam(value = "订单Id", required = true) @RequestParam(value = "orderId", required = true) Integer orderId) {
        WebJsonResult result = success();
        try {
            Order order = new Order();
            order.setId(orderId);
            order.setStatus("1");
            order.setReceiptTime(new Date());
            Integer integer = orderService.updateOrder(order);
            if (integer > 0) {
                result.setMessage("确认收货成功");
                logger.info("cancelOrder success;");
            } else {
                result.setMessage("确认收货失败");
            }
        } catch (Exception ex) {
            logger.error("cancelOrder Exception {}", ex);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteOrderBatch", method = RequestMethod.POST)
    public Map<String, Object> cancelOrder(String orderIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Integer integer = orderService.deleteBatch(orderIds);
            if (integer > 0) {
                map.put("data", integer);
                map.put("code", 200);
                map.put("message", "批量删除订单成功");
                logger.info("cancelOrder success;");
            } else {
                map.put("code", -1);
                map.put("message", "批量删除订单失败");
            }
        } catch (Exception ex) {
            logger.error("cancelOrder Exception {}", ex);
        }
        return map;
    }
    private Map<String,Object> getTime(String dateTime){
        Map<String,Object> map=new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.equals(dateTime,Constants.ALLTIME)){
            map.put("beginTime",null);
            map.put("endTime",null);
        }else if(StringUtils.equals(dateTime,Constants.TODAY)){
            map.put("beginTime",sdf.format(getStartTime()));
            map.put("endTime",sdf.format(getEndTime()));
        }else if(StringUtils.equals(dateTime,Constants.WEEK)){
            map.put("beginTime",getPastDate(6));
            map.put("endTime",sdf.format(getEndTime()));
        }else{
            map.put("beginTime",getPastDate(29));
            map.put("endTime",sdf.format(getEndTime()));
        }
        return map;
    }
    private static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    private static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today)+" "+"00:00:00";
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getPastDate(29));

    }

}
