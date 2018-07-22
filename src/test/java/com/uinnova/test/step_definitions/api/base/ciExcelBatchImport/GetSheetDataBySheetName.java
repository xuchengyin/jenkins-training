package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetSheetDataBySheetName extends RestApi{
	
	/**
	 * @param excelName
	 * @param sheetName
	 * @return
	 */
	public JSONObject getSheetDataBySheetName(String excelName,String sheetName) {
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/getSheetDataBySheetName";
		JSONObject param = new JSONObject();
		param.put("fileName", excelName);
		param.put("pageNum", 1);
		param.put("pageSize", 20);
		param.put("sheetName", sheetName);
		return doRest(url, param.toString(), "POST");
	}

}
