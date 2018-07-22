package com.uinnova.test.step_definitions.api.cmv.kpi;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 *编写时间:2018-03-16
 *编写人:sunsl 
 *功能介绍:指标模型导入模板功能
 */
public class ImportKpi {
    public JSONObject importKpi(String fileName){
    	String url = ":1511/tarsier-vmdb/cmv/kpi/importKpi";
    	HashMap<String,String> uploadFileMap = new HashMap<String,String>();
    	String filePath = ImportKpi.class.getResource("/").getPath() + "testData/kpi/"+fileName;
    	uploadFileMap.put(filePath, "file");
    	String result = QaUtil.uploadfiles(url, uploadFileMap, null);
    	AssertResult as = new AssertResult();
    	return as.assertRes(result);
    }
}
