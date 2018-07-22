package com.uinnova.test.step_definitions.testcase.pmv.TagManagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryTagValues;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetCILable2;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetClassTree;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetConfigCIClassNames;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.QueryPcTagManageRlts;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.SaveOrUpdatePcTagManageRlts;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.pmv.StandardTagUtil;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author kyn PMV 标签管理
 */
public class Scenario_TagManagement {

	private JSONArray searchCiClsDirResult = new JSONArray();// 用于比较查询结果
	private List<String> TagManagementList = new ArrayList<String>();

	@When("^搜索名称包含关键字\"(.*)\"的CI分类$")
	public void searchCiClass(String keyName) {
		GetClassTree getClassTree = new GetClassTree();
		JSONObject result = getClassTree.getClassTree(keyName);
		searchCiClsDirResult = result.getJSONArray("data");

	}

	@Then("^包含\"(.*)\"关键字的CI分类全部搜索出来$")
	public void checksearchClassName(String keyName) {
		String sql = "select ID, CLASS_CODE, CLASS_NAME, CLASS_STD_CODE, DIR_ID, CI_TYPE,    CLASS_KIND, PARENT_ID, CLASS_LVL, CLASS_PATH, ORDER_NO, IS_LEAF,    ICON, CLASS_DESC, CLASS_COLOR, DISP_FIELD_IDS, DISP_FIELD_NAMES, COST_TYPE,    LINE_TYPE, LINE_BORDER, LINE_COLOR, LINE_DIRECT, LINE_ANIME, LINE_DISP_TYPE,    LINE_LABEL_ALIGN, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME,    MODIFY_TIME     from CC_CI_CLASS     where   CLASS_NAME like '%"
				+ keyName.trim().toUpperCase() + "%' and    CI_TYPE = 1 and DOMAIN_ID = " + QaUtil.domain_id
				+ " and DATA_STATUS = 1 order by  CLASS_NAME  ";
		List resultList = JdbcUtil.executeQuery(sql);
		int classCount = 0;

		for (int i = 0; i < searchCiClsDirResult.length(); i++) {
			JSONObject temp = (JSONObject) searchCiClsDirResult.get(i);
			if (temp.has("cls"))
				classCount += temp.getJSONArray("cls").length();
		}
		assertEquals("按照关键字进行ci分类搜索， 关键字为：" + keyName, classCount, resultList.size());

	}

	@When("^对CI分类\"(.*)\"的属性设置标签，标准标签为空$")
	public void SetTagbyCiattr(String className, DataTable table) throws InterruptedException {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		JSONObject param = new JSONObject();
		JSONArray tmRlts = new JSONArray();
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			BigDecimal oTagId = cu.getAttrIdByAttrName(className, row.get(0));
			JSONObject tmRlt = new JSONObject();
			tmRlt.put("objId", clsId);
			tmRlt.put("originalTag", row.get(0));
			tmRlt.put("originalTagId", oTagId);
			tmRlt.put("ownerId", "");
			tmRlt.put("objCode", "");
			tmRlt.put("checked", row.get(1));
			tmRlts.put(tmRlt);
		}
		param.put("tmRlt", tmRlts);
		param.put("tagType", 1);
		param.put("objectName", className);
		param.put("objectId", clsId);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
		Thread.sleep(10000);
	}

	@Then("^CI分类\"(.*)\"的属性标签设置成功$")
	public void checkSetTag(String className, DataTable table) {
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		JSONObject param = new JSONObject();
		param.put("objId", clsId);
		param.put("tagType", 1);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		String job = result.toString();
		for (int k = 1; k < table.raw().size(); k++) {
			List<String> row = table.raw().get(k);
			assertTrue(job.contains(row.get(0)));
		}
	}

	@Then("^显示对象分类\"(.*)\"$")
	public void checkSetshow(String className) throws InterruptedException {
		Thread.sleep(10000);
		GetConfigCIClassNames getCiName = new GetConfigCIClassNames();
		JSONArray result = getCiName.getConfigCIClassNames();
		String job = result.toString();
		assertTrue(job.contains(className));
	}

	@And("^存在对象分类\"(.*)\"的属性\"(.*)\"$")
	public void checkSetshowAttr(String className, String attr) {
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		GetCILable2 getCILable = new GetCILable2();
		JSONArray result = getCILable.getCILable2(clsId);
		String job = result.toString();
		assertTrue(job.contains(className));
	}

	@And("^验证存在对象分类属性\"(.*)\"的标签值$")
	public void checkSetshowAttrValue(String attr) throws InterruptedException {
		Thread.sleep(10000);
		QueryTagValues queryTagValues = new QueryTagValues();
		JSONArray result = queryTagValues.queryTagValues(attr);
		assertTrue(result.length() > 0);
	}

	@When("^更新设置CI分类\"(.*)\"的属性\"(.*)\"标准标签为\"(.*)\"的标签，设置显示$")
	public void SetTagbySTag(String className, String attr, String stag) throws InterruptedException {
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		BigDecimal stagID = StandardTagUtil.getTagIDByName(stag);
		BigDecimal oTagId = cu.getAttrIdByAttrName(className, attr);
		JSONObject param = new JSONObject();
		JSONObject tmRlt = new JSONObject();
		JSONArray tmRlts = new JSONArray();
		tmRlt.put("objId", clsId);
		tmRlt.put("originalTag", attr);
		tmRlt.put("originalTagId", oTagId);
		tmRlt.put("standardTag", stag);
		tmRlt.put("standardTagId", stagID);
		tmRlt.put("ownerId", "");
		tmRlt.put("objCode", "");
		tmRlt.put("checked", 1);
		tmRlts.put(tmRlt);
		param.put("tmRlt", tmRlts);
		param.put("tagType", 1);
		param.put("objectName", className);
		param.put("objectId", clsId);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
		Thread.sleep(10000);

	}

	@Then("^存在CI分类\"(.*)\"的属性\"(.*)\"标准标签为\"(.*)\"的标签$")
	public void checkSetSTag(String className, String attr, String stag) {
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		JSONObject param = new JSONObject();
		param.put("objId", clsId);
		param.put("tagType", 1);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		for (int i = 0; i < result.length(); i++) {
			JSONObject job = result.getJSONObject(0);
			assertEquals(job.get("originalTag"), attr);
			assertEquals(job.get("standardTag"), stag);
		}
	}

	@When("^清除CI分类为\"(.*)\"标签$")
	public void ClearTagbySTag(String className) {
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		SaveOrUpdatePcTagManageRlts suTag = new SaveOrUpdatePcTagManageRlts();
		JSONObject param = new JSONObject();
		JSONArray tmRlt = new JSONArray();
		param.put("tmRlt", tmRlt);
		param.put("tagType", 1);
		param.put("objectName", className);
		param.put("objectId", clsId);
		suTag.saveOrUpdatePcTagManageRlts(param.toString());
	}

	@Then("^CI分类为\"(.*)\"的标签清除成功$")
	public void checkClearTagbySTag(String className) {
		QueryPcTagManageRlts querypctag = new QueryPcTagManageRlts();
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		JSONObject param = new JSONObject();
		param.put("objId", clsId);
		param.put("tagType", 1);
		JSONArray result = querypctag.queryPcTagManageRlts(param.toString());
		assertTrue(result.length() == 0);
	}

	@And("^验证CI分类\"(.*)\"的标签排序\"(.*)\"在\"(.*)\"之前$")
	public void checkLable(String className, String tag1, String tag2) {
		CiClassUtil cu = new CiClassUtil();
		String clsId = String.valueOf(cu.getCiClassId(className));
		StandardTagUtil getOrderNum = new StandardTagUtil();
		BigDecimal OrderNum1 = getOrderNum.getOrderNum(clsId, tag1);
		BigDecimal OrderNum2 = getOrderNum.getOrderNum(clsId, tag2);
		assertTrue(OrderNum2.compareTo(OrderNum1) == 1);
	}

}
