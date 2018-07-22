package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.image.QueryImagesByNames;
import com.uinnova.test.step_definitions.api.dmv.navigationbar.Projects;
import com.uinnova.test.step_definitions.api.dmv.sys.vframe.GetAuthMenuTree;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-12-21 编写人:sunsl 功能介绍:
 */
public class Scenario_image_preview {
	private JSONObject result;

	/* ==================Scenario: Image_获取模块跳转码============ */
	@When("^获取模块跳转码$")
	public void projects() {
		Projects projects = new Projects();
		result = projects.projects();
		assertTrue(result.getBoolean("success"));
		JSONArray data = result.getJSONArray("data");
		assertNotNull(data);
		assertTrue(data.length() > 1);
	}

	@Then("^模块\"(.*)\"成功获取模块跳转码$")
	public void checkProjects(String modelName) {
		JSONArray data = result.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			if ((modelName).equals(obj.getString("id"))) {
				assertEquals(obj.getString("name"), "DMV");
				assertEquals(obj.getString("clickable"), "1");
				assertEquals(obj.getString("code"), "0401");
				break;
			}
		}
	}


   /* ================== Scenario: Image_获取菜单信息================== */
	@When("^dmv获取菜单信息$")
	public void getAuthMenuTree() {
		GetAuthMenuTree getAuthMenuTree = new GetAuthMenuTree();
		result = getAuthMenuTree.getAuthMenuTree();
		assertTrue(result.getBoolean("success"));
	}

	@Then("^模块\"(.*)\"成功获取菜单信息$")
	public void checkGetAuthMenuTree(String modelName) {
		String sql = "select ID,MENU_NAME from sys_menu where PARENT_ID =(select ID From sys_menu where MENU_NAME ='"
				+ modelName + "') order by ID";
		List menuList = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		assertEquals(menuList.size(),data.length());
			for (int i = 0; i < menuList.size(); i++) {
				HashMap menuHashMap = (HashMap) menuList.get(i);
				JSONObject obj = (JSONObject) data.get(i);
				assertEquals((BigDecimal) menuHashMap.get("ID"), obj.getBigDecimal("id"));
				assertEquals(menuHashMap.get("MENU_NAME"), obj.getString("text"));
			}
	}

	/* =====================Scenario: Image_获取图片信息====================== */
	@When("^获取图片\"(.*)\"信息$")
	public void queryImagesByNames(String imageName) {
		QueryImagesByNames queryImagesByNames = new QueryImagesByNames();
		result = queryImagesByNames.queryImagesByNames(imageName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功获取图片\"(.*)\"信息$")
	public void checkQueryImagesByNames(String imageName) {
		String sql = "SELECT CREATE_TIME,IMG_SIZE,IMG_FULL_NAME,SEARCH_FIELD,RLT_IMG_ID_1,RLT_IMG_ID_2,UPOR_ID,DIR_ID,"
				+ "UPOR_NAME,CREATOR,MODIFIER,ID,IMG_NAME,ORDER_NO,UP_TIME,IMG_GROUP,DOMAIN_ID,IMG_PATH,"
				+ "DATA_STATUS,MODIFY_TIME FROM cc_image where IMG_NAME = '" + imageName + "' and DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List imageList = JdbcUtil.executeQuery(sql);

		JSONArray data = result.getJSONArray("data");
		if (imageList.size() == data.length()) {
			for (int i = 0; i < data.length(); i++) {
				HashMap imageHashMap = (HashMap) imageList.get(i);
				JSONObject obj = (JSONObject) data.get(i);
				assertEquals(imageHashMap.get("CREATE_TIME"), obj.getBigDecimal("createTime"));
				assertEquals(imageHashMap.get("IMG_SIZE"), obj.getBigDecimal("imgSize"));
				assertEquals(imageHashMap.get("IMG_FULL_NAME"), obj.getString("imgFullName"));
				assertEquals(imageHashMap.get("SEARCH_FIELD"), obj.getString("searchField"));
				if(imageHashMap.get("RLT_IMG_ID_1") != null){
				   assertEquals(imageHashMap.get("RLT_IMG_ID_1"), obj.getBigDecimal("rltImgId1"));
				}
				if(imageHashMap.get("RLT_IMG_ID_2") != null){
				   assertEquals(imageHashMap.get("RLT_IMG_ID_2"), obj.getBigDecimal("rltImgId2"));
				}
				assertEquals(imageHashMap.get("UPOR_ID"), obj.getBigDecimal("uporId"));
				assertEquals(imageHashMap.get("DIR_ID"), obj.getBigDecimal("dirId"));
				assertEquals(imageHashMap.get("UPOR_NAME"), obj.getString("uporName"));
				assertEquals(imageHashMap.get("CREATOR"), obj.getString("creator"));
				assertEquals(imageHashMap.get("MODIFIER"), obj.getString("modifier"));
				assertEquals(imageHashMap.get("ID"), obj.getBigDecimal("id"));
				assertEquals(imageHashMap.get("IMG_NAME"), obj.getString("imgName"));
				assertEquals(imageHashMap.get("ORDER_NO"), obj.getBigDecimal("orderNo"));
				assertEquals(imageHashMap.get("UP_TIME"), obj.getBigDecimal("upTime"));
				assertEquals(imageHashMap.get("IMG_GROUP"), obj.getBigDecimal("imgGroup"));
				assertEquals(imageHashMap.get("DOMAIN_ID"), obj.getBigDecimal("domainId"));
				String imgPathDb = (String)imageHashMap.get("IMG_PATH");
				if(QaUtil.gethasPort()){
					imgPathDb = QaUtil.getServerUrl()+":1512"+ "/vmdb-sso/rsm/cli/read" + imgPathDb;
				}else{
					imgPathDb = QaUtil.getServerUrl()+"/vmdb-sso/rsm/cli/read" + imgPathDb;
				}
				
				String imgPathObj = obj.getString("imgPath");
				assertEquals(imgPathDb, imgPathObj);
				assertEquals(imageHashMap.get("DATA_STATUS"), obj.getBigDecimal("dataStatus"));
				assertEquals(imageHashMap.get("MODIFY_TIME"), obj.getBigDecimal("modifyTime"));

			}
		}
	}
}
