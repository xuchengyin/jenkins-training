package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;

import cucumber.api.DataTable;

public class SaveOrUpdate extends RestApi{

	/**
	 * @param rltClassId
	 * @param sourceCiId
	 * @param targetCiId
	 * @return
	 */
	public JSONObject saveOrUpdate(BigDecimal rltClassId,BigDecimal sourceCiId,BigDecimal targetCiId, JSONObject attrs){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/saveOrUpdate";
		JSONObject param = new JSONObject();
		JSONObject ciRlt = new JSONObject();
		ciRlt.put("classId",rltClassId );
		ciRlt.put("sourceCiId",String.valueOf(sourceCiId));
		ciRlt.put("targetCiId",String.valueOf(targetCiId));
		param.put("attrs",attrs );
		param.put("ciRlt",ciRlt );
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param rltClsName
	 * @param sourceCiClass
	 * @param sourceCiCode
	 * @param targetCiClass
	 * @param targetCiCode
	 * @return
	 */
	public JSONObject saveOrUpdate(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
		BigDecimal rltId =  (new RltClassUtil()).getRltClassId(rltClsName);
		BigDecimal sourceCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(sourceCiClass, sourceCiCode);
		BigDecimal targetCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(targetCiClass, targetCiCode);
		JSONObject result = saveOrUpdate(rltId,sourceCiId,targetCiId, new JSONObject());
		return result;
	}
	
	
	/**
	 * 保存分类关系， 包含属性
	 * @param rltClsName
	 * @param sourceCiClass
	 * @param sourceCiCode
	 * @param targetCiClass
	 * @param targetCiCode
	 * @param attrs
	 * @return
	 */
	public JSONObject saveOrUpdateHaveAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		BigDecimal rltClsId =  (new RltClassUtil()).getRltClassId(rltClsName);
		BigDecimal sourceCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(sourceCiClass, sourceCiCode);
		BigDecimal targetCiId = (new SearchInfoList()).searchInfoList_getSingleCiId(targetCiClass, targetCiCode);
		JSONObject attrs = new JSONObject();
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				attrs.put(row.get(0), row.get(1));
			}
		}
		JSONObject result = saveOrUpdate(rltClsId,sourceCiId,targetCiId, attrs);
		return result;
	}
	
	/**
	 * 修改关系数据
	 * @param rltClsName
	 * @param sourceCiClass
	 * @param sourceCiCode
	 * @param targetCiClass
	 * @param targetCiCode
	 * @param attrs
	 * @return
	 */
	public JSONObject updateHaveAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		RltUtil ru = new RltUtil();
		JSONObject rltData = ru.getRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode,table);
		JSONObject ciRlt =  rltData.getJSONObject("ciRlt");
		JSONObject attrs = new JSONObject();
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				attrs.put(row.get(0), row.get(1));
			}
		}
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("attrs",attrs );
		param.put("ciRlt",ciRlt );
		return doRest(url, param.toString(), "POST");
	}
}
