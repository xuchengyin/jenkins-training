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
 * 功能介绍:图标管理更新图片类
 */
public class UpdateImageById {
	/**
	 * @param addImage
	 * @param filePath
	 * @return
	 */
	public JSONObject updateImageById(String addImage, String filePath){
		String url =":1511/tarsier-vmdb/cmv/image/updateImageById";
		ImageUtil imageUtil = new ImageUtil();
		Map<String,String> uploadFileMap = new HashMap<String,String>();
		List list = imageUtil.getDBImage(addImage);
		HashMap addImageMap = (HashMap) list.get(0);
		String id = addImageMap.get("ID").toString();
		uploadFileMap.put(filePath,"file");
		Map<String,String> textMap = new HashMap<String,String>();      
		textMap.put("id", id);
		String result = QaUtil.uploadfiles(url, uploadFileMap, textMap);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}
