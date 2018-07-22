package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 2018-3-6
 * @author wsl
 * 迭代2.3.0新增接口
 */
public class ImportCiAgain extends RestApi{
	
	/**
	 * 再次导入
	 * @param ciClassName
	 * @return
	 */
	public JSONObject importCiAgain(String ciClassName) {
		String url = ":1511/tarsier-vmdb/cmv/ciExcelBatchImport/importCiAgain";
		JSONObject param = new JSONObject();
		param.put("datas", new JSONArray());
		param.put("classId", (new CiClassUtil()).getCiClassId(ciClassName));
		return this.doRest(url, param.toString(), "POST");
	}
}
