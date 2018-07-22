package com.uinnova.test.step_definitions.api.dcv.cframe;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class UpFile extends RestApi{
	public String upFile(String fileName){
		Map<String, String> uploadFileMap = new HashMap<String, String>();
		String url = ":1511/tarsier-vmdb/dcv/cframe/upFile";
		String filePath = UpFile.class.getResource("/").getPath()+"testData/dcv/"+fileName;
		uploadFileMap.put(filePath, "file");
		return QaUtil.uploadfiles(url, uploadFileMap, null);
	}

}
