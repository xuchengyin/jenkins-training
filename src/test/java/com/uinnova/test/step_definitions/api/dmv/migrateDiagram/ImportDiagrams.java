package com.uinnova.test.step_definitions.api.dmv.migrateDiagram;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * 导出所有视图
 *
 */
public class ImportDiagrams {
	public JSONObject importDiagrams(String fileName){
		String url =":1511/tarsier-vmdb/dmv/migrateDiagram/importDiagrams";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = ImportDiagrams.class.getResource("/").getPath()+"/testData/dmv/migrateDiagram/"+fileName;
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}

}
