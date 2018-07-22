package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryInfoList;

/**
 * 编写时间:2018-1-10 编写人:wsl 功能介绍:CIRlt工具类
 */
public class CiRltUtil {
   
	/**
	 * @param className
	 * @return
	 */
	public BigDecimal getDefIdByDefName(String defName){
		BigDecimal defId =new BigDecimal(0);
	    JSONArray ciIds = new JSONArray();
	    QueryInfoList qil = new QueryInfoList();
		JSONObject result = qil.queryInfoList();
		JSONArray data = result.getJSONArray("data");
		if (data!=null){
			for (int i=0; i<data.length(); i++){
				JSONObject tempObj = data.getJSONObject(i);
				JSONObject defObj =  tempObj.getJSONObject("def");
				if (defName.compareToIgnoreCase(defObj.getString("defName"))==0){
					defId = defObj.getBigDecimal("id");
					break;
				}
	        }
		}
		return defId;
	}
}
