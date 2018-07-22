package com.uinnova.test.step_definitions.api.cmv.dynamicClass;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class SaveOrUpdateDefaultTpl extends RestApi{

//	|dataStatus|1|
//	|tplName|测试树状图|
//	#|status|1|
//	|levelName5|日期|
//	|levelName4|枚举|
//	|levelName3|小数|
//	|levelName2|整数|
//	|levelName1|CI Code|
	
	/**@author ldw
	 * 2018/5/29修改参数
	 * */
	public JSONObject saveOrUpdateDefaultTpl(String dataStatus, String tplName, List<String> levelNames, List<String> ciClassNameList){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/saveOrUpdateDefaultTpl";
		JSONObject tpl = new JSONObject();
		JSONArray ciClassList = new JSONArray();
		JSONObject param = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		if(levelNames.size() != 5)return null;
		for (int i = 0; i < levelNames.size(); i++) {
			tpl.put("levelName"+(i+1), levelNames.get(i));
		}
		tpl.put("dataStatus", Integer.parseInt(dataStatus));
		tpl.put("tplName", tplName);
		for (int i = 0; i < ciClassNameList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("className", ciClassNameList.get(i));
			temp.put("id", ccu.getCiClassId(ciClassNameList.get(i)));
			ciClassList.put(temp);
		}
		param.put("tpl", tpl);
		param.put("ciClassList", ciClassList);
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject saveOrUpdateDefaultTplFailed(String dataStatus, String tplName, List<String> levelNames, List<String> ciClassNameList, String kw){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/saveOrUpdateDefaultTpl";
		JSONObject tpl = new JSONObject();
		JSONArray ciClassList = new JSONArray();
		JSONObject param = new JSONObject();
		CiClassUtil ccu = new CiClassUtil();
		if(levelNames.size() != 5)return null;
		for (int i = 0; i < levelNames.size(); i++) {
			tpl.put("levelName"+(i+1), levelNames.get(i));
		}
		tpl.put("dataStatus", Integer.parseInt(dataStatus));
		tpl.put("tplName", tplName);
		for (int i = 0; i < ciClassNameList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("className", ciClassNameList.get(i));
			temp.put("id", ccu.getCiClassId(ciClassNameList.get(i)));
			ciClassList.put(temp);
		}
		param.put("tpl", tpl);
		param.put("ciClassList", ciClassList);
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
