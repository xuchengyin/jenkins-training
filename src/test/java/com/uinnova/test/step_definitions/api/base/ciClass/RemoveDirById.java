package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassDirUtil;

public class RemoveDirById extends RestApi{

	/**
	 * @param dirId
	 * @return
	 * 根据ID删除
	 */
	public JSONObject removeDirById(String dirId) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/removeDirById";
		return doRest(url, dirId, "POST");
	}

	/**
	 * @param dirName
	 * @return
	 * 根据名称删除
	 */
	public JSONObject removeDirByName(String dirName) {
		CiClassDirUtil cu = new CiClassDirUtil();
		String dirId = cu.getDirId(dirName);
		return removeDirById(dirId);
	}

}
