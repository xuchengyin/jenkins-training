package com.uinnova.test.step_definitions.api.dmv.visio;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * 导入visio
 *
 */
public class Parser2Json {
	public JSONObject parser2Json(String fileName){
		String url =":1511/tarsier-vmdb/dmv/visio/parser2Json";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = Parser2Json.class.getResource("/").getPath()+"/testData/dmv/image/import/visio/"+fileName;
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}
