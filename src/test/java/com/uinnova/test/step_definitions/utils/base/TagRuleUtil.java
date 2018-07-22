package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRltRule.QueryRuleClassList;
import com.uinnova.test.step_definitions.api.base.tagRule.GetTagTree;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author uinnova
 *
 */
public class TagRuleUtil {

	/**
	 * @param tagName
	 * @return
	 */
	public BigDecimal getTagId(String tagName){
		JSONObject result = (new GetTagTree()).getTagTree(tagName);
		JSONArray data = result.getJSONArray("data");
		BigDecimal tagId = new BigDecimal(0);
		for(Object dataObj : data){
			boolean existFlag = false;
			JSONObject dataTmp = ((JSONObject)dataObj);
			if(dataTmp.toString().indexOf("defs")>0){
				JSONArray defs = dataTmp.getJSONArray("defs");
				for(Object defObj : defs){
					JSONObject defTmp = (JSONObject) defObj;
					String name = defTmp.getString("tagName");
					if(name.equals(tagName)){
						tagId = defTmp.getBigDecimal("id");
						existFlag = true;
						break;
					}
				}
			}
			if (existFlag)
				break;
		}
		return tagId;
	}


	/**
	 * @param tagName
	 * @return
	 */
	public BigDecimal getDirIdByTagName(String tagName){
		JSONObject result = (new GetTagTree()).getTagTree(tagName);
		JSONArray data = result.getJSONArray("data");
		BigDecimal dirId = new BigDecimal(0);
		for(Object dataObj : data){
			boolean existFlag = false;
			JSONObject dataTmp = ((JSONObject)dataObj);
			if(dataTmp.toString().indexOf("defs")>0){
				JSONArray defs = dataTmp.getJSONArray("defs");
				for(Object defObj : defs){
					JSONObject defTmp = (JSONObject) defObj;
					String name = defTmp.getString("tagName");
					if(name.equalsIgnoreCase(tagName)){
						dirId = dataTmp.getJSONObject("dir").getBigDecimal("id");
						String dirName = dataTmp.getJSONObject("dir").getString("dirName");
						QaUtil.report("===标签==="+dirName+"===的Id为==="+dirId+"====");
						existFlag =true;
						break;
					}
				}
			}
			if (existFlag)
				break;
		}
		return dirId;
	}

	/**
	 * @param dirName
	 * @return
	 */
	public BigDecimal getDirId(String dirName){
		JSONObject result = (new GetTagTree()).getTagTree("");
		JSONArray data = result.getJSONArray("data");
		BigDecimal dirId = new BigDecimal(0);
		for(Object obj : data){
			JSONObject dir = ((JSONObject)obj).getJSONObject("dir");
			if(dir.getString("dirName").equalsIgnoreCase(dirName)){
				dirId = dir.getBigDecimal("id");
				break;
			}
		}
		return dirId;
	}

	/**
	 * 获取分类属性id
	 * @param ciClsName
	 * @param attrName
	 * @return
	 */
	public BigDecimal getAttrId(String ciClsName,String attrName){
		JSONObject qc =  (new QueryRuleClassList()).queryRuleClassList(ciClsName);
		JSONArray datas =  qc.getJSONArray("data");
		BigDecimal attrId = new BigDecimal(0);
		for(Object data : datas){
			boolean existFlag = false;
			JSONObject dataObj = (JSONObject) data;
			String clsName = dataObj.getJSONObject("ciClass").getString("className");
			if(clsName.equalsIgnoreCase(ciClsName)){
				JSONArray attrDefs  = dataObj.getJSONArray("attrDefs");
				for(Object attrDef : attrDefs ){
					JSONObject attrDefObj = (JSONObject)attrDef;
					if(attrDefObj.getString("proName").equals(attrName)){
						attrId = attrDefObj.getBigDecimal("id");
						existFlag = true;
						break;
					}
				}
			}
			if (existFlag)
				break;
		}
		if(attrId.compareTo(new BigDecimal(0))==0){
			QaUtil.report("==找不到属性"+attrName+"==");
		}
		return attrId;
	}

	/**
	 * 获取分类属性类型
	 * @param ciClsName
	 * @param attrName
	 * @return
	 */
	public int getAttrType(String ciClsName,String attrName){
		JSONObject qc =  (new QueryRuleClassList()).queryRuleClassList(ciClsName);
		JSONArray datas =  qc.getJSONArray("data");
		int attrType = 0;
		for(Object data : datas){
			boolean existFlag = false;
			JSONObject dataObj = (JSONObject) data;
			String clsName = dataObj.getJSONObject("ciClass").getString("className");
			if(clsName.equals(ciClsName)){
				JSONArray attrDefs  = dataObj.getJSONArray("attrDefs");
				for(Object attrDef : attrDefs ){
					JSONObject attrDefObj = (JSONObject)attrDef;
					if(attrDefObj.getString("proName").equals(attrName)){
						attrType = attrDefObj.getInt("proType");
						existFlag = true;
						break;
					}
				}
			}
			if (existFlag)
				break;
		}
		return attrType;
	}


	/**
	 * @param ruleOpName
	 * @return
	 */
	public String getRuleOp(String ruleOpName){
		String opType ="";
		if (ruleOpName.isEmpty())
			return opType;
		String ruleOpType = null;
		String[] ruleOpList = {"=","!=","<","<=",">",">=","like","not like","in","not in"};
		HashMap map = new HashMap<String,String>();
		for(int i=0;i<ruleOpList.length;i++){
			map.put(ruleOpList[i],String.valueOf(i+1));
		}
		if (map.containsKey(ruleOpName))
			opType =  map.get(ruleOpName).toString();
		return opType;
	}

}
