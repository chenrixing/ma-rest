package com.ma.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 */
public class JsonUtils {


    /**
     * 将对象转换成json字符串。
     * <p>
     * Title: pojoToJson
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        String string = JSON.toJSONString(data,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullNumberAsZero);
        // MAPPER.writeValueAsString(data);
        return string;
    }

    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData
     *            json数据
     * @param clazz
     *            对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = JSON.parseObject(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>
     * Title: jsonToList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            List<T> parseArray = JSON.parseArray(jsonData, beanType);
            return parseArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
