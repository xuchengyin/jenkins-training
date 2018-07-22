package com.uinnova.test.step_definitions.testcase.base.kpi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.base.kpi.QueryKpiInfoById;
import com.uinnova.test.step_definitions.api.base.kpi.QueryKpiInfoPage;
import com.uinnova.test.step_definitions.api.base.kpi.RemoveKpiById;
import com.uinnova.test.step_definitions.api.base.kpi.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.cmv.kpi.ExportKpi;
import com.uinnova.test.step_definitions.api.cmv.kpi.ExportKpiTpl;
import com.uinnova.test.step_definitions.api.cmv.kpi.ImportKpi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-02 编写人:sunsl 功能介绍:指标模型测试用例类
 */
public class Scenario_kpi {
	JSONObject searchResult;
	private List<String> kpiList = new ArrayList<String>(); //用于记录一共新建了多少视图， 用户After方法清理数据
	private String filePath;

	@After("@delKpi")
	public void delKpi(){
		if (!kpiList.isEmpty()){
			for (int i=0; i<kpiList.size(); i++){
				String kpiCode = kpiList.get(i);
				RemoveKpiById rkb = new RemoveKpiById();
				JSONObject result = rkb.removeKpiById(kpiCode);
				assertTrue(result.getBoolean("success"));
				kpiList.remove(kpiCode);
				i--;
			}
		}
	}

	@When("创建名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"的指标")
	public void createKpiNoCiGroup(String kpiCode, String kpiName, String kpiDesc, String unitName) {	
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		// 指标名称在文件里
		if (kpiCode.indexOf(".") > 0) {
			String filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiCode;
			kpiCode = (new TxtUtil()).readTxt(filePath);
		}
		// 指标别名在文件里
		if (kpiName.indexOf(".") > 0) {
			String filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiName;
			kpiName = (new TxtUtil()).readTxt(filePath);
		} 		
		JSONObject result = saveOrUpdate.saveOrUpdate(kpiCode,kpiName,kpiDesc,unitName,"","","",null);
		assertTrue(result.getBoolean("success"));
		kpiList.add(kpiCode);
	}

	@Then("系统中存在名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"的指标")
	public void checkCreateKpiNoCiGroup(String kpiCode, String kpiName, String kpiDesc, String unitName) {
		String filePath = "";

		if (kpiCode.indexOf(".") > 0) {
			filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiCode;
			kpiCode = (new TxtUtil()).readTxt(filePath);
		}

		if (kpiName.indexOf(".") > 0) {
			filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiName;
			kpiName = (new TxtUtil()).readTxt(filePath);
		}
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject result = qkib.queryKpiInfoById(kpiCode);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");
		if (kpi.has("kpiName"))//适配oracle
			assertEquals(kpiName, kpi.get("kpiName"));
		if( kpi.has("kpiDesc"))
			assertEquals(kpiDesc, kpi.get("kpiDesc"));
		if (kpi.has("unitName"))
			assertEquals(unitName, kpi.get("unitName"));
		assertFalse(data.has("kpiCiGroups"));
	}

	@When("^将指标名称\"(.*)\"的指标修改指标的名称为\"(.*)\"，指标别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"的指标$")
	public void updateKpi(String kpiCode,String updateKpiCode, String updateKpiName, String updateKpiDesc, String updateUnitName) {		
		String filePath = "";
		if (kpiCode.indexOf(".") > 0) {
			filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiCode;
			kpiCode = (new TxtUtil()).readTxt(filePath);
		}
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.update(kpiCode, updateKpiCode,updateKpiName,updateKpiDesc,updateUnitName,"","","",null);
		assertTrue(result.getBoolean("success"));
		kpiList.remove(kpiCode);
		kpiList.add(updateKpiCode);
	}

	@Then("指标的名称为\"(.*)\"，指标别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"的指标修改成功")
	public void checkUpdateKpi(String updateKpiCode, String updateKpiName, String updateKpiDesc,
			String updateUnitName) {
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject result = qkib.queryKpiInfoById(updateKpiCode);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");
		assertEquals(updateKpiCode, kpi.get("kpiCode"));
		if (kpi.has("kpiName"))//适配oracle
			assertEquals(updateKpiName, kpi.get("kpiName"));
		if( kpi.has("kpiDesc"))
			assertEquals(updateKpiDesc, kpi.get("kpiDesc"));
		if (kpi.has("unitName"))
			assertEquals(updateUnitName, kpi.get("unitName"));
		assertFalse(data.has("kpiCiGroups"));
	}

	@When("创建含有对象组的名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\"的指标")
	public void createKpiHasCiGroup(String kpiCode, String kpiName, String kpiDesc, String unitName, String classGroups, String tagGroups, String rltGroups) {	
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.saveOrUpdate(kpiCode,kpiName,kpiDesc,unitName,classGroups, tagGroups,rltGroups,null);
		assertTrue(result.getBoolean("success"));
		kpiList.add(kpiCode);
	}

	@Then("^系统中存在含有对象组的名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\"的指标$")
	public void CheckCreateKpiHasCiGroup(String kpiCode, String kpiName, String kpiDesc, String unitName, String classGroups, String tagGroups, String rltGroups) {	
		JSONObject result = this.checkKpi(kpiCode, classGroups, tagGroups, rltGroups);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");
		if (kpi.has("kpiName"))//适配oracle
			assertEquals(kpiName, kpi.get("kpiName"));
		if( kpi.has("kpiDesc"))
			assertEquals(kpiDesc, kpi.get("kpiDesc"));
		if (kpi.has("unitName"))
			assertEquals(unitName, kpi.get("unitName"));

	}

	@When("^创建含有指标状态的指标，名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\",指标状态如下：$")
	public void createKpiHasKpiStatus(String kpiCode, String kpiName, String kpiDesc, String unitName, String classGroups, String tagGroups, String rltGroups,DataTable table) {	
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.saveOrUpdate(kpiCode,kpiName,kpiDesc,unitName,classGroups, tagGroups,rltGroups, table);
		assertTrue(result.getBoolean("success"));
		kpiList.add(kpiCode);
	}
	@Then("^系统中存在含有如下指标，名称为\"(.*)\"，别名为\"(.*)\"，指标描述为\"(.*)\"，单位为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\"，指标状态如下：$")
	public void CheckCreateKpiHasKpiStatus(String kpiCode, String kpiName, String kpiDesc, String unitName, String classGroups, String tagGroups, String rltGroups, DataTable table) {	
		JSONObject result = this.checkKpi(kpiCode, classGroups, tagGroups, rltGroups);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");
		if (kpi.has("kpiName"))//适配oracle
			assertEquals(kpiName, kpi.get("kpiName"));
		if( kpi.has("kpiDesc"))
			assertEquals(kpiDesc, kpi.get("kpiDesc"));
		if (kpi.has("unitName"))
			assertEquals(unitName, kpi.get("unitName"));

		int rows = table.raw().size();
		JSONArray kpiExtPros = new JSONArray();
		ImageUtil iUtil = new ImageUtil();
		for (int i=1;i<rows;i++){
			JSONObject kpiExtProsObj = new JSONObject();
			List<String> row = table.raw().get(i);
			kpiExtProsObj.put("val", row.get(0));
			kpiExtProsObj.put("img", iUtil.getImageUrl(row.get(2)));
			kpiExtPros.put(kpiExtProsObj);
		}
		assertEquals(kpiExtPros.toString(), kpi.get("kpiExtPros"));
	}

	@When("^修改指标状态，名称为\"(.*)\",指标状态如下：$")
	public void updateKpiHasKpiStatus(String kpiCode, DataTable table) {	
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.update(kpiCode,null,null,null,null, null,null,null, table);
		assertTrue(result.getBoolean("success"));
	}

	@When("^成功修改指标状态，名称为\"(.*)\",指标状态如下：$")
	public void checkUpdateKpiHasKpiStatus(String kpiCode, DataTable table) {	
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject result = qkib.queryKpiInfoById(kpiCode);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");

		int rows = table.raw().size();
		JSONArray kpiExtPros = new JSONArray();
		ImageUtil iUtil = new ImageUtil();
		for (int i=1;i<rows;i++){
			JSONObject kpiExtProsObj = new JSONObject();
			List<String> row = table.raw().get(i);
			kpiExtProsObj.put("val", row.get(0));
			kpiExtProsObj.put("img", iUtil.getImageUrl(row.get(2)));
			kpiExtPros.put(kpiExtProsObj);
		}
		assertEquals(kpiExtPros.toString(), kpi.get("kpiExtPros"));
	}

	@When("^将指标名称\"(.*)\"的指标修改分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\"的指标$")
	public void updateKpiCiGroups(String kpiCode,String classGroups, String tagGroups, String rltGroups) {		
		String filePath = "";
		if (kpiCode.indexOf(".") > 0) {
			filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiCode;
			kpiCode = (new TxtUtil()).readTxt(filePath);
		}
		SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.update(kpiCode, kpiCode, null,null,null,classGroups, tagGroups, rltGroups,null);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^指标的名称为\"(.*)\"，分类对象组为\"(.*)\"，标签对象组为\"(.*)\"，关系对象组为\"(.*)\"的指标修改成功$")
	public void checkUpdateKpiCiGroups(String kpiCode,String classGroups, String tagGroups, String rltGroups) {		
		this.checkKpi(kpiCode, classGroups, tagGroups, rltGroups);
	}

	/**
	 * 校验kpi
	 * @param kpiCode
	 * @param classGroups
	 * @param tagGroups
	 * @param rltGroups
	 */
	private JSONObject  checkKpi(String kpiCode,String classGroups, String tagGroups, String rltGroups){
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject result = qkib.queryKpiInfoById(kpiCode);
		JSONObject data = result.getJSONObject("data");
		JSONObject kpi = data.getJSONObject("kpi");
		if(data.has("kpiCiGroups")){
			JSONArray kpiCiGroups = data.getJSONArray("kpiCiGroups");
			Map<String, JSONObject> map = new HashMap<String, JSONObject>();
			for (int k=0; k<kpiCiGroups.length(); k++){
				JSONObject obj = kpiCiGroups.getJSONObject(k);
				map.put( obj.get("objGroupType")+"_"+String.valueOf(obj.getBigDecimal("objGroupId")), obj);
			}

			if (!classGroups.isEmpty()){

				String[] classArr = classGroups.split("、");
				for (int i=0; i<classArr.length; i++){
					String clsName = classArr[i];
					JSONObject kpiCiGroup = new JSONObject();
					BigDecimal objGroupId = new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1));
					if (map.containsKey(objGroupId)){
						kpiCiGroup = map.get(Contants.KPICIGROUPS_GROUPTYPE_CLASSGROUPS+"_"+objGroupId);
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
						kpiCiGroup = map.get(Contants.KPICIGROUPS_GROUPTYPE_TAGGROUPS+"_"+objGroupId);
						assertEquals(objGroupId, new BigDecimal(kpiCiGroup.get("objGroupId").toString()));
						assertEquals(1, kpiCiGroup.get("objGroupType"));
					}
				}
			}
			if (!rltGroups.isEmpty()){
				String[] rltArr = rltGroups.split("、");
				for (int i=0; i<rltArr.length; i++){
					String rltName = rltArr[i];
					JSONObject kpiCiGroup = new JSONObject();
					BigDecimal objGroupId = new RltClassUtil().getRltClassId(rltName);
					if (map.containsKey(objGroupId)){
						kpiCiGroup = map.get(Contants.KPICIGROUPS_GROUPTYPE_RLTGROUPS+"_"+objGroupId);
						assertEquals(objGroupId, new BigDecimal(kpiCiGroup.get("objGroupId").toString()));
						assertEquals(3, kpiCiGroup.get("objGroupType"));
					}
				}
			}
		}
		return result;
	}


	@When("删除名称为\"(.*)\"的指标")
	public void deleteKpi(String kpiCode) {
		if (kpiCode.indexOf(".") > 0) {
			String filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + kpiCode;
			kpiCode = (new TxtUtil()).readTxt(filePath);
		}
		RemoveKpiById rkb = new RemoveKpiById();
		JSONObject result = rkb.removeKpiById(kpiCode);
		assertTrue(result.getBoolean("success"));
		kpiList.remove(kpiCode);
	}

	@Then("系统中不存在名称为\"(.*)\"的指标")
	public void checkDeleteKpi(String updateKpiCode) {
		KpiUtil kpiUtil = new KpiUtil();
		ArrayList kpiList = kpiUtil.getKpi(updateKpiCode);
		assertTrue(kpiList.size() == 0);
	}

	@And("搜索名称包含(.*)的指标模型")
	public void searchKpi(String searchKey) {
		if (searchKey.indexOf(".") > 0) {
			String filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + searchKey;
			searchKey = (new TxtUtil()).readTxt(filePath);
		}
		QueryKpiInfoPage qk = new QueryKpiInfoPage();
		searchResult = qk.queryKpiInfoPage(searchKey);
		assertTrue(searchResult.getBoolean("success"));
	}

	@Then("包含(.*)关键字的的指标模型全部搜索出来")
	public void checkSearchKpi(String searchKey) {
		if (searchKey.indexOf(".") > 0) {
			String filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + searchKey;
			searchKey = (new TxtUtil()).readTxt(filePath);
		}
		// sql检索中的值
		String kpiSql = "SELECT ID,KPI_CODE,KPI_NAME FROM cc_kpi WHERE SEARCH_VALUE like '" + searchKey
				+ "' and DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id+" order by CREATE_TIME ASC";
		ArrayList kpiList = JdbcUtil.executeQuery(kpiSql);
		JSONObject data = searchResult.getJSONObject("data");
		JSONArray dataDb = data.getJSONArray("data");
		for (int i = 0; i < kpiList.size(); i++) {
			HashMap kpiHashMap = (HashMap) kpiList.get(i);
			String kpiId = kpiHashMap.get("ID").toString();
			String kpiCode = kpiHashMap.get("KPI_CODE").toString();
			String kpiName = kpiHashMap.get("KPI_NAME").toString();

			String kpiGroupSql = "Select KPI_ID,OBJ_GROUP_TYPE,OBJ_GROUP_ID from cc_kpi_ci_group where KPI_ID = '"
					+ kpiId + "' and DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList kpiGroupList = JdbcUtil.executeQuery(kpiGroupSql);
			String objGroupType = "";
			String objGroupId ="";
			if (kpiGroupList.size()>0){
				HashMap kpiGroupHashMap = (HashMap) kpiGroupList.get(0);
				objGroupType = kpiGroupHashMap.get("OBJ_GROUP_TYPE").toString();
				objGroupId = kpiGroupHashMap.get("OBJ_GROUP_ID").toString();
			}
			// 取得搜索結果中的值

			JSONObject dataDbObj = (JSONObject) dataDb.get(i);
			JSONObject kpi = dataDbObj.getJSONObject("kpi");
			String kpiCodeDb = kpi.getString("kpiCode");
			String kpiNameDb = kpi.getString("kpiName");
			assertEquals(kpiCode, kpiCodeDb);
			assertEquals(kpiName, kpiNameDb);
			if (kpiGroupList.size()>0){
				JSONArray kpiCiGroups = dataDbObj.getJSONArray("kpiCiGroups");
				JSONObject kpiCiGroupObj = (JSONObject) kpiCiGroups.get(0);
				Integer objGroupTypeDb = kpiCiGroupObj.getInt("objGroupType");
				BigDecimal objGroupIdDb = kpiCiGroupObj.getBigDecimal("objGroupId");
				assertEquals(objGroupType, String.valueOf(objGroupTypeDb));
				assertEquals(objGroupId, String.valueOf(objGroupIdDb));
			}

		}
	}

	/*=====================Scenario: Kpi_下载模板、导入、导出============================*/
	@When("^指标模型下载指标模板$")
	public void exportKpiTpl(){
		ExportKpiTpl exportKpiTpl = new ExportKpiTpl();
		filePath = exportKpiTpl.exportKpiTpl();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^指标模型按如下顺序成功下载指标模板:$")
	public void checkExportKpiTpl(DataTable dt){
		JSONArray arr = (new ExcelUtil()).readFromExcel(filePath, "指标模型数据");
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i ++){
			List arrList = list.get(i);
			JSONObject obj = (JSONObject)arr.get(0);
			assertEquals(arrList.get(1).toString(), obj.getString("0"));
			assertEquals(arrList.get(2).toString(), obj.getString("1"));
			assertEquals(arrList.get(3).toString(), obj.getString("2"));
			assertEquals(arrList.get(4).toString(), obj.getString("3"));
			assertEquals(arrList.get(5).toString(), obj.getString("4"));
			assertEquals(arrList.get(6).toString(), obj.getString("5"));
			assertEquals(arrList.get(7).toString(), obj.getString("6"));
		}
	}

	@When("^指标模型导入名称为\"(.*)\"指标模板$")
	public void importKpi(String fileName){
		ImportKpi importKpi = new ImportKpi();
		JSONObject result = importKpi.importKpi(fileName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^指标模型成功导入名称为\"(.*)\"指标模板$")
	public void checkImportKpi(String fileName){
		filePath = Scenario_kpi.class.getResource("/").getPath() + "testData/kpi/" + fileName;
		JSONArray excelDatas = (new ExcelUtil()).readFromExcel(filePath, "指标模型数据");
		boolean objClassFlag = false;
		boolean objTagFlag = false;
		boolean objRltFlag = false;
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		for(int i = 1; i < excelDatas.length(); i ++){
			JSONObject excelObj = (JSONObject)excelDatas.get(i);
			String kpiCode= (String) excelObj.get("0");
			JSONObject data = qkib.queryKpiInfoById(kpiCode);
			JSONObject dbObj = data.getJSONObject("data");
			JSONObject kpi = dbObj.getJSONObject("kpi");
			JSONArray kpiCiGroups = dbObj.getJSONArray("kpiCiGroups");
			assertEquals(excelObj.get("0"), kpi.get("kpiCode"));
			assertEquals(excelObj.get("1"), kpi.get("kpiName"));
			assertEquals(excelObj.get("2"), kpi.get("kpiDesc"));
			assertEquals(excelObj.get("3"), kpi.get("unitName"));

			for(int k =0; k<kpiCiGroups.length();k++){
				JSONObject kpiCiGroup = (JSONObject)kpiCiGroups.get(k);
				CiClassUtil ciClassUtil = new CiClassUtil();
				TagRuleUtil tagRuleUtil = new TagRuleUtil();					
				BigDecimal objCiGroupId = ciClassUtil.getClassIdByClassName((String)excelObj.get("4"),new BigDecimal(1));
				BigDecimal objTagGroupId = tagRuleUtil.getTagId((String)excelObj.get("5"));
				BigDecimal objRltGroupId = ciClassUtil.getClassIdByClassName((String)excelObj.get("6"),new BigDecimal(2));
				String objGroupId = (kpiCiGroup.get("objGroupId")).toString();
				if(objCiGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objClassFlag = true;
				}
				if(objTagGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objTagFlag = true;
				}
				if(objRltGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objRltFlag =true;
				}
			}
		}
		if( objClassFlag && objTagFlag && objRltFlag){
			assertTrue(true);
		}else{
			fail();
		}

	}

	@When("^指标模型导出指标模板$")
	public void exportKpi(){
		ExportKpi  exportKpi = new ExportKpi();
		filePath = exportKpi.exportKpi();
		File file = new File(filePath);
		if(file.exists()){
			assertTrue(true);
		}
	}

	@Then("^指标模型按顺序成功导出指标模板$")
	public void checkExportKpi(){
		JSONArray excelDatas = (new ExcelUtil()).readFromExcel(filePath, "指标模型数据");
		boolean objClassFlag = false;
		boolean objTagFlag = false;
		boolean objRltFlag = false;
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		for(int i = 1; i < excelDatas.length(); i ++){
			JSONObject excelObj = (JSONObject)excelDatas.get(i);
			String kpiCode= (String) excelObj.get("0");
			JSONObject data = qkib.queryKpiInfoById(kpiCode);
			JSONObject dbObj = data.getJSONObject("data");
			JSONObject kpi = dbObj.getJSONObject("kpi");
			JSONArray kpiCiGroups = new JSONArray();
			if (dbObj.has("kpiCiGroups"))
				kpiCiGroups = dbObj.getJSONArray("kpiCiGroups");

			assertEquals(kpiCode, kpi.get("kpiCode"));
			if (kpi.has("kpiName"))
				assertEquals(excelObj.get("1"), kpi.get("kpiName"));
			if (kpi.has("kpiDesc"))
				assertEquals(excelObj.get("2"), kpi.get("kpiDesc"));
			if (kpi.has("unitName"))
				assertEquals(excelObj.get("3"), kpi.get("unitName"));
			CiClassUtil ciClassUtil = new CiClassUtil();
			TagRuleUtil tagRuleUtil = new TagRuleUtil();	
			BigDecimal objCiGroupId = ciClassUtil.getClassIdByClassName((String)excelObj.get("4"),new BigDecimal(1));
			BigDecimal objTagGroupId = tagRuleUtil.getTagId((String)excelObj.get("5"));
			BigDecimal objRltGroupId = ciClassUtil.getClassIdByClassName((String)excelObj.get("6"),new BigDecimal(2));
			for(int k =0; k<kpiCiGroups.length();k++){
				JSONObject kpiCiGroup = (JSONObject)kpiCiGroups.get(k);
				String objGroupId = (kpiCiGroup.get("objGroupId")).toString();
				if(objCiGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objClassFlag = true;
				}
				if(objTagGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objTagFlag = true;
				}
				if(objRltGroupId.compareTo(new BigDecimal(objGroupId))==0){
					objRltFlag =true;
				}
			}
			if (dbObj.has("kpiCiGroups")){
				if (objCiGroupId.compareTo(new BigDecimal(0))>0)
					assertTrue(objClassFlag);
				if (objTagGroupId.compareTo(new BigDecimal(0))>0)
					assertTrue(objTagFlag);
				if (objRltGroupId.compareTo(new BigDecimal(0))>0)
					assertTrue(objRltFlag);
			}
		}
	}

	@And("^删除以下指标模板:$")
	public void removeKpiById(DataTable dt){
		List<List<String>> list = dt.raw();
		RemoveKpiById removeKpiById = new RemoveKpiById();
		for(int i = 1; i < list.size(); i ++){
			JSONObject result = removeKpiById.removeKpiById(list.get(i).get(1));
			assertTrue(result.getBoolean("success"));
		}
	}
}
