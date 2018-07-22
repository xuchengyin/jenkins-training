package com.uinnova.test.step_definitions.api.emv.severity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/*
 * 编写时间:2018-05-22
 * 编写人:yll
 * 功能介绍:团队里面上传图片、告警级别上传告警声音，下面整个接口的目的是把图片或声音上传上去
 */
public class UploadVoice extends RestApi {
	public JSONObject uploadVoice(String filePath){
		String url =":1516/monitor-web/severity/uploadVoice";
		 Map<String,String> uploadFileMap = new HashMap<String,String>();
		 //Content-Disposition: form-data; name="file"; filename="微信图片_20171018164546.jpg"
		   uploadFileMap.put(filePath, "file");//该接口传的参数不是一般的样式，但是接口里面的传 的参数 为 file
		   String result = QaUtil.uploadfiles(url, uploadFileMap, null);
		   AssertResult as = new AssertResult();
		   return as.assertRes(result);

	}

}
