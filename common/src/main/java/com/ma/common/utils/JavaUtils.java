package com.ma.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class JavaUtils {
    private static Logger logger = Logger.getLogger(JavaUtils.class);
    private static Random random=new Random();
    /**
     * 获取uuid
     * 
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 返回一个1-100的随机整数
     * 
     * @auth:李光明
     * @date:2018年11月9日
     * @return int
     */
    public static int getRandom(int count) {
        if (count <= 1) {
            return new Random().nextInt(1000) + 1;
        }
        int value = new Random().nextInt(1000) + 1;
        logger.info("第一次;随机码：" + value);
        for (int i = 0; i < count - 1; i++) {
            int newValue = new Random().nextInt(1000) + 1;
            logger.info("第" + (i + 2) + "次;随机码：" + newValue);
            if (value > newValue) {
                value = newValue;
            }
        }
        return value;
    }

    /**
     * 返回一个1-100的随机整数
     * 
     * @auth:李光明
     * @date:2018年11月9日
     * @return int
     */
    public static int getMinMaxRandom(int min, int max) {
        return random.nextInt(max) % (max - min + 1) + min;
    }


 
    /**
     * 验证list是否为空
     *@auth:李光明
     *@date:2018年05月28日
     *@param list
     *@return boolean
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }
    /**
     * 验证list是否不为空
     *@auth:李光明
     *@date:2018年05月28日
     *@param list
     *@return boolean
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    
    /**
     * 字符串判断是否为空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {    
       return StringUtils.isNotBlank(str) && !str.equals("null"); 
    }
    
    /**
     * 字符串判断是否为空
     * @param str
     * @return
     */
    public static Object getObj(Object str) { 
       return str==null?"":StringUtils.isNotBlank(str.toString())?str:""; 
    } 
}
