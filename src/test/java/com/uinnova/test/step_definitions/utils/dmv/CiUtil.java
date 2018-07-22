package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ci.QueryPageByIndex;

/**
 * 编写时间:2017-12-20 编写人:sunsl 功能介绍:CI工具类
 */
public class CiUtil {
    /**
     * @param className
     * @param pageNum
     * @param pageSize
     */
	public JSONArray getCiIdByClassName(String className,int pageNum, int pageSize){
	    JSONArray ciIds = new JSONArray();
		QueryPageByIndex queryCIPageByIndex = new QueryPageByIndex();
		JSONObject result = queryCIPageByIndex.queryCIPageByIndex(className,pageNum,pageSize);
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject ciAllObj = data.getJSONObject(i);
				JSONObject ciObj =  ciAllObj.getJSONObject("ci");
				BigDecimal ciId = ciObj.getBigDecimal("id");
				ciIds.put(ciId);
	        }
		}
		return ciIds;
	}
	 /**
     * @param className
     * @param pageNum
     * @param pageSize
     */
	public JSONArray getCiCodeByClassName(String className,int pageNum, int pageSize){
	    JSONArray ciCodes = new JSONArray();
		QueryPageByIndex queryCIPageByIndex = new QueryPageByIndex();
		JSONObject result = queryCIPageByIndex.queryCIPageByIndex(className,pageNum,pageSize);
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject ciAllObj = data.getJSONObject(i);
				JSONObject ciObj =  ciAllObj.getJSONObject("ci");
				String ciCode = ciObj.getString("ciCode");
				ciCodes.put(ciCode);				
	        }
			
		}
		return ciCodes;
	}
}
