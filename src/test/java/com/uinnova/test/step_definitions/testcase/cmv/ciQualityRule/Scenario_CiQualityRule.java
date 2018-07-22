package com.uinnova.test.step_definitions.testcase.cmv.ciQualityRule;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.ActivateCiQualityRule;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryCiQualityInfoById;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryCiQualityPage;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryCiQualityRuleTagDataPage;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryFailureCiInfoPage;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.RemoveRuleById;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.SaveOrUpdateRuleInfo;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_CiQualityRule {
	private static JSONObject queryCiQualityRulePageResult = new JSONObject();
	private static JSONObject queryCiQualityRuleResult = new JSONObject();
	List queryCiQualityRuleTagDataPageArr = new ArrayList();
	List queryFailureCiInfoPageArr = new ArrayList();
	private DataTable ciTable;//CI筛选条件
	private Map<String, List<String>> ciQualityRuleMap = new HashMap<String, List<String>>(); 

	@After("@delCiQualityRule")
	public void delCiQualityRule(){
		if (!ciQualityRuleMap.isEmpty()){
			RemoveRuleById removeRuleById = new RemoveRuleById();
			for(String key : ciQualityRuleMap.keySet()){ 
				List valueList = ciQualityRuleMap.get(key); 
				for (int i=0; i<valueList.size(); i++){
					String ruleName = (String) valueList.get(i);
					removeRuleById.removeRuleById(key, ruleName);
					valueList.remove(i);
					i--;
				}
			}
		}
	}

	@When("^新建类型为\"(.*)\",名称为\"(.*)\",描述为\"(.*)\"的仪表盘规则$")
	public void createCiQualityRule(String ruleTypeName, String ruleName, String ruleDesc){
		SaveOrUpdate su = new SaveOrUpdate();
		su.saveOrUpdate(ruleTypeName, ruleName, ruleDesc);
		List<String> ruleNameList = new ArrayList<String>();
		if (!ciQualityRuleMap.isEmpty() && ciQualityRuleMap.containsKey(ruleTypeName))
			ruleNameList = ciQualityRuleMap.get(ruleTypeName);
		ruleNameList.add(ruleName);
		ciQualityRuleMap.put(ruleTypeName, ruleNameList);
	}

	@Then("^成功新建类型为\"(.*)\",名称为\"(.*)\",描述为\"(.*)\"的仪表盘规则$")
	public void checkCreateCiQualityRule(String ruleTypeName, String ruleName, String ruleDesc){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 and STATUS=0  order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条激活的记录",1,dbList.size());
	}

	@And("^再次新建类型为\"(.*)\",名称为\"(.*)\",描述为\"(.*)\"的仪表盘规则失败,kw=\"(.*)\"$")
	public void againCreateCiQualityRule(String ruleTypeName, String ruleName, String ruleDesc, String kw){
		SaveOrUpdate su = new SaveOrUpdate();
		JSONObject result = su.saveOrUpdateAgain(ruleTypeName, ruleName, ruleDesc, kw);
		assertEquals(null, result);
	}

	@When("^修改类型为\"(.*)\",名称为\"(.*)\",描述为\"(.*)\"的仪表盘规则名称为\"(.*)\",描述为\"(.*)\"$")
	public void modifyCiQualityRule(String ruleTypeName, String ruleName, String ruleDesc, String newRuleName, String newRuleDesc){
		SaveOrUpdate su = new SaveOrUpdate();
		su.saveOrUpdate(ruleTypeName, ruleName, ruleDesc, newRuleName, newRuleDesc);
		List<String> ruleNameList = new ArrayList<String>();
		if (!ciQualityRuleMap.isEmpty() && ciQualityRuleMap.containsKey(ruleTypeName))
			ruleNameList = ciQualityRuleMap.get(ruleTypeName);
		ruleNameList.remove(ruleName);
		ruleNameList.add(newRuleName);
		ciQualityRuleMap.put(ruleTypeName, ruleNameList);
	}

	@Then("^成功修改类型为\"(.*)\",名称为\"(.*)\",描述为\"(.*)\"的仪表盘规则名称为\"(.*)\",描述为\"(.*)\"$")
	public void checkModifyCiQualityRule(String ruleTypeName, String ruleName, String ruleDesc,  String newRuleName, String newRuleDesc){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String oldSql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 and STATUS=0  order by  MODIFY_TIME DESC";
		List oldDbList =  JdbcUtil.executeQuery(oldSql);
		if (ruleName.compareToIgnoreCase(newRuleName)==0)
			assertEquals("名称不改的时候",1,oldDbList.size());
		else
			assertEquals("原来名称不存在",0,oldDbList.size());

		String sql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+newRuleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 and STATUS=0  order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在更名后的记录",1,dbList.size());
	}


	@When("^按照关键字\"(.*)\"搜索\"(.*)\"类型的仪表盘规则$")
	public void queryCiQualityRule(String searchKey, String ruleTypeName){
		QueryCiQualityPage qup = new QueryCiQualityPage();
		queryCiQualityRulePageResult = qup.queryCiQualityPage(searchKey, ruleTypeName, 1, 1000);
		queryCiQualityRulePageResult = queryCiQualityRulePageResult.getJSONObject("data");
	}

	@Then("^成功按照关键字\"(.*)\"搜索\"(.*)\"类型的仪表盘规则$")
	public void checkQueryCiQualityRule(String searchKey, String ruleTypeName){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "select ID, RULE_NAME, RULE_DESC, RULE_TYPE, RULE_SUB_TYPE, TAG_ID,STATUS, USE_STATUS, VALID_ERR_MSG, DOMAIN_ID, DATA_STATUS, CREATOR,MODIFIER, CREATE_TIME, MODIFY_TIME  from CC_CI_QUALITY_RULE   where   RULE_NAME like '%"+searchKey.trim()+"%' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		JSONArray arr = queryCiQualityRulePageResult.getJSONArray("data");
		assertEquals(dbList.size(), arr.length());
		assertEquals(dbList.size(), queryCiQualityRulePageResult.getInt("totalRows"));

		for (int i=0; i<arr.length(); i++){
			JSONObject tempObj = arr.getJSONObject(i);
			Map tempMap = (Map) dbList.get(i);
			assertEquals(tempObj.getBigDecimal("status"),tempMap.get("STATUS"));
			assertEquals(tempObj.getBigDecimal("ruleSubType"),tempMap.get("RULE_SUB_TYPE"));
			assertEquals(tempObj.getBigDecimal("id"),tempMap.get("ID"));
			assertEquals(tempObj.getBigDecimal("ruleType"),tempMap.get("RULE_TYPE"));
			assertEquals(tempObj.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
			assertEquals(tempObj.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
			assertEquals(tempObj.getBigDecimal("useStatus"),tempMap.get("USE_STATUS"));
			if (tempMap.get("RULE_DESC")!=null)
				assertEquals(tempObj.getString("ruleDesc"),tempMap.get("RULE_DESC"));
			assertEquals(tempObj.getString("ruleName"),tempMap.get("RULE_NAME"));
			assertEquals(tempObj.getString("creator"),tempMap.get("CREATOR"));
			assertEquals(tempObj.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
			assertEquals(tempObj.getString("modifier"),tempMap.get("MODIFIER"));
			assertEquals(tempObj.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		}
	}


	@When("^查询类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则$")
	public void queryCiQualityInfoById(String ruleTypeName, String ruleName){
		QueryCiQualityInfoById query = new QueryCiQualityInfoById();
		queryCiQualityRuleResult = query.queryCiQualityInfoById(ruleTypeName, ruleName);
		queryCiQualityRuleResult= queryCiQualityRuleResult.getJSONObject("data");
	}

	@Then("^成功查询类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则$")
	public void checkQueryCiQualityInfoById(String ruleTypeName, String ruleName){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "select ID, RULE_NAME, RULE_DESC, RULE_TYPE, RULE_SUB_TYPE, TAG_ID,STATUS, USE_STATUS, VALID_ERR_MSG, DOMAIN_ID, DATA_STATUS, CREATOR,MODIFIER, CREATE_TIME, MODIFY_TIME  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条记录",1, dbList.size());
		JSONObject ciQualityRule = queryCiQualityRuleResult.getJSONObject("ciQualityRule");
		Map tempMap = (Map) dbList.get(0);
		assertEquals(ciQualityRule.getBigDecimal("status"),tempMap.get("STATUS"));
		assertEquals(ciQualityRule.getBigDecimal("ruleSubType"),tempMap.get("RULE_SUB_TYPE"));
		assertEquals(ciQualityRule.getBigDecimal("id"),tempMap.get("ID"));
		assertEquals(ciQualityRule.getBigDecimal("ruleType"),tempMap.get("RULE_TYPE"));
		assertEquals(ciQualityRule.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
		assertEquals(ciQualityRule.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
		assertEquals(ciQualityRule.getBigDecimal("useStatus"),tempMap.get("USE_STATUS"));
		if (tempMap.get("RULE_DESC")!=null)
			assertEquals(ciQualityRule.getString("ruleDesc"),tempMap.get("RULE_DESC"));
		assertEquals(ciQualityRule.getString("ruleName"),tempMap.get("RULE_NAME"));
		assertEquals(ciQualityRule.getString("creator"),tempMap.get("CREATOR"));
		assertEquals(ciQualityRule.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
		assertEquals(ciQualityRule.getString("modifier"),tempMap.get("MODIFIER"));
		assertEquals(ciQualityRule.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		JSONArray ciQualityRuleAttrs = queryCiQualityRuleResult.getJSONArray("ciQualityRuleAttrs");
		for (int i=0; i<ciQualityRuleAttrs.length(); i++){

		}
		JSONArray ciQualityRuleRlts = queryCiQualityRuleResult.getJSONArray("ciQualityRuleRlts");
		for (int j=0; j<ciQualityRuleRlts.length(); j++){

		}
	}

	@When("^激活类型为\"(.*)\"名称为\"(.*)\"的仪表盘规则$")
	public void activeCiQualityRule(String ruleTypeName, String ruleName){
		ActivateCiQualityRule acqr = new ActivateCiQualityRule();
		acqr.activateCiQualityRule(ruleTypeName, ruleName, 1);
	}

	@Then("^成功激活类型为\"(.*)\"名称为\"(.*)\"的仪表盘规则$")
	public void checkActiveCiQualityRule(String ruleTypeName, String ruleName){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 and STATUS=1  order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条激活的记录",1,dbList.size());

	}

	@When("^删除类型为\"(.*)\"名称为\"(.*)\"的仪表盘规则$")
	public void removeRuleById(String ruleTypeName, String ruleName){
		RemoveRuleById removeRuleById = new RemoveRuleById();
		removeRuleById.removeRuleById(ruleTypeName, ruleName);
		List<String> ruleNameList = ciQualityRuleMap.get(ruleTypeName);
		ruleNameList.remove(ruleName);
		ciQualityRuleMap.put(ruleTypeName, ruleNameList);
	}

	@Then("^成功删除类型为\"(.*)\"名称为\"(.*)\"的仪表盘规则$")
	public void checkRemoveRuleById(String ruleTypeName, String ruleName){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中不存在记录", 0, dbList.size());
	}


	@When("^新建如下仪表盘规则:$")
	public void batchCreateCiQualityRule(DataTable table){
		SaveOrUpdate su = new SaveOrUpdate();
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			List<String> row = table.raw().get(i);
			String ruleTypeName = row.get(1);
			String ruleName = row.get(2);
			String ruleDesc = row.get(3);
			su.saveOrUpdate(ruleTypeName, ruleName, ruleDesc);
			List<String> ruleNameList = new ArrayList<String>();
			if (!ciQualityRuleMap.isEmpty() && ciQualityRuleMap.containsKey(ruleTypeName))
				ruleNameList = ciQualityRuleMap.get(ruleTypeName);
			ruleNameList.add(ruleName);
			ciQualityRuleMap.put(ruleTypeName, ruleNameList);
		}
	}

	@Then("^成功新建如下仪表盘规则:$")
	public void checkBatchCreateCiQualityRule(DataTable table){
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			List<String> row = table.raw().get(i);
			String ruleTypeName = row.get(1);
			String ruleName = row.get(2);
			int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
			String sql = "select ID  from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
					+" and RULE_TYPE = "+ruleType
					+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 order by  MODIFY_TIME DESC";
			List dbList =  JdbcUtil.executeQuery(sql);
			assertEquals("数据库中存在一条的记录",1,dbList.size());
		}
	}

	@When("^给类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则增加规则定义-设置CI筛选范围为:$") //
	public void saveOrUpdateRuleInfoSetRule(String ruleTypeName, String ruleName,DataTable table){
		ciTable = table;
	}

	@And("^给类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则增加规则定义-设置检查条件:$")
	public void saveOrUpdateRuleInfoSetRuleAttr(String ruleTypeName, String ruleName,DataTable table){
		SaveOrUpdateRuleInfo saveOrUpdateRuleInfo = new SaveOrUpdateRuleInfo();
		saveOrUpdateRuleInfo.saveOrUpdateRuleInfoSetRule(ruleTypeName, ruleName, ciTable, table);
	}


	@Then("^成功给类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则增加规则定义:$")
	public void checkSaveOrUpdateRuleInfo(String ruleTypeName, String ruleName){

	}


	@When("^预览类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则筛选范围数据$")
	public void queryCiQualityRuleTagDataPage(String ruleTypeName, String ruleName){
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRule = result.getJSONObject("data").getJSONObject("ciQualityRule");
		String tagId = String.valueOf(ciQualityRule.get("tagId"));
		JSONObject tagRuleInfo = result.getJSONObject("data").getJSONObject("tagRuleInfo");
		BigDecimal classId  =new BigDecimal(0);
		JSONArray rules = tagRuleInfo.getJSONArray("rules");
		QueryCiQualityRuleTagDataPage queryCiQualityRuleTagDataPage = new QueryCiQualityRuleTagDataPage();
		for (int i=0; i<rules.length(); i++){
			JSONObject rulesObj = rules.getJSONObject(i);
			JSONObject ruleObj = rulesObj.getJSONObject("rule");
			classId = ruleObj.getBigDecimal("classId");
			JSONObject queryResult = queryCiQualityRuleTagDataPage.queryCiQualityRuleTagDataPage(tagId, classId, 1, 1000);
			queryCiQualityRuleTagDataPageArr.add(queryResult);
		}
	}


	@Then("^成功预览类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则筛选范围数据$")
	public void checkQueryCiQualityRuleTagDataPage(String ruleTypeName, String ruleName){
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRule = result.getJSONObject("data").getJSONObject("ciQualityRule");
		String tagId = String.valueOf(ciQualityRule.get("tagId"));
		JSONObject tagRuleInfo = result.getJSONObject("data").getJSONObject("tagRuleInfo");
		BigDecimal classId  =new BigDecimal(0);
		JSONArray rules = tagRuleInfo.getJSONArray("rules");
		for (int i=0; i<rules.length(); i++){
			JSONObject rulesObj = rules.getJSONObject(i);
			JSONObject ruleObj = rulesObj.getJSONObject("rule");
			classId = ruleObj.getBigDecimal("classId");
			JSONObject queryResult = (JSONObject) queryCiQualityRuleTagDataPageArr.get(i);
			String sql = "select ID, CI_CODE, CI_DESC, CLASS_ID, SOURCE_ID, OWNER_ID,    ORG_ID, SUB_CLASS, CI_VERSION, CUSTOM_1, CUSTOM_2, CUSTOM_3,    CUSTOM_4, CUSTOM_5, CUSTOM_6, DOMAIN_ID, DATA_STATUS, CREATOR,    MODIFIER, CREATE_TIME, MODIFY_TIME     "
					+" from CC_CI     where       DOMAIN_ID = "+QaUtil.domain_id
					+" and    DATA_STATUS = 1 and id in (select ID  FROM CC_CI where DATA_STATUS = 1 AND DOMAIN_ID = "+ QaUtil.domain_id+" AND CLASS_ID = "+classId+") order by ID ";
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(l.size(), queryResult.getJSONObject("data").getInt("totalRows"));
			JSONArray records =  queryResult.getJSONObject("data").getJSONObject("data").getJSONArray("records");
			assertEquals(l.size(), records.length());
			for (int j =0; j<records.length(); j++){
				JSONObject record = records.getJSONObject(j).getJSONObject("ci");
				Map tempMap = (Map) l.get(j);
				assertEquals(record.getBigDecimal("ownerId"),tempMap.get("OWNER_ID"));
				assertEquals(record.getBigDecimal("id"),tempMap.get("ID"));
				assertEquals(record.getBigDecimal("sourceId"),tempMap.get("SOURCE_ID"));
				assertEquals(record.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
				assertEquals(record.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
				assertEquals(record.getBigDecimal("classId"),tempMap.get("CLASS_ID"));
				assertEquals(record.getString("ciCode"),tempMap.get("CI_CODE"));
				assertEquals(record.getString("creator"),tempMap.get("CREATOR"));
				assertEquals(record.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
				assertEquals(record.getString("modifier"),tempMap.get("MODIFIER"));
				assertEquals(record.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
			}
		}
	}


	@When("^预览类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则不合规的CI数据$")
	public void queryFailureCiInfoPage(String ruleTypeName, String ruleName) throws InterruptedException{
		//Thread.sleep(50000);
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRule = result.getJSONObject("data").getJSONObject("ciQualityRule");
		String tagId = String.valueOf(ciQualityRule.get("tagId"));
		JSONArray ciQualityRuleAttrs = result.getJSONObject("data").getJSONArray("ciQualityRuleAttrs");
		BigDecimal classId  =new BigDecimal(0);
		BigDecimal ruleId  =new BigDecimal(0);
		QueryFailureCiInfoPage queryFailureCiInfoPage = new QueryFailureCiInfoPage();
		for (int i=0; i<ciQualityRuleAttrs.length(); i++){
			JSONObject rulesObj = ciQualityRuleAttrs.getJSONObject(i);
			classId = rulesObj.getBigDecimal("classId");
			ruleId = rulesObj.getBigDecimal("ruleId");
			JSONObject queryResult = queryFailureCiInfoPage.queryFailureCiInfoPage(ruleId, classId, 1, 1000);
			queryFailureCiInfoPageArr.add(queryResult);
		}
	}


	@Then("^成功预览类型为\"(.*)\",名称为\"(.*)\"的仪表盘规则不合规的CI数据$")
	public void checkQueryFailureCiInfoPage(String ruleTypeName, String ruleName){
		/*QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRule = result.getJSONObject("data").getJSONObject("ciQualityRule");
		String tagId = String.valueOf(ciQualityRule.get("tagId"));
		JSONObject tagRuleInfo = result.getJSONObject("data").getJSONObject("tagRuleInfo");
		BigDecimal classId  =new BigDecimal(0);
		JSONArray rules = tagRuleInfo.getJSONArray("rules");
		for (int i=0; i<rules.length(); i++){
			JSONObject rulesObj = rules.getJSONObject(i);
			JSONObject ruleObj = rulesObj.getJSONObject("rule");
			classId = ruleObj.getBigDecimal("classId");
			JSONObject queryResult = (JSONObject) queryFailureCiInfoPageArr.get(i);
			String sql = "select ID, CI_CODE, CI_DESC, CLASS_ID, SOURCE_ID, OWNER_ID,    ORG_ID, SUB_CLASS, CI_VERSION, CUSTOM_1, CUSTOM_2, CUSTOM_3,    CUSTOM_4, CUSTOM_5, CUSTOM_6, DOMAIN_ID, DATA_STATUS, CREATOR,    MODIFIER, CREATE_TIME, MODIFY_TIME     "
					+"from CC_CI     where       DOMAIN_ID = "+QaUtil.domain_id
					+ " and    DATA_STATUS = 1 and id in (select ID  FROM CC_CI where DATA_STATUS = 1 AND DOMAIN_ID = "+ QaUtil.domain_id+" AND CLASS_ID = "+classId+") order by ID ";

			List l = JdbcUtil.executeQuery(sql);
			assertEquals(l.size(), queryResult.getJSONObject("data").getInt("totalRows"));
			JSONArray records =  queryResult.getJSONObject("data").getJSONObject("data").getJSONArray("records");
			assertEquals(l.size(), records.length());
			for (int j =0; j<records.length(); j++){
				JSONObject record = records.getJSONObject(j).getJSONObject("ci");
				Map tempMap = (Map) l.get(j);
				assertEquals(record.getBigDecimal("ownerId"),tempMap.get("OWNER_ID"));
				assertEquals(record.getBigDecimal("id"),tempMap.get("ID"));
				assertEquals(record.getBigDecimal("sourceId"),tempMap.get("SOURCE_ID"));
				assertEquals(record.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
				assertEquals(record.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
				assertEquals(record.getBigDecimal("classId"),tempMap.get("CLASS_ID"));
				assertEquals(record.getString("ciCode"),tempMap.get("CI_CODE"));
				assertEquals(record.getString("creator"),tempMap.get("CREATOR"));
				assertEquals(record.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
				assertEquals(record.getString("modifier"),tempMap.get("MODIFIER"));
				assertEquals(record.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
			}
		}*/
	}

}
