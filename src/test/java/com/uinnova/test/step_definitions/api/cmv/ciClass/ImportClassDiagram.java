package com.uinnova.test.step_definitions.api.cmv.ciClass;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.dmv.migrateDiagram.ImportDiagrams;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ImportClassDiagram extends RestApi{

	public String importClassDiagram(String fileName){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/importClassDiagram";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = ImportClassDiagram.class.getResource("/").getPath()+"/testData/ciClass/"+fileName;
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		return result;
	}
}
