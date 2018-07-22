package com.uinnova.test.step_definitions.utils.base;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.xi.iface.QueryTplList;

/**
 * 编写时间:2017-11-09
 * 编写人:sunsl
 * 功能介绍:数据集成工具类
 */
public class IfaceUtil {

	/**
	 * @param tplName
	 * @return
	 */
	public String getTplId(String tplName){
		String tplId = "";
		QueryTplList qt = new QueryTplList();
		JSONObject result = qt.queryTplList();
		//取得Id
		JSONArray data = result.getJSONArray("data");
		for(int i = 0; i < data.length(); i ++){
			JSONObject obj = (JSONObject)data.get(i);
			String tplNameDb = obj.getString("tplName");
			if(tplName.equals(tplNameDb)){
				tplId = String.valueOf(obj.getBigDecimal("id"));
				break;
			}
		}
		return tplId;
	}
}
