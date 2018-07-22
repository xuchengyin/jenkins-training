package com.uinnova.test.step_definitions.testcase.pmv.StandardTag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetKpiTree;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.QueryPcTagManageRlts;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.SaveOrUpdatePcTagManageRlts;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryMetricsTags;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryTagValueCombination;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.Delete;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.pmv.StandardTagUtil;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author kyn PMV 指标标签管理
 */
public class Scenario_StandardTag {

	private JSONObject queryStandardTagResult = new JSONObject();// 用于比较查询结果
	private List<String> StandardTagList = new ArrayList<String>();

	@After("@delStandardTag")
	public void delStandardTag() {
		if (!StandardTagList.isEmpty()) {
			for (int i = 0; i < StandardTagList.size(); i++) {
				String stag = StandardTagList.get(i);
				Delete delete = new Delete();
				delete.delete(StandardTagUtil.getTagIDByName(stag));
				StandardTagList.remove(stag);
				i--;
			}
		}
	}
	@When("^搜索名称包含关键字\"(.*)\"的指标$")
	public void searchKpi(String keyName) {
		GetKpiTree getKpiTree = new GetKpiTree();
		JSONObject result = getKpiTree.getKpiTree(keyName);
		queryStandardTagResult = result.getJSONObject("data");

	}

	@Then("^包含\"(.*)\"关键字的指标全部搜索出来$")
	public void checksearchKpi(String keyName) {
		String KpiClass = new String();
		String sql = "select KPI,KPI_CLASS from pc_kpi where KPI like '%" + keyName + "%'";
		List resultList = JdbcUtil.executeQuery(sql);
		int classCount = 0;
		Iterator iterator = queryStandardTagResult.keys();
		while (iterator.hasNext()) {
			String Key = (String) iterator.next();
			classCount += queryStandardTagResult.getJSONArray(Key).length();
		}
		assertEquals("按照关键字进行KPI搜索， 关键字为：" + keyName, classCount, resultList.size());
	}

	@When("^新建名称为\"(.*)\"的标准标签$")
	public void createStandardTag(String stag) {
		SaveOrUpdate su = new SaveOrUpdate();
		su.saveOrUpdate(stag);
		StandardTagList.add(stag);
	}
	
	@Then("^成功新建名称为\"(.*)\"的标准标签$")
	public void checkCreateStandardTag(String stag) {
		String sql = "  select ID  from pc_tag_standard   where   NAME = '" + stag + "' and  DATA_STATUS = 1   ";
		List dbList = JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条激活的记录", 1, dbList.size());
	}

	@And("^再次新建名称为\"(.*)\"的标准标签失败,kw=\"(.*)\"$")
	public void againCreateStandardTag(String stag, String kw) {
		SaveOrUpdate su = new SaveOrUpdate();

		JSONObject result = su.saveOrUpdateAgain(stag, kw);
		assertEquals(null, result);
	}

	@When("^删除名称为\"(.*)\"的标准标签$")
	public void removeSTagById(String stag) {
		BigDecimal ID = StandardTagUtil.getTagIDByName(stag);
		Delete delete = new Delete();
		delete.delete(ID);
		StandardTagList.remove(stag);
	}

	@Then("^成功删除名称为\"(.*)\"的标准标签$")
	public void checkRemoveRuleById(String stag) {
		String Sql = "  select ID  from pc_tag_standard   where   NAME = '" + stag.trim() + "' "
				+ "and   DATA_STATUS = 1 ";
		List stagList = JdbcUtil.executeQuery(Sql);
		assertEquals("数据库中不存在该标准标签", 0, stagList.size());
	}

	@When("^对指标类为\"(.*)\"，指标为\"(.*)\"的属性\"(.*)\"设置标签，标准标签为空$")

	public void SetTag(String kpiclass, String kpi, String attr) {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		BigDecimal kpiID = StandardTagUtil.getkpiIDByName(kpiclass, kpi);
		JSONObject param = new JSONObject();
		JSONObject tmRlt = new JSONObject();
		JSONArray tmRlts = new JSONArray();
		tmRlt.put("objId", kpiID);
		tmRlt.put("originalTag", attr);
		tmRlt.put("originalTagId", attr);
		tmRlt.put("ownerId", "");
		tmRlt.put("objCode", "");
		tmRlt.put("checked", 0);
		tmRlts.put(tmRlt);
		param.put("tmRlt", tmRlts);
		param.put("tagType", 2);
		param.put("parentName", kpiclass);
		param.put("objectName", kpi);
		param.put("objectId", kpiID);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
	}

	@Then("^指标类为\"(.*)\"，指标为\"(.*)\"的属性\"(.*)\"的标签设置成功$")
	public void checkSetTag(String kpiclass, String kpi, String attr) throws InterruptedException {
		Thread.sleep(10000);
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		JSONObject param = new JSONObject();
		param.put("objParentName", kpiclass);
		param.put("objId", "");
		param.put("objName", kpi);
		param.put("tagType", 2);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		for (int i = 0; i < result.length(); i++) {
			JSONObject job = result.getJSONObject(0);
			assertEquals(job.get("originalTag"), attr);
		}
	}

	@Then("^验证存在指标为\"(.*)\"属性为\"(.*)\"的指标标签$")
	public void checkTagValue(String kpi, String attr) {
		QueryTagValueCombination queryTvalue = new QueryTagValueCombination();
		JSONArray result = queryTvalue.queryTagValueCombination(kpi);
		String job = result.toString();
		assertTrue(job.contains(attr));
	}

	@And("^指标为\"(.*)\"属性为\"(.*)\"的指标标签已存在$")
	public void checkkpiTag(String kpi, String attr) throws InterruptedException {
		Thread.sleep(10000);
		QueryMetricsTags queryTag = new QueryMetricsTags();
		JSONArray result = queryTag.queryMetricsTags(kpi);
		String job = result.toString();
		assertTrue(job.contains(attr));		
	}

	@When("^更新设置指标类为\"(.*)\"，指标为\"(.*)\"的属性\"(.*)\"，标准标签为\"(.*)\"的标签$")
	public void SetTagbySTag(String kpiclass, String kpi, String attr, String stag) throws InterruptedException {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		BigDecimal kpiID = StandardTagUtil.getkpiIDByName(kpiclass,kpi);
		BigDecimal stagID = StandardTagUtil.getTagIDByName(stag);
		JSONObject param = new JSONObject();
		JSONObject tmRlt = new JSONObject();
		JSONArray tmRlts = new JSONArray();
		tmRlt.put("objId", kpiID);
		tmRlt.put("originalTag", attr);
		tmRlt.put("originalTagId", attr);
		tmRlt.put("standardTag", stag);
		tmRlt.put("standardTagId", stagID);
		tmRlt.put("ownerId", "");
		tmRlt.put("objCode", "");
		tmRlt.put("checked", 0);
		tmRlts.put(tmRlt);
		param.put("tmRlt", tmRlts);
		param.put("tagType", 2);
		param.put("parentName", kpiclass);
		param.put("objectName", kpi);
		param.put("objectId", kpiID);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
		Thread.sleep(10000);

	}

	@When("^清除指标类为\"(.*)\"，指标为\"(.*)\"的指标标签$")
	public void ClearTagbySTag(String kpiclass, String kpi) {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		BigDecimal kpiID = StandardTagUtil.getkpiIDByName(kpiclass,kpi);
		JSONObject param = new JSONObject();
		JSONArray tmRlt = new JSONArray();
		param.put("tmRlt", tmRlt);
		param.put("tagType", 2);
		param.put("parentName", kpiclass);
		param.put("objectName", kpi);
		param.put("objectId", kpiID);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
	}

	@Then("^指标类为\"(.*)\"，指标为\"(.*)\"的标签清除成功$")
	public void checkClearTagbySTag(String kpiclass, String kpi) {
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		JSONObject param = new JSONObject();
		param.put("objParentName", kpiclass);
		param.put("objId", "");
		param.put("objName", kpi);
		param.put("tagType", 2);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		boolean boo = false;
		if (result.length() > 0) {
			boo = false;
		} else {
			boo = true;
		}
		assertTrue(boo);
	}
	@When("^清除指标类为\"(.*)\"，指标为\"(.*)\"的指标数据$")
	public void ClearKpiData(String kpiclass, String kpi) {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		BigDecimal kpiID = StandardTagUtil.getkpiIDByName(kpiclass,kpi);
		JSONObject param = new JSONObject();
		JSONArray tmRlt = new JSONArray();
		param.put("tmRlt", tmRlt);
		param.put("tagType", 2);
		param.put("parentName", kpiclass);
		param.put("objectName", kpi);
		param.put("objectId", kpiID);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
	}

	@Then("^指标类为\"(.*)\"，指标为\"(.*)\"的数据清除成功$")
	public void checkClearKpiData(String kpiclass, String kpi) {
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		JSONObject param = new JSONObject();
		param.put("objParentName", kpiclass);
		param.put("objId", "");
		param.put("objName", kpi);
		param.put("tagType", 2);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		boolean boo = false;
		if (result.length() > 0) {
			boo = false;
		} else {
			boo = true;
		}
		assertTrue(boo);
	}

}
