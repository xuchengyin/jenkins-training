package com.uinnova.test.step_definitions.api.dcv.dataCenter;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *编写时间: 2018-2-05
 *编写人:zsz
 *功能介绍:数据上传
 */
import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/*
 * 上传3Dmax场景接口
 * */
public class UploadMax {
	/**
	 * @param dataCenterid
	 * @param fileName
	 * @return
	 */
	public JSONObject uploadMax(String dataCenterid,String fileName){
		BigDecimal data = (new CiUtil()).getCiId(dataCenterid);
		String url=":1511/tarsier-vmdb/dcv/dcFile/uploadMax/" + data;
		HashMap<String,String> uploadFileMap = new HashMap<String,String>();
		String filePath = UploadMax.class.getResource("/").getPath()+"/testData/dcv/upload/"+fileName;
		uploadFileMap.put(filePath, "file");
		String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		AssertResult as = new AssertResult();
		return as.assertRes(result);
		//return null;

	}
}
