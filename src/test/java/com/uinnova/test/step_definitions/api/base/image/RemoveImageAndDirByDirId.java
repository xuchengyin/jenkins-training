package com.uinnova.test.step_definitions.api.base.image;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;

/**
 * 编写时间:2017-10-27
 * 编写人: sunsl
 * 功能介绍:图标管理根据图标ID删除图标文件夹
 */
public class RemoveImageAndDirByDirId extends RestApi{

	/**
	 * @param dirName
	 * @return
	 */
	public JSONObject removeImageAndDirByDirId(String dirName){
		ImageUtil imageUtil = new ImageUtil();
		String url=":1511/tarsier-vmdb/cmv/image/removeImageAndDirByDirId";
		ArrayList list = imageUtil.getDBImageDir(dirName);
		if(list != null && list.size()>0){
			HashMap imageDirMap = (HashMap) list.get(0);
			String dirId = imageDirMap.get("ID").toString();
			return doRest(url, dirId, "POST");
		}
		return null;
	}

}
