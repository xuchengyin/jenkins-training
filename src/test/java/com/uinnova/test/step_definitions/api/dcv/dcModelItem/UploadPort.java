package com.uinnova.test.step_definitions.api.dcv.dcModelItem;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 *编写时间: 2018-2-1
 *编写人:wjx
 *功能介绍:上传端口
 */
public class UploadPort {
	public JSONObject uploadProductPort(String fileName) {
		String url = ":1511/tarsier-vmdb/dcv/dcModelItem/uploadPort";
		Map<String, String> uploadFileMap = new HashMap<String, String>();
		String filePath = UploadZip.class.getResource("/").getPath() + "testData/dcv/productModel/" + fileName;
		uploadFileMap.put(filePath, "file");

		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}

}