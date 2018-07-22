package com.uinnova.test.step_definitions.api.dcv.dcModelItem;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 *编写时间: 2018-1-26
 *编写人:wjx
 *功能介绍:上传模型库
 */
public class UploadZip {
	public JSONObject uploadProduct(String fileName) {
		String url = ":1511/tarsier-vmdb/dcv/dcModelItem/uploadZip";
		Map<String, String> uploadFileMap = new HashMap<String, String>();
		String FilePath = UploadZip.class.getResource("/").getPath() + "testData/dcv/productModel/" + fileName;
		uploadFileMap.put(FilePath, "file");

		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}

}