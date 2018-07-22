package com.uinnova.test.step_definitions.api.base.ciClass;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


public class ImportClassAttr {

	/**
	 * @param ciClassName
	 * @param filePath
	 * @return
	 */
	public JSONObject importAttr(String ciClassName,String filePath) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/importClassAttr?simpleMsg%20=%20false";
		Map<String, String> uploadFileMap = new HashMap<String, String>();
		//		String filePath = ImportClassAttr.class.getResource("/").getPath() + "testData/ci/" + ciClassName + ".xls";
		uploadFileMap.put(filePath, "file");
		Map<String, String> textMap = new HashMap<String, String>();
		textMap.put("classIds", "");
		textMap.put("isAll", "true");
		String result = QaUtil.uploadfiles(url, uploadFileMap, textMap);
		AssertResult asRes = new AssertResult();
		return asRes.assertRes(result);
	}

}
