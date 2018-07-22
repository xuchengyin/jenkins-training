package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRltClass.QueryAll;
import com.uinnova.test.step_definitions.api.base.ciRltClass.QueryById;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 关系分类工具类
 * @author uinnova
 *
 */
public class RltClassUtil {

	/**
	 * @param rltClassName
	 * @return
	 */
	public BigDecimal getRltClassId(String rltClassName){
		JSONObject allRltClass = (new QueryAll()).queryAll();
		int rltClassNum = allRltClass.getJSONArray("data").length();
		BigDecimal rltClassId = new BigDecimal(0);
		boolean flag = false;
		for(int i=0;i<rltClassNum;i++){
			JSONObject ciClass = (JSONObject) allRltClass.getJSONArray("data").get(i);
			if(ciClass.getJSONObject("ciClass").getString("className").equals(rltClassName)){
				rltClassId = ciClass.getJSONObject("ciClass").getBigDecimal("id");
				flag = true;
				break;
			}
		}
		if (!flag)
		{
			QaUtil.report("========关系分类"+rltClassName+"不存在====");
		}
		return rltClassId;
	}

	/**
	 * 获取属性idJSONObject{属性名：属性id}
	 * @param rltClassName
	 * @return
	 */
	public JSONObject getRltAttrId(String rltClassName) {
		JSONObject qi = (new QueryById()).queryById(rltClassName);
		JSONObject attr = null;
		if(qi.toString().indexOf("attrDefs")>=0) {
			JSONArray attrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
			if(attrDefs.length()>0) {
				attr = new JSONObject();
				for(int i=0;i<attrDefs.length();i++) {
					String proName = attrDefs.getJSONObject(i).getString("proName");
					int  id = attrDefs.getJSONObject(i).getInt("id");
					attr.put(proName, id);
				}
			}
		}
		return attr;
	}


	/**
	 * 关系的某一属性是否是isCode
	 * @param rltClassName
	 * @param attrName
	 * @return
	 */
	public boolean rltAttrIsCode(String rltClassName, String attrName) {
		JSONObject qi = (new QueryById()).queryById(rltClassName);
		JSONObject attr = null;
		boolean iscode = false;
		if(qi.toString().indexOf("attrDefs")>=0) {
			JSONArray attrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
			if(attrDefs.length()>0) {
				attr = new JSONObject();
				for(int i=0;i<attrDefs.length();i++) {
					String proName = attrDefs.getJSONObject(i).getString("proName");
					if(attrName.compareToIgnoreCase(proName)==0)
					{
						BigDecimal isCode = attrDefs.getJSONObject(i).getBigDecimal("isCode");
						if (isCode.compareTo(new BigDecimal(1))==0)
							iscode = true;
					}
				}
			}
		}
		return iscode;
	}
}
