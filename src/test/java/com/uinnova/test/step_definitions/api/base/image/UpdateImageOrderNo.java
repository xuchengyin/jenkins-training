package com.uinnova.test.step_definitions.api.base.image;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;

/**
 * 编写时间:2017-11-03 编写人:sunsl 功能介绍:图标管理移动图片
 */
public class UpdateImageOrderNo extends RestApi{
	/**
	 * @param moveImage
	 * @param dirName
	 * @return
	 */
	public JSONObject updateImageOrderNo(String moveImage, String dirName) {
		ImageUtil imageUtil = new ImageUtil();
		String url = ":1511/tarsier-vmdb/cmv/image/updateImageOrderNo";
		List dirlist = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) dirlist.get(0);
		String dirId = imageHashMap.get("ID").toString();
		List list = imageUtil.getDBImage(moveImage);
		HashMap moveImageMap = (HashMap) list.get(0);
		String moveImageId = moveImageMap.get("ID").toString();
		String orderNo = moveImageMap.get("ORDER_NO").toString();
		JSONObject param = new JSONObject();
		JSONObject imageCdt = new JSONObject();
		imageCdt.put("id", moveImageId);
		imageCdt.put("dirId", dirId);
		imageCdt.put("orderNo", orderNo);
		param.put("newNo", 2);
		param.put("imageCdt", imageCdt);
		return doRest(url, param.toString(), "POST");
	}
}
