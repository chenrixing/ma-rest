package com.ma.rest.controller;

import com.ma.common.utils.Constants;
import com.ma.common.utils.WebJsonResult;

public abstract class BaseController {

	protected WebJsonResult success() {
		WebJsonResult jsonResult = new WebJsonResult();
		jsonResult.setReturnCode(Constants.RESULT_CODE_200);
		jsonResult.setMessage("成功！");
		return jsonResult;
	}
	protected WebJsonResult error(String msg) {
		WebJsonResult jsonResult = new WebJsonResult();
		jsonResult.setReturnCode(Constants.RESULT_CODE_0);
		jsonResult.setMessage(msg);
		return jsonResult;
	}

    /**
     * 测试
     * @param code
     * @param msg
     * @return
     */
	protected WebJsonResult error(String code,String msg) {
		WebJsonResult jsonResult = new WebJsonResult();
		jsonResult.setReturnCode(code);
		jsonResult.setMessage(msg);
		return jsonResult;
	}
}
