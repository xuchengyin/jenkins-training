package com.uinnova.test.step_definitions.api.base.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 删除分类下的全部数据
 * @author uinnova
 *
 */
public class RemoveByClassId extends RestApi{

	/**
	 * 根据ci分类ID删除该分类下所有的ci
	 * @param classId
	 * @return
	 */
	public JSONObject removeByClassId(String classId) {
		String url = ":1511/tarsier-vmdb/cmv/ci/removeByClassId";
		return doRest(url, classId, "POST");
	}
	
	/**
	 * 根据ci分类名称删除该分类下所有ci
	 * @param ciClassName
	 * @return
	 */
	public JSONObject removeByClassName(String ciClassName) {
		return removeByClassId(String.valueOf((new CiClassUtil()).getCiClassId(ciClassName)));
	}
}
