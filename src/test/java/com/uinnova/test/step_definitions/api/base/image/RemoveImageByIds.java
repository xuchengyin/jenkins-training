package com.uinnova.test.step_definitions.api.base.image;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;

/**
 * 编写时间:2017-10-30
 * 编写人:sunsl
 * 功能介绍:图标管理删除图片类
 */
public class RemoveImageByIds extends RestApi{
	/**
	 * @param updateImage
	 * @return
	 */
	public JSONObject removeImageByIds(String updateImage){
		ImageUtil imageUtil = new ImageUtil();
		String url =":1511/tarsier-vmdb/cmv/image/removeImageByIds";
		List list = imageUtil.getDBImage(updateImage);
		HashMap updateImageMap = (HashMap) list.get(0);
		String deleteId = updateImageMap.get("ID").toString();
		JSONArray array = new JSONArray();
		array.put(Long.valueOf(deleteId));
		return doRest(url, array.toString(), "POST");
	}

	/**
	 * @param deleteImageList
	 * @return
	 */
	public JSONObject removeImageByIds(List deleteImageList){
		ImageUtil imageUtil = new ImageUtil();
		String url =":1511/tarsier-vmdb/cmv/image/removeImageByIds";
		JSONArray array = new JSONArray();
		for (int i = 0; i < deleteImageList.size(); i++) {
			String deleteImage = (String)deleteImageList.get(i);
			List list = imageUtil.getDBImage(deleteImage);
			HashMap deleteImageMap = (HashMap) list.get(0);
			String deleteId = deleteImageMap.get("ID").toString();
			array.put(Long.valueOf(deleteId));
		}
		return doRest(url, array.toString(), "POST");
	}
	
	public JSONObject removeImageByIds(String addImage,String dirName){
		ImageUtil imageUtil = new ImageUtil();
		String url =":1511/tarsier-vmdb/cmv/image/removeImageByIds";
		List list = imageUtil.getDBImageIdByDirName(addImage,dirName);
		HashMap updateImageMap = (HashMap) list.get(0);
		String deleteId = updateImageMap.get("ID").toString();
		JSONArray array = new JSONArray();
		array.put(Long.valueOf(deleteId));
		return doRest(url, array.toString(), "POST");
	}
}
