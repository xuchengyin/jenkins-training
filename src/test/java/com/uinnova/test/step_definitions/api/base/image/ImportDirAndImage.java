package com.uinnova.test.step_definitions.api.base.image;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-10-27
 * 编写人:sunsl
 * 功能介绍:图标管理导入图标类
 */
public class ImportDirAndImage {
	/**
	 * @param imageDirName
	 * @param filePath
	 * @return
	 */
	public JSONObject importDirAndImage(String imageDirName,String filePath){
		String url = ":1511/tarsier-vmdb/cmv/image/importDirAndImage";
		Map<String,String> uploadFileMap = new HashMap<String,String>();
		uploadFileMap.put(filePath, "files");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}
