package com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ImportExcel {
	
	/**
	 * @param fileName
	 * @return
	 */
	public JSONObject importExcel(String fileName) {
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/importExcel";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = ImportExcel.class.getResource("/").getPath()+"/testData/cirltBatchExcelImport/"+fileName;
		uploadFileMap.put(filePath, "files");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		return (new AssertResult()).assertRes(result);
	}

}
