package com.uinnova.test.step_definitions.testcase.base.kpi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.kpi.tpl.QueryKpiInfoById;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.QueryKpiTplInfoPage;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.RemoveInfoById;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.SaveOrUpdateInfo;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.base.TplUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-09 编写人:sunsl 功能介绍:指标模板测试用例类
 */
public class Scenario_tpl {
	JSONObject searchResult;


	private static List<String> kpiTplList = new  ArrayList<String>();

	@After("@delKpiTpl")
	public void cleanCicls(){
		if (!kpiTplList.isEmpty()){
			for (int i=0; i<kpiTplList.size(); i++){
				String tplName = kpiTplList.get(i);
				TplUtil tplUtil = new TplUtil();
				RemoveInfoById rib = new RemoveInfoById();
				JSONObject result = rib.removeInfoById(tplUtil.getKpiTplId(tplName));
				assertTrue(result.getBoolean("success"));
				kpiTplList.remove(tplName);
				i--;
			}
		}
	}


	/*========Scenario Outline:Tpl_新建指标模板、更改指标模板、删除指标模板==========*/
	@When("^创建模板名称为\"(.*)\"，别名为\"(.*)\"，模板描述为\"(.*)\"，指标应用类型为\"(.*)\"，指标模型名称为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"的指标模板$")
	public void createKpiTpl(String tplName, String tplAlias, String tplDesc, String kpiUseType,String kpiName, String classGroups, String tagGroups) {
		SaveOrUpdateInfo su = new SaveOrUpdateInfo();
		JSONObject result = su.saveOrUpdateInfo(tplName, tplAlias,tplDesc,kpiUseType,kpiName, classGroups, tagGroups);
		assertTrue(result.getBoolean("success"));
		kpiTplList.add(tplName);
	}

	@Then("^系统中存在模板名称为\"(.*)\"，别名为\"(.*)\"，模板描述为\"(.*)\"，指标应用类型为\"(.*)\"，指标模型名称为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"的指标模板$")
	public void checkCreateTpl(String tplName, String tplAlias, String tplDesc, String kpiUseType,String kpiName, String classGroups, String tagGroups) {
		checkTpl(tplName, tplAlias, tplDesc, kpiUseType, kpiName, classGroups, tagGroups);
	}

	@When("^模板名称为\"(.*)\"的指标模板修改模板名称为\"(.*)\"，别名为\"(.*)\"，模板描述为\"(.*)\"，指标应用类型为\"(.*)\"，指标模型名称为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"的指标模板$")
	public void updateTpl(String tplName,String updateTplName, String updateTplAlias, String updateTplDesc, String updateKpiUseType,String kpiName, String updateClassGroups, String updateTagGroups) {
		SaveOrUpdateInfo su = new SaveOrUpdateInfo();
		JSONObject result = su.saveOrUpdateInfo(tplName, updateTplName, updateTplAlias, updateTplDesc, updateKpiUseType,kpiName, updateClassGroups, updateTagGroups);
		assertTrue(result.getBoolean("success"));
		kpiTplList.remove(tplName);
		kpiTplList.add(updateTplName);
	}

	@Then("^模板名称为\"(.*)\"，别名为\"(.*)\"，模板描述为\"(.*)\"，指标应用类型为\"(.*)\"，指标模型名称为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"的指标模板修改成功$")
	public void checkUpdateTpl(String updateTplName, String updateTplAlias, String updateTplDesc,
			String updateKpiUseType,String kpiName, String updateClassGroups, String updateTagGroups) {
		checkTpl(updateTplName, updateTplAlias, updateTplDesc, updateKpiUseType, kpiName, updateClassGroups, updateTagGroups);
	}

	/**
	 * 校验kpi
	 * @param kpiCode
	 * @param classGroups
	 * @param tagGroups
	 * @param rltGroups
	 */
	private JSONObject  checkTpl(String tplName, String tplAlias, String tplDesc, String kpiUseType, String  kpiName, String classGroups, String tagGroups){
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		TplUtil tplUtil = new TplUtil();
		String kpiTplId = tplUtil.getKpiTplId(tplName);
		JSONObject result = qkib.queryKpiInfoById(kpiTplId);
		JSONObject kpiTpl = new JSONObject();
		kpiTpl = result.getJSONObject("data").getJSONObject("kpiTpl");
		assertEquals(tplName,kpiTpl.getString("tplName"));
		if(kpiTpl.has("tplAlias"))
			assertEquals(tplAlias,kpiTpl.getString("tplAlias"));
		if(kpiTpl.has("tplDesc"))
			assertEquals(tplDesc,kpiTpl.getString("tplDesc"));
		JSONArray tplItems = new JSONArray();
		if (result.getJSONObject("data").has("tplItems")){
			tplItems = result.getJSONObject("data").getJSONArray("tplItems");
		}
		if(tplItems!=null && tplItems.length()>0){
			Map<String, JSONObject> map = new HashMap<String, JSONObject>();
			for (int k=0; k<tplItems.length(); k++){
				JSONObject obj = tplItems.getJSONObject(k);
				map.put( obj.get("rltObjType")+"_"+String.valueOf(obj.getBigDecimal("rltObjId")), obj);
			}

			if (!kpiName.isEmpty()){
				String kpiId = (new KpiUtil()).getKpiIdByKpiCode(kpiName);
				JSONObject kpiObj= map.get("1_"+kpiId);
				assertEquals(kpiName, kpiObj.get("rltObjName"));
				assertEquals(1, kpiObj.get("rltObjType"));
				assertEquals(kpiUseType, String.valueOf(kpiObj.get("kpiUseType")));
			}

			if (!classGroups.isEmpty()){
				String[] classArr = classGroups.split("、");
				for (int i=0; i<classArr.length; i++){
					String clsName = classArr[i];
					JSONObject kpiCiGroup = new JSONObject();
					BigDecimal objGroupId = new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1));
					if (map.containsKey(objGroupId)){
						kpiCiGroup = map.get("2_"+objGroupId);
						assertEquals(objGroupId, new BigDecimal(kpiCiGroup.get("objGroupId").toString()));
						assertEquals(2, kpiCiGroup.get("objGroupType"));

					}
				}
			}
			if (!tagGroups.isEmpty()){
				String[] tagArr = tagGroups.split("、");
				for (int i=0; i<tagArr.length; i++){
					String tagName = tagArr[i];
					JSONObject kpiCiGroup = new JSONObject();
					BigDecimal objGroupId = new TagRuleUtil().getTagId(tagName);
					if (map.containsKey(objGroupId)){
						kpiCiGroup = map.get("3_"+objGroupId);
						assertEquals(objGroupId, new BigDecimal(kpiCiGroup.get("objGroupId").toString()));
						assertEquals(1, kpiCiGroup.get("objGroupType"));
					}
				}
			}

		}
		return result;
	}

	@When("^删除模板名称为\"(.*)\"的指标模板$")
	public void deleteTpl(String tplName) {
		TplUtil tplUtil = new TplUtil();
		RemoveInfoById rib = new RemoveInfoById();
		JSONObject result = rib.removeInfoById(tplUtil.getKpiTplId(tplName));
		assertTrue(result.getBoolean("success"));
		kpiTplList.remove(tplName);
	}

	@Then("^系统中不存在模板名称为\"(.*)\"的指标模板$")
	public void checkDeleteTpl(String updateTplName) {
		String sql = "SELECT ID FROM cc_kpi_tpl WHERE TPL_NAME = '" + updateTplName + "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertTrue(list.size() == 0);
	}


	/* ========Scenario Outline:Tpl_指标搜索=============== */
	@And("^搜索名称包含\"(.*)\"的指标模板$")
	public void searchTpl(String searchKey) throws InterruptedException {
		//Thread.sleep(5000);
		QueryKpiTplInfoPage qkip = new QueryKpiTplInfoPage();
		searchResult = qkip.queryKpiTplInfoPage(searchKey);
		assertTrue(searchResult.getJSONObject("data").getInt("totalRows") > 0);
	}

	@Then("^包含\"(.*)\"关键字的指标模板全部搜索出来$")
	public void checkSearchTpl(String searchKey) {
		JSONObject data = searchResult.getJSONObject("data");
		JSONArray dataArray = data.getJSONArray("data");
		String tplSql = "SELECT ID,TPL_NAME,TPL_ALIAS,TPL_DESC FROM cc_kpi_tpl WHERE SEARCH_FIELD like '%" + searchKey.trim().toUpperCase()
				+ "%' AND DATA_STATUS = 1  and DOMAIN_ID = "+ QaUtil.domain_id+" ORDER BY MODIFY_TIME DESC";
		ArrayList tplList = JdbcUtil.executeQuery(tplSql);
		assertEquals(dataArray.length(),tplList.size());
		for (int i = 0; i < tplList.size(); i++) {
			HashMap tplHashMap = (HashMap) tplList.get(i);
			String tplId = tplHashMap.get("ID").toString();
			JSONObject dataObject = (JSONObject) dataArray.get(i);
			JSONObject kpiTpl = dataObject.getJSONObject("kpiTpl");
			JSONArray tplItems = dataObject.getJSONArray("tplItems");
			//比较指标模板信息
			if (tplHashMap.get("TPL_NAME").equals(kpiTpl.getString("tplName"))
					&& tplHashMap.get("TPL_ALIAS").equals(kpiTpl.getString("tplAlias"))
					&& (tplHashMap.get("TPL_DESC")).equals(kpiTpl.getString("tplDesc"))) {

				String tplItemSql = "SELECT ID,TPL_ID,RLT_OBJ_ID,RLT_OBJ_TYPE,RLT_OBJ_NAME FROM cc_kpi_tpl_item WHERE TPL_ID = '"
						+ tplId + "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id+" ORDER BY ID,RLT_OBJ_TYPE";
				ArrayList tplItemList = JdbcUtil.executeQuery(tplItemSql);
				assertEquals(tplItems.length() , tplItemList.size());
				for (int j = 0; j < tplItemList.size(); j++) {
					HashMap tplItemHashMap = (HashMap) tplItemList.get(j);
					JSONObject tplItemObj = (JSONObject) tplItems.get(j);
					assertEquals(String.valueOf(tplItemHashMap.get("RLT_OBJ_ID")), String.valueOf(tplItemObj.getBigDecimal("rltObjId")));
					assertEquals(tplItemHashMap.get("RLT_OBJ_NAME"), tplItemObj.getString("rltObjName"));
					assertEquals(String.valueOf(tplItemHashMap.get("RLT_OBJ_TYPE")), String.valueOf(tplItemObj.getInt("rltObjType")));
				}
				break;
			}
		}
	}

}
