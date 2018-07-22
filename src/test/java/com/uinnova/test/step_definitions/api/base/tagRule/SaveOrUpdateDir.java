package com.uinnova.test.step_definitions.api.base.tagRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class SaveOrUpdateDir extends RestApi{

	/**
	 * @param dirName
	 * @return
	 */
	public JSONObject saveOrUpdateDir(String dirName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/saveOrUpdateDir";
		JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("id", "");
		param.put("parentId", 0);
		param.put("tagType", 1);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * @param sourceDirName
	 * @param targetDirName
	 * @return
	 */
	public JSONObject saveOrUpdateDir(String sourceDirName,String targetDirName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/saveOrUpdateDir";
		JSONObject param = new JSONObject();
		param.put("dirName", targetDirName);
		BigDecimal dirId = (new TagRuleUtil()).getDirId(sourceDirName);
		param.put("id", dirId);
		param.put("parentId", 0);
		param.put("tagType", 1);
		return doRest(url, param.toString(), "POST");
	}
}
