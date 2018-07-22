package com.uinnova.test.step_definitions.api.dmv.image;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-01-05
 * 编写人:sunsl
 * 功能介绍:画布顶部插入图片类
 */
public class AddImage {
    public JSONObject addImage(String filePath){
    	String url = ":1511/tarsier-vmdb/dmv/image/addImage";
    	Map<String,String>  uploadFileMap = new HashMap<String,String>();
    	uploadFileMap.put(filePath, "files");
    	Map<String, String> textMap = new HashMap<String,String>();
    	textMap.put("dirId", "0");
    	String result = QaUtil.uploadfiles(url, uploadFileMap, textMap);
    	AssertResult as = new AssertResult();
    	return as.assertRes(result);
    }
    
}
