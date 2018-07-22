package com.uinnova.test.step_definitions.api.base.image;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


/**
 * 图标库导入
 * @author wsl
 * 3.2.0迭代新增接口
 */
public class ImportImage {

	/**
	 * 上传zip格式的目录和目录下的图标信息(开启进程导入)
	 * @param filePath
	 * @return
	 */
	public JSONObject importImage(String filePath){
		String url = ":1511/tarsier-vmdb/cmv/image/importImage";
		Map<String,String> uploadFileMap = new HashMap<String,String>();
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	}
}
