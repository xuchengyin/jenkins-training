package com.uinnova.test.step_definitions.api.base.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-10-30
 * 编写人:sunsl
 * 功能介绍: 图标管理的添加图片功能类
 */
public class AddImage {
	/**
	 * @param dirName
	 * @param filePath
	 * @return
	 */
	public JSONObject addImage(String dirName,String filePath){
		ImageUtil imageUtil = new ImageUtil();
		String url = ":1511/tarsier-vmdb/cmv/image/addImage";
		List list = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) list.get(0);
		String dirId = imageHashMap.get("ID").toString();
		Map<String,String> uploadFileMap = new HashMap<String,String>();
		uploadFileMap.put(filePath, "files");
		Map<String,String> textMap = new HashMap<String,String>();	   
		textMap.put("dirId", dirId);
		String result = QaUtil.uploadfiles(url, uploadFileMap, textMap);
		AssertResult rs = new AssertResult();
		return rs.assertRes(result);
	}
}
