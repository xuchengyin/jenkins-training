package com.uinnova.test.step_definitions.api.base.image;

import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 * 编写时间:2017-10-29
 * 编写人:sunsl
 * 功能介绍:图标管理导出图标类
 */
public class ExportDirAndImage {
	/**
	 * @param dirName
	 * @return
	 */
	public String exportDirAndImage(String dirName){
		ImageUtil imageUtil = new ImageUtil();
		ArrayList list = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) list.get(0);
		String dirId = imageHashMap.get("ID").toString();
		String url = ":1511/tarsier-vmdb/cmv/image/exportDirAndImage?ids=" + dirId;
		String filePath = ExportDirAndImage.class.getResource("/").getPath() + "testData/download/" + dirName + ".zip";
		if(QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
