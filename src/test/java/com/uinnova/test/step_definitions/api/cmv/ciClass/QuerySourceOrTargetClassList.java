package com.uinnova.test.step_definitions.api.cmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class QuerySourceOrTargetClassList extends RestApi{

	
	public JSONObject querySourceOrTargetClassList(String ClassName, String targetOrSource, String orders){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/querySourceOrTargetClassList";
		CiClassUtil cu = new CiClassUtil();
//		{
//			  cdt: {
//				sourceClassId: 700000000013506  //我是源分类,我要查我的目标类信息
//				//或者（sourceClassId 和 targetClassId 两个参数值仅存其一，两个都传值，或者都不传值则均不查询，直接返回空）
//				targetClassId: 700000000013506   ////我是目标分类,我要查我的源分类信息
//				},
//			  orders: 排序字段，主要是对返回的CI分类排序，可选参数
//			}
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		if(targetOrSource.equals("target")){
			cdt.put("targetClassId", cu.getCiClassId(ClassName));
		}else if(targetOrSource.equals("source")){
			cdt.put("sourceClassId", cu.getCiClassId(ClassName));
		}else{
			cdt.put("sAndtClassId", cu.getCiClassId(ClassName));
		}
		param.put("cdt", cdt);
		param.put("orders", orders);
		return doRest(url, param.toString(), "POST");
		
	}
}
