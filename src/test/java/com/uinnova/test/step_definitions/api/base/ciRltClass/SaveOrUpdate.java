package com.uinnova.test.step_definitions.api.base.ciRltClass;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.testcase.base.rlt.rltClass.Scenario_rltClass;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;

public class SaveOrUpdate extends RestApi{

	/**
	 * @param attrDefs
	 * @param ciClass
	 * @return
	 */
	public JSONObject saveOrUpdate(JSONArray attrDefs,JSONObject ciClass){
		String url = ":1511/tarsier-vmdb/cmv/ciRltClass/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("attrDefs", attrDefs);
		param.put("ciClass", ciClass);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * @param rltClassName
	 * @param lineAnime
	 * @param lineType
	 * @param lineBorder
	 * @param lineDirect
	 * @param lineDispType   1默认显示， 2 容器
	 * @param table
	 * @return
	 */
	public JSONObject saveOrUpdate(String rltClassName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineDispType, DataTable table){
		JSONArray attrDefs = new JSONArray();
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				JSONObject attrDefsObj = new JSONObject();
				List<String> row = table.raw().get(i);
				String attrName = row.get(0);
				String attrType = row.get(1);
				//				String attrType = (new CiClassUtil()).getDataType(row, 1);
				String enumVal = row.get(2);
				
				String isCode = "0";
				if (row.size()>=4)
					isCode=row.get(3);
				attrDefsObj.put("defVal", "");
				attrDefsObj.put("enumValues", enumVal);
				attrDefsObj.put("orderNo", i);
				attrDefsObj.put("proDesc", "");
				attrDefsObj.put("proName", attrName);
				attrDefsObj.put("proType", attrType);
				attrDefsObj.put("isCode", new BigDecimal(isCode));
				/*********************接口参数变更***************************************/
				attrDefsObj.put("lineLabelAlign", 1);
				//				attrDefsObj.put("lineLabelAlign", 1);
				/*********************接口参数变更***************************************/
				attrDefs.put(attrDefsObj);
			}
		}
		JSONObject ciClass = new JSONObject();
		
		ciClass.put("ciType", 2);
		ciClass.put("classCode", rltClassName);
		ciClass.put("classDesc", "");
		ciClass.put("classLvl", 1);
		ciClass.put("className", rltClassName);
		ciClass.put("costType", 1);
		ciClass.put("icon", "");
		/*********************接口参数变更***************************************/
		ciClass.put("lineLabelAlign", 3);
		ciClass.put("lineDispType", Integer.parseInt(lineDispType));
		ciClass.put("lineAnime", Integer.parseInt(lineAnime));
		/*********************接口参数变更***************************************/
		ciClass.put("lineBorder", Integer.parseInt(lineBorder));
		ciClass.put("lineColor", "#000");
		ciClass.put("lineDirect", lineDirect);
		ciClass.put("lineType", lineType);
		ciClass.put("parentId", 0);
		return saveOrUpdate(attrDefs,ciClass);
	}

	//使用默认线型创建关系分类
	public JSONObject saveOrUpdateUseDefaultLine(String rltClassName,DataTable table){
		String lineAnime = "0";
		String lineType = "solid";
		String lineBorder = "1" ;
		String lineDirect = "classic"; 
		JSONArray attrDefs = new JSONArray();
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				JSONObject attrDefsObj = new JSONObject();
				List<String> row = table.raw().get(i);
				String attrName = row.get(0);
				//					String attrType = row.get(1);
				String attrType = (new CiClassUtil()).getDataType(row, 1);
				String enumVal = row.get(2);
				attrDefsObj.put("defVal", "");
				attrDefsObj.put("enumValues", enumVal);
				attrDefsObj.put("orderNo", i);
				attrDefsObj.put("proDesc", "");
				attrDefsObj.put("proName", attrName);
				attrDefsObj.put("proType", attrType);
				attrDefsObj.put("lineLabelAlign", 1);
				
				attrDefs.put(attrDefsObj);
			}
		}
		JSONObject ciClass = new JSONObject();
		ciClass.put("ciType", 2);
		ciClass.put("classCode", rltClassName);
		ciClass.put("classDesc", "");
		ciClass.put("classLvl", 1);
		ciClass.put("className", rltClassName);
		ciClass.put("costType", 1);
		ciClass.put("icon", "");
		ciClass.put("lineAnime", Integer.parseInt(lineAnime));
		ciClass.put("lineBorder", Integer.parseInt(lineBorder));
		ciClass.put("lineColor", "#000");
		ciClass.put("lineDirect", lineDirect);
		ciClass.put("lineType", lineType);
		ciClass.put("parentId", 0);
		ciClass.put("lineLabelAlign", 3);
		ciClass.put("lineDispType", 1);
		return saveOrUpdate(attrDefs,ciClass);
	}

	/**
	 * 创建无属性关系分类
	 * @param rltName
	 * @return
	 */
	public JSONObject saveOrUpdateWithoutAttr(String rltName){
		String lineAnime = "0";
		String lineType = "solid";
		String lineBorder = "1" ;
		String lineDirect = "classic"; 
		DataTable table = null;
		JSONObject res = saveOrUpdate(rltName,lineAnime,lineType,lineBorder,lineDirect,"1",table);
		return res;
	}
}
