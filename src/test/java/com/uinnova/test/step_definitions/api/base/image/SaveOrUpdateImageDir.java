package com.uinnova.test.step_definitions.api.base.image;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;

/**
 * 编写时间:2017-10-27
 * 编写人:sunsl
 * 功能介绍:图标管理目录创建类
 */
public class SaveOrUpdateImageDir extends RestApi{

	/**
	 * 创建图标目录
	 * @param dirName
	 * @return
	 */
	public JSONObject createImageDir(String dirName){
		String url = ":1511/tarsier-vmdb/cmv/image/saveOrUpdateImageDir";
		JSONObject param = new JSONObject();
		param.put("parentId", 0);
		param.put("dirType", 1);
		param.put("dirName", dirName);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 再次用相同的名字创建图标目录， 失败场景使用
	 * @param dirName
	 * @return
	 */
	public JSONObject createImageDirAgain(String dirName, String kw){
		String url = ":1511/tarsier-vmdb/cmv/image/saveOrUpdateImageDir";
		JSONObject param = new JSONObject();
		param.put("parentId", 0);
		param.put("dirType", 1);
		param.put("dirName", dirName);
		return doFailRest(url, param.toString(), "POST", kw);
	}

	/**
	 * 更新图标目录
	 * @param dirName
	 * @param sourceName
	 * @return
	 */
	public JSONObject updateImageDir(String dirName,String sourceName){
		ImageUtil imageUtil = new ImageUtil();
		String url = ":1511/tarsier-vmdb/cmv/image/saveOrUpdateImageDir";
		ArrayList list = (ArrayList)imageUtil.getDBImageDir(sourceName);
		HashMap imageDirMap = (HashMap) list.get(0);
		String dirId = imageDirMap.get("ID").toString();
		JSONObject param = new JSONObject();
		param.put("parentId", 0);
		param.put("dirType", 1);
		param.put("dirName", dirName);
		param.put("id", dirId);
		return doRest(url, param.toString(), "POST");
	}
}
