package com.ma.rest.utils;

import com.ma.rest.dao.JedisClient;
import redis.clients.jedis.JedisPool;
import javax.annotation.Resource;
import java.util.Date;

/**
 * Descriptions
 *直接获取订单号
 * @Author施龙飞
 * @date: Created in09:40 2018/7/6 0006
 **/
public class OrderNumberUtil {
    private static final String reids_order_number_key="ma_rest_max_order_number";
    @Resource(name = "jedisPool")
    public  JedisPool jedisPool;
    public static String getOrderNumber(int i, JedisClient jedis) {
        i++;
        String ss=String.valueOf(i);
        jedis.set(reids_order_number_key,ss);
        String s = "0000000"+i;
        String newS=null;
         newS=s.substring(s.length()-8);
        Date date = new Date();
        String s1 = DateUtil.toString(date).replace("-","").replace(":","");
        String sss = "0123456789";
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < 4; j++) {
            int x = (int) (Math.random() * 10);
            char c = sss.charAt(x);
            sb.append(c);
        }
        return s1+newS+sb.toString();
    }
}
