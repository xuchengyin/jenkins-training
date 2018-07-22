package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 删除对象分类
 * @author uinnova
 *
 */
public class RemoveById extends RestApi{

	public JSONObject removeById(String classId) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/removeById";
		return doRest(url, classId, "POST");
	}
	
	/**
	 * 依据ciClass名称删除分类
	 * @param ciClassName
	 * @return
	 */
	public JSONObject removeByName(String ciClassName) {
		return removeById(String.valueOf((new CiClassUtil()).getCiClassId(ciClassName)));
	}

}
