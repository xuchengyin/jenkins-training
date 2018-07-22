package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 2018-3-5
 * @author wsl
 * 迭代2.3.0新增接口
 */
public class ImportExcel2 {
	/**
	 * @param fileName
	 * @return
	 */
	public JSONObject importExcel2(String fileName) {
		String url = ":1511/tarsier-vmdb/cmv/ciExcelBatchImport/importExcel2";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = ImportExcel2.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/"+fileName;
		uploadFileMap.put(filePath, "files");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		return (new AssertResult()).assertRes(result);
	}
}
