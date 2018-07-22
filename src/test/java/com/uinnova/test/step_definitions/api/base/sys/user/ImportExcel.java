package com.uinnova.test.step_definitions.api.base.sys.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ImportExcel {

	/**
	 * @param filePath
	 * @return
	 */
	public JSONObject importExcel(String filePath){
		String url = ":1511/tarsier-vmdb/cmv/sys/user/importExcel";
		Map<String,String> uploadFileMap = new HashMap<String,String>();
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}
