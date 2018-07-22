package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 异步导入一个分类数据
 * @author wsl
 */
public class AsyncImportCiBySheetName extends RestApi{

	public JSONObject asyncImportCiBySheetName(String  fileName, String sheetName, JSONObject ciClassInfo){
		String url =":1511/tarsier-vmdb/cmv/ciExcelBatchImport/asyncImportCiBySheetName";
		JSONObject param = new JSONObject();
		param.put("fileName", fileName);
		param.put("sheetName", sheetName);
		param.put("ciClassInfo", ciClassInfo);
		return this.doRest(url, param.toString(), "POST");
	}

}
