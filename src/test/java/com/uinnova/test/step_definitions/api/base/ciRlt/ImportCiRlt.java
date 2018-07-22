package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ImportCiRlt {

	/**
	 * @param rltClsName
	 * @param filePath
	 * @return
	 */
	public String importCiRlt(String rltClsName,String filePath){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/importCiRlt";
		HashMap uploadFileMap =  new HashMap();
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		return result;
	}
}
