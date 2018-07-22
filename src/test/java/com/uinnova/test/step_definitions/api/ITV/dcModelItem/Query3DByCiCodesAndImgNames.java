package com.uinnova.test.step_definitions.api.ITV.dcModelItem;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 *编写时间: 2018-03-12
 *编写人:wjx
 *功能介绍:获取组合视图板子信息
 */
public class Query3DByCiCodesAndImgNames {
	public JSONObject getIconMapping(String[] ciCode,String[] imageName){
		String url = ":1511/tarsier-vmdb/dcv/dcModelItem/query3DByCiCodesAndImgNames";
		
		JSONObject param = new JSONObject();
		JSONArray code = new JSONArray();
		JSONArray image = new JSONArray();
		
		for(int i = 0; i < ciCode.length; i++){
			code.put(ciCode[i]);
		}
		
		for(int i = 0; i < imageName.length; i++){
			image.put(imageName[i]);
		}
		
		param.put("ciCodes", code);
		param.put("imgNames", imageName);
		
		System.out.println("************************"  + param );
		
		String result = QaUtil.loadRest(url, param.toString(), "POST");
		AssertResult as = new AssertResult();
		return as.assertRes(result);
//		return null;
//	}
//	public static void main (String args[]) {
//		String[] ciCode = {"asdsd","fdfdf"};
//		String[] imageName = {"bbbbb","ssssssw"};
//		
//		
//		Query3DByCiCodesAndImgNames test = new Query3DByCiCodesAndImgNames();
//		test.getIconMapping(ciCode, imageName);
		
	}
}
