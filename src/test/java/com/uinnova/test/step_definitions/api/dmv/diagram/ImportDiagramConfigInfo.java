package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-1-11
 * 编写人:wsl
 * 功能介绍:DMV-绘图-导入绘图-导入配置信息
 */
public class ImportDiagramConfigInfo {
	public JSONObject importDiagramConfigInfo(String fileName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/importDiagramConfigInfo";
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = ImportDiagramConfigInfo.class.getResource("/").getPath()+"/testData/dmv/image/import/config/"+fileName;
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);


	}
}
