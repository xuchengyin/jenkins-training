package com.uinnova.test.step_definitions.api.dmv.ci;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

/**
 * @author wsl
 * ci新增
 *
 */
public class SaveOrUpdate extends RestApi{
	
		
	/**
	 * @param ciInfo
	 * @return
	 * 新增一个ci 待改进针对绘图新增application的数据
	 */
	public JSONObject saveOrUpdateSingCi(BigDecimal ciClassId, JSONObject  attrs){
		JSONObject ciObj = new JSONObject();
		ciObj.put("attrs", attrs);
		ciObj.put("ciClassId", ciClassId);
		String url = ":1511/tarsier-vmdb/dmv/ci/saveOrUpdate";
		return doRest(url,ciObj.toString(), "POST");
	}
	/**
	 * @param ciId
	 * @param ciInfo
	 * @param attrs
	 * @return
	 * 新增一个ci 待改进针对绘图新增application的数据
	 */
	public JSONObject saveOrUpdateEditCi(String ciClassName, JSONArray attrs,String ciCode,String proName,String proValue){
		String url = ":1511/tarsier-vmdb/dmv/ci/saveOrUpdate";
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal ciClassId = ciClassUtil.getCiClassId(ciClassName);
		CiUtil ciu = new CiUtil();		
		BigDecimal ciId = ciu.getCiId(ciCode);
		
		JSONObject ciObj = new JSONObject();
		JSONObject attrsObj = new JSONObject();
		
		if(attrs.length() > 0){
			for(int i = 0; i < attrs.length(); i++){
			   JSONObject obj = (JSONObject)attrs.get(i);
			   attrsObj.put(obj.getString("key"), obj.getString("value"));
			}
		}
		attrsObj.put(proName, proValue);
		attrsObj.put("CI Code", ciCode);
		ciObj.put("attrs", attrsObj);
		ciObj.put("ciClassId", ciClassId);
		ciObj.put("ciId", ciId);
		
		return doRest(url,ciObj.toString(), "POST");
	}
}
