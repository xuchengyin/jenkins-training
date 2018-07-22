package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;



public class SaveOrUpdateDir extends RestApi{

	/**
	 * 创建目录
	 * @param dirName
	 * @return
	 */
	public JSONObject createDir(String dirName) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateDir";
		JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("dirDesc", "");
		param.put("ciType", "1");
		param.put("parentId", "0");
		return doRest(url, param.toString(), "POST");
	}
	
	/**
	 * 创建目录失败
	 * @param dirName
	 * @return
	 */
	public JSONObject createDirFail(String dirName, String kw) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateDir";
		JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("dirDesc", "");
		param.put("ciType", "1");
		param.put("parentId", "0");
		return doFailRest(url, param.toString(), "POST", kw);
	}

	/**
	 * 更新目录
	 * @param dirName
	 * @param dirId
	 * @return
	 */
	public JSONObject updateDir(String dirName, String dirId) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdateDir";
		JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("dirDesc", "");
		param.put("ciType", "1");
		param.put("parentId", "0");
		param.put("id", dirId);
		return doRest(url, param.toString(), "POST");
	}

}
