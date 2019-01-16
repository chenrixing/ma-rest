package com.ma.common.utils;

import java.io.Serializable;
import java.sql.Array;
import java.util.HashMap;

public class WebJsonResult implements Serializable {

    /**
     * 封装接口返回信息
     */
    private static final long serialVersionUID = 2466554152925855918L;
    private String returnCode = Constants.RESULT_CODE_200;
    private String message;
    private Object data;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Object getData() {
        if(data==null){
            return new HashMap<>();
        }
        return data;
    }

    public void setData(Object data) {

        this.data = data;
    }

}
