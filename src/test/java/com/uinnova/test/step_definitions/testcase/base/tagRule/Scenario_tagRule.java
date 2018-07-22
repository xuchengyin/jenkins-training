package com.uinnova.test.step_definitions.testcase.base.tagRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.SearchRealInfoPage;
import com.uinnova.test.step_definitions.api.base.tagRule.GetTagTree;
import com.uinnova.test.step_definitions.api.base.tagRule.QueryDefExtInfoById;
import com.uinnova.test.step_definitions.api.base.tagRule.RemoveDefById;
import com.uinnova.test.step_definitions.api.base.tagRule.RemoveDirById;
import com.uinnova.test.step_definitions.api.base.tagRule.SaveOrUpdateDefInfo;
import com.uinnova.test.step_definitions.api.base.tagRule.SaveOrUpdateDir;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_tagRule {
	HashMap resultMap;

	private String TagDir = "";
	private List<String> tagsList = new ArrayList<String>();
	@After("@deleteTagDir")
	public void afterDelTagDir(){
		if (!TagDir.isEmpty()){
			JSONObject delResult = (new RemoveDirById()).removeDirById(TagDir);
			assertEquals(1, delResult.getInt("data"));
		}
	}


	@After("@deleteTag")
	public void afterDelTag(){
		if (tagsList!=null && tagsList.size()>0){
			for (int i=0; i<tagsList.size(); i++){
				String tagName = tagsList.get(i);
				JSONObject delResult = (new RemoveDefById()).removeDefById(tagName);
				assertEquals(1, delResult.getInt("data"));
				tagsList.remove(tagName);
				i--;
			}
		}		
	}

	/*============================tagDir的增删改==============================*/
	@When("^创建名称为\"(.*)\"的标签目录$")
	public void createTagDir(String tagDirName){
		TagDir = tagDirName;
		JSONObject saveResult = (new SaveOrUpdateDir()).saveOrUpdateDir(tagDirName);
		assertTrue(saveResult.getInt("data")>0);
	}

	@Then("^名称为\"(.*)\"的标签目录创建成功$")
	public void checkCreateTagDir(String tagDirName){
		String sql = "SELECT count(*) NUM FROM cc_ci_tag_dir c where DIR_NAME='"+tagDirName+"' and  DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		HashMap map = (HashMap) (JdbcUtil.executeQuery(sql)).get(0);
		String dirNum = map.get("NUM").toString();
		assertEquals("1", dirNum);
	}


	@When("^修改标签目录\"(.*)\"的名称为\"(.*)\"$")
	public void updateTagDirName(String sourceName,String modifyName){
		JSONObject updateResult = (new SaveOrUpdateDir()).saveOrUpdateDir(sourceName, modifyName);
		assertTrue(updateResult.getInt("data")>0);
	}

	@Then("^标签目录\"(.*)\"的名称修改为\"(.*)\"$")
	public void checkUpdateTagDirName(String sourceName,String modifyName){
		String sqlSource = "SELECT count(*) NUM FROM cc_ci_tag_dir c where DIR_NAME='" +sourceName+"' and  DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String sqlTarget = "SELECT count(*) NUM FROM cc_ci_tag_dir c where DIR_NAME='" +modifyName+"' and  DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		HashMap mapSource = (HashMap) (JdbcUtil.executeQuery(sqlSource)).get(0);
		HashMap mapTarget = (HashMap) (JdbcUtil.executeQuery(sqlTarget)).get(0);
		String sourceNum = mapSource.get("NUM").toString();
		String targetNum = mapTarget.get("NUM").toString();
		if (sourceName.compareToIgnoreCase(modifyName)!=0)
			assertEquals("0", sourceNum);
		assertEquals("1", targetNum);
	}

	@When("^删除名称为\"(.*)\"的标签目录$")
	public void delTagDir(String tagDirName){
		JSONObject delResult = (new RemoveDirById()).removeDirById(tagDirName);
		assertEquals(1, delResult.getInt("data"));
		tagsList = new ArrayList<String>();
		TagDir = "";
	}

	@Then("^系统中不存在名称为\"(.*)\"的标签目录$")
	public void checkdelTagDir(String tagDirName){
		BigDecimal dirId = (new TagRuleUtil()).getDirId(tagDirName);	
		assertTrue(dirId.compareTo(new BigDecimal(0))==0);
	}

	/*============================tag的增删改==============================*/
	@When("^在名称为\"(.*)\"的标签目录下,创建名称为\"(.*)\"的标签$")
	public void createTagRule(String tagDirName,String tagName){
		JSONObject saveResult = (new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName);
		assertTrue(saveResult.getInt("data")>0);

	}
	@Then("^名称为\"(.*)\"的标签创建成功$")
	public void checkCreateTagRule(String tagName){
		String sql = "SELECT count(*) NUM FROM cc_ci_tag_def c where  TAG_NAME='"+tagName+"' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String tagNum = ((HashMap)JdbcUtil.executeQuery(sql).get(0)).get("NUM").toString();
		assertEquals("1", tagNum);
		tagsList.add(tagName);
	}

	@When("^将名称为\"(.*)\"的标签修改为\"(.*)\"$")
	public void updateTagName(String sourceTagName,String targetTagName){
		JSONObject upResult = (new SaveOrUpdateDefInfo()).UpdateDefInfo(sourceTagName, targetTagName);
		assertTrue(upResult.getInt("data")>0);
		tagsList.remove(sourceTagName);
		tagsList.add(targetTagName);
	}

	@Then("^名称为\"(.*)\"的标签名称修改为\"(.*)\"$")
	public void checkUpdateTagName(String sourceTagName,String targetTagName){
		String sqlSource = "SELECT  count(*) NUM FROM cc_ci_tag_def c where  TAG_NAME='"+sourceTagName+"' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String sqlTarget = "SELECT  count(*) NUM FROM cc_ci_tag_def c where  TAG_NAME='"+targetTagName+"' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String sourceNameSql = ((HashMap)JdbcUtil.executeQuery(sqlSource).get(0)).get("NUM").toString();
		String targetNameSql = ((HashMap)JdbcUtil.executeQuery(sqlTarget).get(0)).get("NUM").toString();
		if (sourceTagName.compareToIgnoreCase(targetTagName)!=0)
			assertEquals("0", sourceNameSql);
		assertEquals("1", targetNameSql);
	}

	@When("^删除名称为\"(.*)\"的标签$")
	public void delTagRule(String tagName){
		JSONObject delResult = (new RemoveDefById()).removeDefById(tagName);
		assertEquals(1, delResult.getInt("data"));
		tagsList.remove(tagName);
	}

	@Then("^系统中不存在名称为\"(.*)\"的标签$")
	public void checkDelTagRule(String tagName){
		String sql = "SELECT count(*) NUM FROM cc_ci_tag_def c where TAG_NAME='"+tagName+"' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String num =((HashMap)JdbcUtil.executeQuery(sql).get(0)).get("NUM").toString();
		assertEquals("0",  num);
	}


	/*============================tag搜索==============================*/
	@When("^在名称为\"(.*)\"的标签目录下,创建名称如下的标签:\"(.*)\"$")
	public void creatMoreTagRule(String tagDirName,@Delimiter("、") List<String> tagList ){
		int flag = 0;
		JSONObject result = null;
		for(String tagName : tagList){
			result = (new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName);
			if(result.getBigDecimal("data").compareTo(new BigDecimal(0))>0){
				flag++;
				tagsList.add(tagName);
			}
		}
		assertTrue(flag!=0);
		assertEquals(flag,  tagList.size());
	}

	@And("^查询名称包含关键字的标签:$")
	public void searchTag(DataTable keyTable){
		resultMap = new HashMap<String,JSONObject>();
		List<Map<String,String>> data  = keyTable.asMaps(String.class, String.class);
		String keyword = null;
		JSONArray getTree = new JSONArray();
		for(Map map : data){
			keyword = map.get("searchKey").toString();
			getTree = (new GetTagTree()).getTagTree(keyword).getJSONArray("data");
			resultMap.put(keyword,getTree);
		}
		assertEquals(resultMap.size(),  data.size());
	}

	@Then("^标签查询返回结果正确$")
	public void checkSearchTag(DataTable keyTable){
		List<Map<String,String>> keywords =  keyTable.asMaps(String.class, String.class);
		String keyword = null;
		String sql = null;
		int sqlRes = 0;
		JSONArray apiRes = null;
		int flag = 0;
		for(Map<String,String> map : keywords){
			keyword = map.get("searchKey").toString();
			sql = "SELECT count(*) NUM FROM cc_ci_tag_def c where TAG_NAME like '%"+keyword+"%' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList sqlList  = JdbcUtil.executeQuery(sql);
			sqlRes = Integer.parseInt(((HashMap)JdbcUtil.executeQuery(sql).get(0)).get("NUM").toString());

			apiRes = (JSONArray)resultMap.get(keyword);
			int apiNum = 0;
			for(int i=0;i<apiRes.length();i++){
				JSONObject dataObj = (JSONObject) apiRes.get(i);
				JSONArray defs = dataObj.getJSONArray("defs");
				apiNum += defs.length();
			}

			//比较接口返回值与api返回值
			if(apiNum==sqlRes){
				flag++;
			}
		}
		assertEquals(flag, keywords.size());
	}


	/*============================添加单个item==============================*/
	@And("^在名称为\"(.*)\"的标签下,添加如下单条规则:$")
	public void createItems(String tagName,DataTable ruleTable){
		JSONObject result = (new SaveOrUpdateDefInfo()).saveDefInfo(tagName, ruleTable);
		assertTrue(result.getInt("data")>0);
	}

	@Then("^名称为\"(.*)\"的标签下,如下规则创建成功$")
	public void checkCreateItems(String tagName,DataTable ruleTable){
		JSONObject result = (new QueryDefExtInfoById()).queryDefExtInfoById(tagName);
		JSONArray rules = result.getJSONObject("data").getJSONArray("rules");
		HashMap<String,Integer> ruleMap = QaUtil.getNumMap(ruleTable,"ruleNum");
		List<Map<String,String>> ruleListMap =  ruleTable.asMaps(String.class, String.class);
		int ruleFlag = 0;
		int IndexFlag = 1;
		for(int i=0;i<ruleListMap.size();i++){
			String ruleNumName = ruleListMap.get(i).get("ruleNum").toString();
			int ruleNum = Integer.parseInt(ruleMap.get(ruleNumName).toString());
			int items = ((JSONObject)rules.get(ruleFlag)).getJSONArray("items").length();
			if(items==ruleNum){
				IndexFlag++;
			}
			i = i+ruleNum-1;
		}
		assertEquals(IndexFlag, ruleMap.size());
	}




	@When("^获取\"(.*)\"标签下,符合如下规则的数据$")
	public void searchRealInfo(String tagName,DataTable ruleTable){
		JSONObject apiResult = (new SearchRealInfoPage()).searchRealInfoPage(tagName);
		assertTrue(apiResult.getBoolean("success"));
	}

	@Then("^\"(.*)\"标签下,符合如下规则的数据返回正确$")
	public void checkSearchRealInfo(String tagName,DataTable ruleTable){
		//    	JSONObject apiResult = (new SearchRealInfoPage()).searchRealInfoPage(tagName).;
		int apiCount = (new SearchRealInfoPage()).searchRealInfoPage(tagName).getJSONObject("data").getInt("totalRows");
		//获取标签定义
		JSONObject queryDefInfo = (new QueryDefExtInfoById()).queryDefExtInfoById(tagName);
		JSONArray rules = queryDefInfo.getJSONObject("data").getJSONArray("rules");

		/*
		 *组装ruleMap JSONArray对象,以按照数据类型存放item 对应的数据
		 * 
		 * {
	        "big": {
	            "table": "cc_ci_big_attr_0", 
	            "attrs": { }
	        }, 
	        "short": {
	            "table": "cc_ci_short_attr_0", 
	            "attrs": {
	                "0": "SV_0  in '10.16.16.1,10.16.16.2' ", 
	                "1": "SV_1  not in '10.7.3.55' "
	        }
        } */
		HashMap<String,Integer> ruleNumMap = QaUtil.getNumMap(ruleTable,"ruleNum");
		//    	HashMap<String,Integer> ruleNumMap = (new tagRuleUtil()).getRuleNumMap(ruleTable,"ruleNum");

		List<Map<String,String>> ruleMap = ruleTable.asMaps(String.class, String.class);
		String[] typeStr = {"int","double","short","long","big"};

		JSONArray sqlRules = new JSONArray();
		for(int rm=0;rm<ruleNumMap.size();rm++){
			JSONObject sqlRule = new JSONObject();
			for(int i=0;i<typeStr.length;i++){
				JSONObject attrObj = new JSONObject();
				//        		JSONArray attrs = new JSONArray();
				JSONObject attrs = new JSONObject();
				if(typeStr[i].equals("int")){
					attrObj.put("table", "cc_ci_int_attr_0");
				}
				if(typeStr[i].equals("double")){
					attrObj.put("table", "cc_ci_doub_attr_0");
				}
				if(typeStr[i].equals("short")){
					attrObj.put("table", "cc_ci_short_attr_0");
				}
				if(typeStr[i].equals("long")){
					attrObj.put("table", "cc_ci_long_attr_0");
				}
				if(typeStr[i].equals("big")){
					attrObj.put("table", "cc_ci_big_attr_0");
				}
				attrObj.put("attrs", attrs);
				sqlRule.put(typeStr[i], attrObj);
			}
			sqlRules.put(sqlRule);
		}

		int flag=0;
		int ru = 0;
		for(int i=0;i<ruleMap.size();i++){
			JSONObject sqlRuleObj = sqlRules.getJSONObject(ru) ;
			String ciClsName = ruleMap.get(i).get("ciClsName").toString();
			sqlRuleObj.put("className", ciClsName);
			sqlRuleObj.put("classId", (new CiClassUtil()).getCiClassId(ciClsName));
			String ruleNumName = ruleMap.get(i).get("ruleNum").toString();
			int ruleCount = ruleNumMap.get(ruleNumName);

			int IV = 0;
			int DV = 0;
			int SV = 0;
			int LV = 0;
			int BV = 0;

			for(int j=0;j<ruleCount;j++){
				String attrName = ruleMap.get(flag).get("attrName").toString();
				int  attrType = (new TagRuleUtil()).getAttrType(ciClsName, attrName);
				assertTrue(attrType>0);
				String attrSort = (new CiClassUtil()).getCiClsAttrSort(ciClsName, attrName, attrType);
				String ruleOp = ruleMap.get(flag).get("ruleOp").toString();
				String ruleVal = ruleMap.get(flag).get("ruleVal").toString();
				if(ruleOp.equals("!=")){
					ruleOp = "<>";
				}
				if(ruleOp.equals("like") || ruleOp.equals("not like")){
					ruleOp = " " +ruleOp+ " ";
					ruleVal = " '"+ruleVal+"' ";
				}
				if(ruleOp.equals("in")|| ruleOp.equals("not in")){
					ruleOp = " " +ruleOp+ " ";
					String[] ruleValArr = ruleVal.split(",");
					String inStr = " (";
					for(int rr=0;rr<ruleValArr.length;rr++){
						inStr = inStr +"'"+ruleValArr[rr]+"'";
						if(rr!=(ruleValArr.length-1)){
							inStr += ",";
						}
					}
					ruleVal = inStr+") ";
				}

				JSONObject obj = new JSONObject();
				String sqlStr = null;
				JSONObject attr =  new JSONObject();
				if(attrType==1){
					JSONObject intObj = (JSONObject)sqlRuleObj.get("int");
					JSONObject attrsTmp = intObj.getJSONObject("attrs");
					//使用属性存储顺序IV
					sqlStr = attrSort+ruleOp+Integer.parseInt(ruleVal);
					//	    				 sqlStr = "IV_"+IV+ruleOp+Integer.parseInt(ruleVal);
					attrsTmp.put(String.valueOf(IV), sqlStr);
					IV++;
				}
				if(attrType==2){
					JSONObject doubObj = (JSONObject)sqlRuleObj.get("double");
					JSONObject attrsTmp = doubObj.getJSONObject("attrs");
					sqlStr = attrSort+ruleOp+Double.parseDouble(ruleVal);
					//	    				 sqlStr = "DV_"+BV+ruleOp+Double.parseDouble(ruleVal);
					attrsTmp.put(String.valueOf(DV), sqlStr);
					DV++;
				}
				if(attrType==3 || attrType==6 ||attrType==7||attrType==8 ){
					JSONObject shortObj = (JSONObject)sqlRuleObj.get("short");
					JSONObject attrsTmp = shortObj.getJSONObject("attrs");
					if(ruleOp.indexOf("in")>=0 || ruleOp.indexOf("like")>=0){
						sqlStr = attrSort+" "+ruleOp+" "+ruleVal+" ";
					}else{
						sqlStr = attrSort+" "+ruleOp+" '"+ruleVal+"' ";
					}
					//	    				 sqlStr = "SV_"+SV+" "+ruleOp+"'"+ruleVal+"' ";
					attrsTmp.put(String.valueOf(SV), sqlStr);
					SV++;
				}
				if(attrType==4){
					JSONObject longObj = (JSONObject)sqlRuleObj.get("long");
					JSONObject attrsTmp = longObj.getJSONObject("attrs");
					sqlStr = attrSort+" "+ruleOp+" "+ruleVal+" ";
					//	    				 sqlStr = "LV_"+LV+" "+ruleOp+"'"+ruleVal+"' ";
					attrsTmp.put(String.valueOf(LV), sqlStr);
					LV++;
				}
				if(attrType==5){
					JSONObject bigObj = (JSONObject)sqlRuleObj.get("big");
					JSONObject attrsTmp = bigObj.getJSONObject("attrs");
					sqlStr = attrSort+" "+ruleOp+" "+ruleVal+" ";
					//	    				 sqlStr = "BV_"+BV+" "+ruleOp+"'"+ruleVal+"' ";
					attrsTmp.put(String.valueOf(BV), sqlStr);
					BV++;
				}
				flag++;
			}
			ru++;
			//			sqlRules.put(sqlRuleObj);
			i = i+ruleCount-1;
		}

		// 遍历每个rule
		String sql = "select count(ID) NUM from cc_ci where  DOMAIN_ID = "+ QaUtil.domain_id+" and DATA_STATUS=1  and ID in ( ";
		for (int ss = 0; ss < sqlRules.length(); ss++) {
			JSONObject sqlRule = (JSONObject) sqlRules.get(ss);
			BigDecimal clsId = sqlRule.getBigDecimal("classId");
			int ruleFlag = 0;
			// 遍历每个item
			for (int ts = 0; ts < typeStr.length; ts++) {

				String tableName = null;
				tableName = sqlRule.getJSONObject(typeStr[ts]).getString("table");
				JSONObject attrs = sqlRule.getJSONObject(typeStr[ts]).getJSONObject("attrs");
				if (attrs.length() != 0) {

					if (ruleFlag != 0) {
						sql += " and ID in ( ";
					}
					sql += " select ID from " + tableName + " where CLASS_ID=" + clsId
							+ " and DATA_STATUS=1  and DOMAIN_ID = "+ QaUtil.domain_id;
					for (int as = 0; as < attrs.length(); as++) {
						sql += " and " + attrs.getString(String.valueOf(as));
					}
					if (ruleFlag != 0) {
						sql += " ) ";

					}
					ruleFlag++;
				}
			}
			if (ss != (sqlRules.length() - 1)) {
				sql += "union all ";

			}
		}
		sql += " )";
		String sqlCount = ((HashMap)JdbcUtil.executeQuery(sql).get(0)).get("NUM").toString();
		assertEquals(sqlCount, String.valueOf(apiCount));
	}



}
