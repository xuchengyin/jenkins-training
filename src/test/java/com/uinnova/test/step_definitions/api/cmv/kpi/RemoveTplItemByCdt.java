package com.uinnova.test.step_definitions.api.cmv.kpi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class RemoveTplItemByCdt extends RestApi{

	public JSONObject removeTplItemByCdt(List<String> tplNames, List<String> rltObjNames, List<String> rltObjTypeNames){
//		{
//			tplIds: [700000000002501], //模板ID数组
//			rltObjIds: [35261], //关联对象数组
//			rltObjTypes: [2]  //关联对象类型数组:1=指标 2=CI分类 3=标签
//		}
//		{
//			  "data" : 1,//成功删除个数
//			  "code" : -1,
//			  "success" : true
//			}
		//本接口逻辑，删除关联对象根据对象类型删除，全选或全不选，则全部删除，选择那个删除那个，其中rltObjIds可以是指标、CI分类、标签
		//由于rltObjIds中可以是指标、CI分类、标签三种，所以在feature文件中定义需要做区分，暂定格式为:指标-abc、CI分类-abc、标签-abc，其中abc为对应的名字
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/removeTplItemByCdt";
		KpiUtil ku = new KpiUtil();
		CiClassUtil ccu = new CiClassUtil();
		TagRuleUtil tru = new TagRuleUtil();
		ArrayList tplIds = new ArrayList();
		ArrayList rltObjIds = new ArrayList();
		ArrayList rltObjTypes = new ArrayList();
		JSONObject param = new JSONObject();
		for(int i = 0; i < tplNames.size(); i++){
			tplIds.add(ku.getKpiTplIdByKpiTplName(tplNames.get(i)));
		}
		for(int i = 0; i < rltObjNames.size(); i++){
			if(rltObjNames.get(i).startsWith("指标")){
				rltObjIds.add(ku.getKpiIdByKpiCode(rltObjNames.get(i).substring(3, rltObjNames.get(i).length())));//这里"可能会有问题，忘了汉字长度和英文是否一样了"
			}else if(rltObjNames.get(i).startsWith("CI分类")){
				rltObjIds.add(ccu.getCiClassId(rltObjNames.get(i).substring(5, rltObjNames.get(i).length())));
			}else{
				rltObjIds.add(tru.getTagId(rltObjNames.get(i).substring(3, rltObjNames.get(i).length())));
			}
		}
		for(int i = 0; i < rltObjTypeNames.size(); i++){
			if(rltObjTypeNames.get(i).equals("指标")){
				rltObjTypes.add(1);
			}else if(rltObjTypeNames.get(i).equals("CI分类")){
				rltObjTypes.add(2);
			}else{
				rltObjTypes.add(3);
			}
		}
		param.put("tplIds", tplIds);
		param.put("rltObjIds", rltObjIds);
		param.put("rltObjTypes", rltObjTypes);
		
		return doRest(url, param.toString(), "POST");
	}
}
