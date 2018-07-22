package com.uinnova.test.step_definitions.api.dmv.file;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编辑时间:2018-04-20
 * 编辑人:sunsl
 * 功能介绍:查询目录文件列表
 */
public class QueryFileInfoPage extends RestApi{
   public JSONObject queryFileInfoPage(String word,String dirName){
	   String url = ":1511/tarsier-vmdb/dmv/file/queryFileInfoPage";
	   FormUtil  formUtil = new FormUtil();
	   BigDecimal dirId = formUtil.getDirIdByName(dirName);
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   param.put("pageNum", 1);
	   param.put("pageSize", 100);
	   cdt.put("word", word);
	   cdt.put("dirId",dirId );
	   param.put("cdt", cdt);
	   return doRest(url,param.toString(),"POST");
   }
}
