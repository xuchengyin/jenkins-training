package com.uinnova.test.step_definitions.api.dmv.file;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍:DMV表格根据ID取得File信息
 */
public class QueryFileInfoById extends RestApi{
   public JSONObject queryFileInfoById(String fileName){
	   String url = ":1511/tarsier-vmdb/dmv/file/queryFileInfoById";
	   FormUtil formUtil = new FormUtil();
	   BigDecimal id = formUtil.getFormIdByFormName(fileName);
   	   return doRest(url, id.toString(), "POST");
   }
}
