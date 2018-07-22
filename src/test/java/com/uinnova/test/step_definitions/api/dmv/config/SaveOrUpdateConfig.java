package com.uinnova.test.step_definitions.api.dmv.config;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-23
 * 编写人:sunsl
 * 功能介绍:DMV设置里的设置功能类
 */
public class SaveOrUpdateConfig extends RestApi{
	public JSONObject saveOrUpdateConfig(String cfgCode,Integer cfgContent){
		String url =":1511/tarsier-vmdb/dmv/config/saveOrUpdateConfig";
		JSONObject param = new JSONObject();
		param.put("cfgCode", cfgCode);
		param.put("cfgContent", cfgContent);
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject saveOrUpdateConfig(String cfgCode,String cfgContent){
		String url = ":1511/tarsier-vmdb/dmv/config/saveOrUpdateConfig";
		JSONObject param = new JSONObject();
		param.put("cfgCode", cfgCode);
		param.put("cfgContent", cfgContent);
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject saveOrUpdateConfig2(String cfgCode,String cfgContent){
		QueryConfigList queryConfigList = new QueryConfigList();
		JSONObject result = queryConfigList.queryConfigList("");
		JSONArray data = result.getJSONArray("data");
		BigDecimal id = new BigDecimal(0);
		for(int i = 0; i < data.length(); i ++){
			JSONObject obj = (JSONObject)data.get(i);
			if (cfgCode.equals(obj.getString("cfgCode"))){
				id = obj.getBigDecimal("id");
				break;
			}
		}
		String url = ":1511/tarsier-vmdb/dmv/config/saveOrUpdateConfig";
		JSONObject param = new JSONObject();
		param.put("cfgCode", cfgCode);
		param.put("cfgContent", cfgContent);
		if(id.compareTo(new BigDecimal(0))!=0){
		  param.put("id", id);
		}
		return doRest(url, param.toString(), "POST");
	}
}
