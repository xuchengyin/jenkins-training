package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryPage extends RestApi{

	/**
	 * @param rltClsName
	 * @return
	 */
	public JSONObject queryPage(String rltClsName){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		ArrayList<String> ciRltQ = new ArrayList<String>();
		ciRltQ.add("CI_RLt_ATTR");
		ciRltQ.add("CI_RLt_CI_Attr");
		ciRltQ.add("CI_RLt_CI");
		ciRltQ.add("CI_RLt_CI_CLASS");
		ciRltQ.add("CI_RLt_CI_CLASS_ATTR_DEF");
		cdt.put("ciRltQ", ciRltQ);
		BigDecimal classId = (new RltClassUtil()).getRltClassId(rltClsName);
		cdt.put("classId", classId);
		int[] arr = new int[]{1,2};
		cdt.put("ciRltLvls", arr);
		param.put("cdt",cdt);
		param.put("orders","");
		param.put("pageNum", 1);
		param.put("pageSize",30);
		JSONObject result =   doRest(url, param.toString(), "POST");
		JSONArray data  =  result.getJSONObject("data").getJSONArray("data");
		int totalPages = result.getJSONObject("data").getInt("totalPages");			
		int totalRows = result.getJSONObject("data").getInt("totalRows");			
		if(totalPages>1){
			for(int i=1;i<totalPages;i++){
				param.put("pageNum", i+1);
				param.put("totalPages", totalPages);
				param.put("totalRows", totalRows);
				JSONObject resultTmp = doRest(url, param.toString(), "POST");
				JSONArray datas = resultTmp.getJSONObject("data").getJSONArray("data");
				for(int j=0;j<datas.length();j++){
					data.put(datas.get(j));
				}
			}
		}
		return result;
	}
}
