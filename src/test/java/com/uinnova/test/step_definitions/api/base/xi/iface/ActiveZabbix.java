package com.uinnova.test.step_definitions.api.base.xi.iface;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.IfaceUtil;

/**
 * 编写时间:2017-11-08
 * 编写人:sunsl
 * 功能介绍:数据集成功能添加数据源类
 */
public class ActiveZabbix extends RestApi{
	/**
	 * @param tplName
	 * @param ifaceName
	 * @return
	 */
	public JSONObject activeZabbix(String tplName,String ifaceName){
		String url=":1511/tarsier-vmdb/cmv/xi/iface/activeZabbix";
		JSONObject param = new JSONObject();
		IfaceUtil iu = new IfaceUtil();
		String tplId = iu.getTplId(tplName);
		param.put("tplId", tplId);
		param.put("ifaceName", ifaceName);
		return doRest(url, param.toString(), "POST");
	}
}
