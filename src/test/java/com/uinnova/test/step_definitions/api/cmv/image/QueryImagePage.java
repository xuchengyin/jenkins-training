package com.uinnova.test.step_definitions.api.cmv.image;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-11
 * 编写人:sunsl
 * 功能介绍:CMV查询图标类
 */
public class QueryImagePage extends RestApi{
   public JSONObject queryImagePage(BigDecimal dirId){
	   String url = ":1511/tarsier-vmdb/cmv/image/queryImagePage";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   cdt.put("dirId", dirId);
	   param.put("orders", "DIR_ID,ORDER_NO,IMG_NAME");
	   param.put("pageNum", 1);
	   param.put("pageSize", 60);
	   return doRest(url, param.toString(), "POST");
   }
}
