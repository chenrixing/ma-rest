package com.ma.rest.test;

/**
 * Descriptions
 *
 * @Author施龙飞
 * @date: Created in09:33 2018/7/6 0006
 **/
public class AutoAddOne {
    private static int i=7888;//初始值
    /**
     * 在初始值上自增1
     * @return
     */
    public static String Plus(){
        i++;
        String s = "00000"+i;
        return s.substring(s.length()-6);
    }
    /**
     * 测试
     * @param s
     */
    public static void main(String[] s){
        System.out.println(Plus());
        System.out.println(Plus());
    }
}
