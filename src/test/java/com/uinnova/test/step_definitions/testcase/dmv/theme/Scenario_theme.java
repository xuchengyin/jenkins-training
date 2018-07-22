package com.uinnova.test.step_definitions.testcase.dmv.theme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ciClass.QueryAllCiClassList;
import com.uinnova.test.step_definitions.api.dmv.ciClass.RemovePluginAttrByIds;
import com.uinnova.test.step_definitions.api.dmv.ciClass.SaveOrUpdateCiClassAttrDefs;
import com.uinnova.test.step_definitions.api.dmv.ciClass.SaveOrUpdatePluginAttr;
import com.uinnova.test.step_definitions.api.dmv.ciRltClass.QueryPage;
import com.uinnova.test.step_definitions.api.dmv.ciRltClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.dmv.config.QueryConfigList;
import com.uinnova.test.step_definitions.api.dmv.config.SaveOrUpdateConfig;
import com.uinnova.test.step_definitions.api.dmv.theme.QueryThemeInfoList;
import com.uinnova.test.step_definitions.api.dmv.theme.RemoveThemeField;
import com.uinnova.test.step_definitions.api.dmv.theme.SaveOrUpdateThemeField;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-27 编写人:sunsl 功能介绍:DMV 设置的配置测试用例
 */
public class Scenario_theme {
	JSONObject result;
	HashMap themeFieldHashMap = new HashMap();
	List themeFieldList = new ArrayList();
	/* ===============Scenario:Theme_添加配置标签设置、更新配置标签设置、删除配置标签设置============== */
	@When("^给分类名称为\"(.*)\"的分类添加配置$")
	public void createTheme(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		HashMap retHashMap = themeUtil.addTheme(className);
		themeFieldList = (ArrayList)retHashMap.get("themeFieldList");
		SaveOrUpdateThemeField su = new SaveOrUpdateThemeField();
		result = su.saveOrUpdateThemeField(1,1,themeUtil.getClassId(className),(JSONArray)retHashMap.get("themeFields"));
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的分类添加配置成功$")
	public void checkCreateTheme(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT CLASS_ID,FIELD_ID,FIELD_NAME FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getClassId(className)
				+ " AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		// 查询表vc_theme_field
		List list = JdbcUtil.executeQuery(sql);
		for (int i = 0; i < list.size(); i++) {
			HashMap themeHashMap = (HashMap) list.get(i);
			JSONObject themeObj = (JSONObject) themeFieldList.get(i);
			if ((((BigDecimal) themeHashMap.get("FIELD_ID")).compareTo(themeObj.getBigDecimal("fieldId"))==0)
					&& themeHashMap.get("FIELD_NAME").equals(themeObj.getString("fieldName"))) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}

	@When("^分类名称为\"(.*)\"的分类配置属性排序$")
	public void saveOrUpdateCiClassAttrDefs(String className) {
		// {"ciType":1,"id":10002}
		ThemeUtil themeUtil = new ThemeUtil();
		JSONArray attrDefs = themeUtil.updateAttrDefs(className,themeFieldList);
		SaveOrUpdateCiClassAttrDefs su = new SaveOrUpdateCiClassAttrDefs();
		result = su.saveOrUpdateCiClassAttrDefs(className,attrDefs);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的分类的配置属性成功排序$")
	public void checkSaveOrUpdateCiClassAttrDefs(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT ID,PRO_NAME, ORDER_NO FROM cc_ci_attr_def WHERE CLASS_ID = " + themeUtil.getClassId(className)
				+ " AND CI_TYPE =1 AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		JSONObject themeObj = (JSONObject) themeFieldList.get(0);
		List dbList = JdbcUtil.executeQuery(sql);
		for (int i = 0; i < dbList.size(); i++) {
			HashMap themeHashMap = (HashMap) dbList.get(i);
			if (themeObj.getString("fieldName").equals(themeHashMap.get("PRO_NAME"))) {
				if (((BigDecimal) themeHashMap.get("ORDER_NO")).intValue() == 1) {
					assertTrue(true);
					break;
				}
			}
		}
	}

	@When("^分类名称为\"(.*)\"的分类更新配置属性$")
	public void updateTheme(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		HashMap retHashMap = themeUtil.updateTheme(className);
		SaveOrUpdateThemeField su = new SaveOrUpdateThemeField();
		themeFieldList = (ArrayList)retHashMap.get("themeFieldList");
		result = su.saveOrUpdateThemeField(1,1,themeUtil.getClassId(className),(JSONArray)retHashMap.get("themeFields"));
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的分类配置属性更新成功$")
	public void checkUpdateTheme(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		
		String sql = "SELECT CLASS_ID,FIELD_ID,FIELD_NAME FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getClassId(className)
				+ " and DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List themeList = JdbcUtil.executeQuery(sql);
		for (int i = 0; i < themeList.size(); i++) {
			HashMap themeHashMap = (HashMap) themeList.get(i);
			JSONObject obj = (JSONObject) themeFieldList.get(i);
			if (((BigDecimal) themeHashMap.get("FIELD_ID")).compareTo(obj.getBigDecimal("fieldId"))==0
					&& themeHashMap.get("FIELD_NAME").equals(obj.getString("fieldName"))) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}

	@When("^删除分类名称为\"(.*)\"的分类的配置$")
	public void deleteTheme(String className) {
		RemoveThemeField rt = new RemoveThemeField();
		result = rt.removeThemeField(1,1,className);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的分类成功删除配置$")
	public void checkDeleteTheme(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT ID,CLASS_ID FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getClassId(className) + " and DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List themeList = JdbcUtil.executeQuery(sql);

		if (themeList.size() == 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ===================Scenario Outline:Theme_搜索配置标签设置============= */
	@When("^搜索分类名称为\"(.*)\"的配置$")
	public void searchTheme(String searchKey) {
		QueryThemeInfoList qt = new QueryThemeInfoList();
		result = qt.queryThemeInfoList(1,1,searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的配置成功搜索出来$")
	public void checkSearchTheme(String searchKey) {
		String sql = "SELECT * FROM cc_ci_class WHERE id in (SELECT class_id FROM vc_theme_field WHERE theme_id =(SELECT id FROM vc_theme WHERE theme_name = '配置' and data_status = 1 and DOMAIN_ID = "+ QaUtil.domain_id+") and data_status = 1) and data_status = 1 and class_name like '%"
				+ searchKey + "%'";
		List themeList = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		if (data.length() == themeList.size()) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ====================Theme_KPI添加分类指标设置、删除分类指标设置============== */
	@When("^创建\"(.*)\"指标设置$")
	public void createThemeKpi(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		HashMap retHashMap = themeUtil.createThemeKpi(className);
		themeFieldHashMap = (HashMap)retHashMap.get("themeFieldHashMap");
		// 创建指标设置
		SaveOrUpdateThemeField su = new SaveOrUpdateThemeField();
		result = su.saveOrUpdateThemeField(2,2,themeUtil.getClassId(className),(JSONArray)retHashMap.get("themeFields"));
		assertTrue(result.getBoolean("success"));
	}

	@Then("^\"(.*)\"成功创建指标设置$")
	public void checkCreateThemeKpi(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT ID,THEME_ID,CLASS_ID,FIELD_ID,FIELD_NAME FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getClassId(className)
				+ " AND THEME_ID = 2 AND FIELD_TYPE = 2 AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List themeKpi = JdbcUtil.executeQuery(sql);
		HashMap themeKpiHashMap = (HashMap) themeKpi.get(0);
		if (((BigDecimal) themeKpiHashMap.get("FIELD_ID")).compareTo((BigDecimal)themeFieldHashMap.get("fieldId")) ==0
				&& themeKpiHashMap.get("FIELD_NAME").equals(themeFieldHashMap.get("fieldName"))) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^\"(.*)\"删除指标设置$")
	public void deleteThemeKpi(String className) {
		RemoveThemeField rt = new RemoveThemeField();
		result = rt.removeThemeField(2,2,className);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^\"(.*)\"成功删除指标设置$")
	public void checkDeleteThemeKpi(String className) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT ID,CLASS_ID FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getClassId(className)
				+ " and THEME_ID = 2 AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List themKpiList = JdbcUtil.executeQuery(sql);
		if (themKpiList.size() == 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/*===========Scenario:Theme_KPI添加关系指标设置、删除关系指标设置===*/
	@When("^创建指标名称为\"(.*)\"关系名称为\"(.*)\"的关系指标设置$")
	public void createThemeRltKpi(String kpiCode,String rltClassName) {
        ThemeUtil  themeUtil = new ThemeUtil();
        result = themeUtil.createThemeRltKpi(kpiCode,rltClassName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功创建指标名称为\"(.*)\"关系名称为\"(.*)\"的关系指标设置$")
	public void checkCreateThemeRltKpi(String kpiCode,String rltClassName) {
		ThemeUtil themeUtil = new ThemeUtil();
		String sql = "SELECT ID,CLASS_ID FROM vc_theme_field WHERE CLASS_ID = " + themeUtil.getRltClassId(rltClassName)
				+ " AND THEME_ID = 2 AND FIELD_TYPE = 2 AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id + " and FIELD_NAME ='" + kpiCode + "'";
		List themeRltKpi = JdbcUtil.executeQuery(sql);
		if (themeRltKpi.size() == 1) {
			assertTrue(true);
		}
	}

	@When("^搜索分类名称为\"(.*)\"的指标配置$")
	public void searchThemeKpi(String searchKey) {
		QueryThemeInfoList qt = new QueryThemeInfoList();
		result = qt.queryThemeInfoList(2,2,searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^分类名称为\"(.*)\"的指标配置成功搜索出来$")
	public void checkSearchThemeKpi(String searchKey) {
		String sql = "SELECT * FROM cc_ci_class WHERE id in (SELECT class_id FROM vc_theme_field WHERE theme_id =(SELECT id FROM vc_theme WHERE theme_name ='指标' and DATA_STATUS =1 and DOMAIN_ID = " + QaUtil.domain_id + ") AND DATA_STATUS =1) AND DATA_STATUS =1 AND class_name like '%"
				+ searchKey + "%'";
		List themeKpiList = JdbcUtil.executeQuery(sql);
		if (themeKpiList.size() == (result.getJSONArray("data")).length()) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ===================Scenario Outline: Theme_自动化工具关联增改删================== */
	@When("^创建链接名称为\"(.*)\"，链接地址为\"(.*)\"，窗口显示宽度为\"(.*)\"，高度为\"(.*)\"，分类名称为\"(.*)\"$")
	public void createPluginAttr(String proName, String proVal1, String width, String height,String ClassName) {
		if(proVal1.contains("proVal1")){
			   String filePath = Scenario_theme.class.getResource("/").getPath() + "testData/dmv/theme/" + proVal1;
			   proVal1 = (new TxtUtil()).readTxt(filePath);
		   }
		// 获得分类ID
		SaveOrUpdatePluginAttr su = new SaveOrUpdatePluginAttr();
		result = su.saveOrUpdatePluginAttr(proName,proVal1,ClassName,width,height);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^链接名称为\"(.*)\"，链接地址为\"(.*)\"，窗口显示宽度为\"(.*)\"，高度为\"(.*)\"，分类名称为\"(.*)\"的动态链接成功创建$")
	public void checkCreatePluginAttr(String proName, String proVal1, String width, String height,String className) throws InterruptedException {
		if(proVal1.contains("proVal1")){
			   String filePath = Scenario_theme.class.getResource("/").getPath() + "testData/dmv/theme/" + proVal1;
			   proVal1 = (new TxtUtil()).readTxt(filePath);
		 }
		ThemeUtil themeUtil = new ThemeUtil();
        BigDecimal classId = themeUtil.getClassId(className);
        HashMap pluginHashMap = themeUtil.getPluginAttr(proName);
		 String proVal2 = "{\"width\":"+width+ ",\"height\":"+ height +"}";
		 assertEquals(proName,pluginHashMap.get("PRO_NAME"));
		 assertEquals(proVal1,pluginHashMap.get("PRO_VAL_1"));
		 assertEquals(proVal2,pluginHashMap.get("PRO_VAL_2"));
		if (classId.compareTo((BigDecimal) (pluginHashMap.get("CLASS_ID")))==0) {
            assertTrue(true);
		}else{
			fail();
		}
	}
	
   @When("^链接名称为\"(.*)\"，修改链接名称为\"(.*)\"，修改链接地址为\"(.*)\"，修改窗口宽度为\"(.*)\"，高度为\"(.*)\"，分类名称为\"(.*)\"$")
   public void updatePluginAttr(String proName,String updateProName,String updateProVal1,String updateWidth, String updateHeight,String className){
	   SaveOrUpdatePluginAttr su = new SaveOrUpdatePluginAttr();
	   result = su.saveOrUpdatePluginAttr(proName,updateProName,updateProVal1,className,updateWidth,updateHeight);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^链接名称为\"(.*)\"，链接地址为\"(.*)\"，窗口宽度为\"(.*)\"，高度为\"(.*)\"，分类名称为\"(.*)\"的动态链接修改成功$")
   public void checkUpdatePluginAttr(String updateProName,String updateProVal1,String updateWidth,String updateHeight,String className){
	    ThemeUtil themeUtil =new ThemeUtil();
	   HashMap pluginHashMap = themeUtil.getPluginAttr(updateProName);
	   String proVal2 = "{\"width\":"+updateWidth+ ",\"height\":"+ updateHeight +"}";
	   assertEquals(updateProName,pluginHashMap.get("PRO_NAME"));
	   assertEquals(updateProVal1,pluginHashMap.get("PRO_VAL_1"));
	   assertEquals(proVal2,pluginHashMap.get("PRO_VAL_2"));
	   if(themeUtil.getClassId(className).compareTo((BigDecimal)(pluginHashMap.get("CLASS_ID")))==0){
		   assertTrue(true);
	   }else{
		   fail();
	   }
   }
   
   @When("^删除链接名称为\"(.*)\"动态链接$")
   public void deletePluginAttr(String updateProName){
	   RemovePluginAttrByIds rp = new RemovePluginAttrByIds();
	   result = rp.removePluginAttrByIds(updateProName);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^动态链接\"(.*)\"成功删除$")
   public void checkDeletePluginAttr(String updateProName){
	   String sql = "SELECT ID,CLASS_ID,PRO_NAME,PRO_VAL_1,PRO_VAL_2 FROM cc_ci_plugin_attr WHERE PRO_NAME = '" + updateProName 
				+ "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
	   List pluginList = JdbcUtil.executeQuery(sql);
	   assertEquals(0, pluginList.size());
   }
   
	/* ===================== Scenario Outline: Theme_设置开关================ */
	@When("^设置开关\"(.*)\",cfgContent为\"(.*)\"$")
	public void saveOrUpdateConfig(String cfgCode,String cfgContent) {
		SaveOrUpdateConfig saveOrUpdateConfig = new SaveOrUpdateConfig();
		result = saveOrUpdateConfig.saveOrUpdateConfig2(cfgCode,cfgContent);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"),-1);
	}
	
	@Then("^成功设置开关\"(.*)\"$")
	public void checkSaveOrUpdateConfig(String cfgCode){
		String sql = "SELECT CFG_CODE, CFG_CONTENT,CREATE_TIME,CREATOR,DATA_STATUS,DOMAIN_ID,"
				+ "MODIFY_TIME,MODIFIER,ID FROM vc_base_config WHERE CFG_CODE='" + cfgCode + "' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List configList = JdbcUtil.executeQuery(sql);
		if (configList.size() > 0) {
			QueryConfigList queryConfigList = new QueryConfigList();
			result = queryConfigList.queryConfigList("");
			assertTrue(result.getBoolean("success"));
			assertEquals(result.getInt("code"), -1);
			JSONArray data = result.getJSONArray("data");
			if (data.length() >0 ){
				for(int i = 0; i < data.length(); i++){
					JSONObject obj = (JSONObject)data.get(i);
					if (cfgCode.equals(obj.getString("cfgCode"))){
						HashMap configHashMap = (HashMap)configList.get(0);
						assertEquals(configHashMap.get("CFG_CODE"),obj.getString("cfgCode"));
						assertEquals(configHashMap.get("CFG_CONTENT"),obj.getString("cfgContent"));
						assertEquals(configHashMap.get("CREATE_TIME"),obj.getBigDecimal("createTime"));
						assertEquals(configHashMap.get("CREATOR"),obj.getString("creator"));
						assertEquals(((BigDecimal)configHashMap.get("DATA_STATUS")).intValue(),obj.getInt("dataStatus"));
						assertEquals((BigDecimal)configHashMap.get("DOMAIN_ID"),obj.getBigDecimal("domainId"));
						assertEquals(configHashMap.get("MODIFY_TIME"),obj.getBigDecimal("modifyTime"));
						assertEquals((BigDecimal)configHashMap.get("ID"),obj.getBigDecimal("id"));
						break;
					}
				}
			}else{
				fail();
			}
		} else {
			fail();
		}
	}
	
	@And("^删除设置开关\"(.*)\"$")
	public void removeConfig(String cfgCode){
		String sql = "UPDATE vc_base_config set DATA_STATUS = 0 where CFG_CODE='" + cfgCode + "' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		JdbcUtil.executeUpdate(sql);
	}
	
	/*=====================Scenario Outline: Theme_设置刷新频率======================*/
	@When("^设置刷新频率\"(.*)\",cfgContent为\"(.*)\"$")	
	public void saveOrUpdateConfig2(String cfgCode,String cfgContent){
		SaveOrUpdateConfig saveOrUpdateConfig = new SaveOrUpdateConfig();
		result = saveOrUpdateConfig.saveOrUpdateConfig2(cfgCode, cfgContent);
		assertTrue(result.getBoolean("success"));
	}
	
	/*====Scenario Outline: Theme_关系分类设置不同线型===========*/
	@When("^修改名称为\"(.*)\"的关系分类，其动态效果为\"(.*)\",关系样式为\"(.*)\",关系宽度为\"(.*)\",关系箭头为\"(.*)\",关系颜色为\"(.*)\"$")
	public void saveOrUpdate(String rltName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineColor){
		SaveOrUpdate saveOsrUpdate = new SaveOrUpdate();
		result = saveOsrUpdate.saveOrUpdate(rltName, lineAnime, lineType, lineBorder, lineDirect, lineColor);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功修改名称为\"(.*)\"的关系分类创建成功,其动态效果为\"(.*)\",关系样式为\"(.*)\",关系宽度为\"(.*)\",关系箭头为\"(.*)\",关系颜色为\"(.*)\"$")
	public void checkSaveOrUpdate(String rltName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineColor)
	{
		QueryPage queryPage = new QueryPage();
		result = queryPage.queryPage();
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		for(int i = 0; i< data.length(); i++){
			JSONObject obj = (JSONObject)data.get(i);
			JSONObject ciClass = obj.getJSONObject("ciClass");
			if(ciClass.getString("classCode").equals(rltName)){
				assertEquals(Integer.toString(ciClass.getInt("lineAnime")),lineAnime);
				assertEquals(ciClass.getString("lineType"),lineType);
				assertEquals(Integer.toString(ciClass.getInt("lineBorder")),lineBorder);
				assertEquals(ciClass.getString("lineDirect"),lineDirect);
				assertEquals(ciClass.getString("lineColor"),lineColor);
				break;
			}
		}
	}
	
	/*============Scenario Outline: Theme_设置模板===========*/
	@When("^设置模板\"(.*)\",cfgContent为\"(.*)\"$")	
	public void saveOrUpdateConfig3(String cfgCode,String cfgContent){
		SaveOrUpdateConfig saveOrUpdateConfig = new SaveOrUpdateConfig();
		result = saveOrUpdateConfig.saveOrUpdateConfig2(cfgCode, cfgContent);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功设置模板\"(.*)\",\"(.*)\"$")
	public void checkSaveOrUpdateConfig3(String cfgCode,String cfgContent){
		QueryConfigList queryConfigList = new QueryConfigList();
		JSONObject result = queryConfigList.queryConfigList("");
		JSONArray data = result.getJSONArray("data");
		for(int i = 0; i < data.length(); i ++){
			JSONObject obj = (JSONObject)data.get(i);
			if (cfgCode.equals(obj.getString("cfgCode"))){
				assertEquals(Integer.parseInt(cfgContent),obj.getInt("cfgContent"));
				break;
			}
		}
	}

	/*============Scenario: Theme_设置菜单顺序============*/
	@When("^设置菜单顺序\"(.*)\",cfgContent为\"(.*)\"$")
	public void saveOrUpdateConfig4(String cfgCode,String cfgContent){
		SaveOrUpdateConfig saveOrUpdateConfig = new SaveOrUpdateConfig();
		result =saveOrUpdateConfig.saveOrUpdateConfig2(cfgCode, cfgContent);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功改变菜单顺序\"(.*)\",\"(.*)\"$")
	public void checkSaveOrUpdateConfig4(String cfgCode,String cfgContent){
		QueryConfigList queryConfigList = new QueryConfigList();
		JSONObject result = queryConfigList.queryConfigList("");
		JSONArray data =result.getJSONArray("data");
		for(int i=0;i < data.length(); i ++){
			JSONObject obj = (JSONObject)data.get(i);
			if (cfgCode.equals(obj.getString("cfgCode"))){
				assertEquals(cfgContent,obj.getString("cfgContent"));
				break;
			}
		}
	}
	
	/*===============Theme_查询分类列表=================*/
	@When("^查询分类列表$")
	public void queryAllCiClassList(){
		QueryAllCiClassList queryAllCiClassList = new QueryAllCiClassList();
		result = queryAllCiClassList.queryAllCiClassList();
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^成功查询分类列表$")
	public void checkQueryAllCiClassList(){
		String sql ="SELECT ID, CLASS_CODE FROM cc_ci_class WHERE DATA_STATUS = 1 ";
		List list = JdbcUtil.executeQuery(sql);
		String sqlMapping ="SELECT A.ID AS ID ,A.NM_CI_CODE AS NM_CI_CODE from cc_fix_attr_mapping A ,cc_ci_class B where A.ID =B.ID AND B.DATA_STATUS =1";
		List mappingList = JdbcUtil.executeQuery(sqlMapping);
		HashMap mappingMap = new HashMap();
		if(mappingList.size() > 0){
			for(int k =0; k < mappingList.size(); k++){
				HashMap map = (HashMap)mappingList.get(k);
				mappingMap.put(map.get("ID"), map);
			}
		}
		
		HashMap mapClass = new HashMap();
		if(list.size() > 0){
			for(int i = 0; i < list.size(); i++){
			    HashMap map = (HashMap)list.get(i);
			    mapClass.put(map.get("ID"), map);
			}
		}
		
		JSONArray data = result.getJSONArray("data");
		if(data.length() > 0){
			for(int j = 0; j < data.length(); j++){
				JSONObject obj = (JSONObject)data.get(j);
				JSONObject ciClass = obj.getJSONObject("ciClass");
				HashMap map = (HashMap)mapClass.get(ciClass.getBigDecimal("id"));
				assertEquals(ciClass.getString("classCode"),map.get("CLASS_CODE"));
				if(obj.toString().contains("fixMapping")){
				    JSONObject fixMapping = obj.getJSONObject("fixMapping");				
				    HashMap fixMappingMap =(HashMap)mappingMap.get(fixMapping.getBigDecimal("id"));
				    assertEquals(fixMapping.getString("nmCiCode"),fixMappingMap.get("NM_CI_CODE"));
				}
			}
		}
	}
}

