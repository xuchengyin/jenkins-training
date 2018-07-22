package com.uinnova.test.step_definitions.api.dmv.file;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍:通过id删除文件夹，可以传多个
 */
public class RemoveDirByIds extends RestApi{
	public JSONObject removeDirByIds(List dirNameList){
		String url = ":1511/tarsier-vmdb/dmv/file/removeDirByIds";
		FormUtil formUtil = new FormUtil();
		JSONArray ids = new JSONArray();
		JSONObject param = new JSONObject();
		for(int i =0;i<dirNameList.size();i++){
			String dirName = (String)dirNameList.get(i);
			BigDecimal dirId = formUtil.getDirIdByName(dirName);
			ids.put(dirId);
		}
		param.put("ids", ids);
		return doRest(url,param.toString(),"POST");
	}
}
