package com.uinnova.test.step_definitions.api.base.ci;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


public class ImportCi {

	/**
	 * @param className
	 * @param filePath
	 * @return
	 */
	public JSONObject importCi(String className,String filePath) {
		String url = ":1511/tarsier-vmdb/cmv/ci/importCi?simpleMsg%20=%20false";
		Map<String, String> uploadFilePaths = new HashMap<String, String>();
		uploadFilePaths.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFilePaths, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}

