package com.uinnova.test.step_definitions.testcase.dmv.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.severity.QuerySeverityList;
import com.uinnova.test.step_definitions.api.dmv.ciClass.QueryPluginAttrPage;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryCiBetweenRlt;
import com.uinnova.test.step_definitions.api.dmv.diagram.ConvertDiagramAndTemplate;
import com.uinnova.test.step_definitions.api.dmv.diagram.CopyDiagramById;
import com.uinnova.test.step_definitions.api.dmv.diagram.ExportDiagramConfigInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.GetConfigInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryAlarmListByDiagramId;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryCurrentPerformancesByCiCodes;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramTagListByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramTemplate;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryEnshDiagramPage;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryEventWallCdt;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryRecycleDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryUserEditAuthByDiagramId;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDiagramByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.RestoreRecycleBinDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDir;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveTmpSvg;
import com.uinnova.test.step_definitions.api.dmv.diagram.ShareDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramBaseInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramNameAndDirById;
import com.uinnova.test.step_definitions.api.dmv.diagramVersion.QueryDiagramVersionByDiagramId;
import com.uinnova.test.step_definitions.api.dmv.group.QueryGroupUserInfoList;
import com.uinnova.test.step_definitions.api.dmv.group.RemoveGroupById;
import com.uinnova.test.step_definitions.api.dmv.image.QueryImage3dByClassIds;
import com.uinnova.test.step_definitions.api.dmv.theme.QueryThemeByName;
import com.uinnova.test.step_definitions.testcase.dmv.group.Scenario_group;
import com.uinnova.test.step_definitions.testcase.dmv.image.Scenario_image_canvas;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.MyUtil;
import com.uinnova.test.step_definitions.utils.dmv.ThemeUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-12-05 编写人:sunsl 功能介绍:DMV我的测试用例类
 */
public class Scenario_my {
	JSONObject result;
	private String resultStr;
	private JSONArray groupIdArray = new JSONArray();
    private List<String> diagramDirList = new ArrayList<String>(); //用于记录一共新建了多少文件夹， 用户After方法清理数据
	
	@After("@delDiagramDir")
	public void delDiagraDir(){
		if (!diagramDirList.isEmpty()){
			for (int i=0; i<diagramDirList.size(); i++){
				String diagramDirName = diagramDirList.get(i);
				MyUtil myUtil = new MyUtil();
				JSONObject result = myUtil.deleteDir(diagramDirName);
				assertTrue(result.getBoolean("success"));
				diagramDirList.remove(diagramDirName);
				i--;
			}
		}
	}

	/* ======Scenario Outline: My_新建文件夹,移动视图,重命名文件夹,删除文件夹===== */
	@When("^创建名称为\"(.*)\"的文件夹$")
	public void createDir(String dirName) {
		SaveOrUpdateDir su = new SaveOrUpdateDir();
		result = su.saveOrUpdateDir(dirName);
		assertTrue(result.getBoolean("success"));
		diagramDirList.add(dirName);
	}

	@Then("^名称为\"(.*)\"的文件夹创建成功$")
	public void checkCreateDir(String dirName) {
		MyUtil myUtil = new MyUtil();
		List dirList = myUtil.getDirList(dirName);
		HashMap dirHashMap = (HashMap) dirList.get(0);
		if (dirName.equals(dirHashMap.get("DIR_NAME"))) {
			assertTrue(true);
		} else {
			fail();
		}
	}
    @And("^再次创建名称为\"(.*)\"的文件夹失败,kw=\"(.*)\"$")
    public void againCreateDir(String dirName, String kw){
    	SaveOrUpdateDir su = new SaveOrUpdateDir();
		result = su.saveOrUpdateDirAgain(dirName, kw);
		assertEquals(null,result);
    }
	@When("^文件夹名称为\"(.*)\"重命名为\"(.*)\"$")
	public void updateDirName(String dirName, String updateDirName) {
		MyUtil myUtil = new MyUtil();
		List dirList = myUtil.getDirList(dirName);
		HashMap dirHashMap = (HashMap) dirList.get(0);
		SaveOrUpdateDir su = new SaveOrUpdateDir();
		result = su.saveOrUpdateDir(dirHashMap, updateDirName, dirName);
		assertTrue(result.getBoolean("success"));
		diagramDirList.remove(dirName);
		diagramDirList.add(updateDirName);
	}

	@Then("^文件夹名称成功重命名为\"(.*)\"$")
	public void checkUpdateDirName(String updateDirName) {
		MyUtil myUtil = new MyUtil();
		List dirList = myUtil.getDirList(updateDirName);
		HashMap dirHashMap = (HashMap) dirList.get(0);
		if (updateDirName.equals(dirHashMap.get("DIR_NAME"))) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^将视图\"(.*)\"移动到文件夹\"(.*)\"$")
	public void moveDirAndDiagram(String diagramName, String dirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.moveDirAndDiagram(diagramName, dirName);
		assertTrue(result.getBoolean("success"));		
	}
	
	@And("^将多个视图\"(.*)\"移动到文件夹\"(.*)\"$")
	public void moveDirAndDiagramDupli(@Delimiter(",") List<String> diagramNameList,String dirName){
		MyUtil myUtil = new MyUtil();
		for(int i = 0; i<diagramNameList.size(); i ++){
			String diagramName = diagramNameList.get(i);
			result = myUtil.moveDirAndDiagram(diagramName, dirName);
			assertTrue(result.getBoolean("success"));
		}
	}

	@Then("^视图\"(.*)\"成功移动到文件夹\"(.*)\"$")
	public void checkMoveDirAndDiagram(String diagramName, String dirName) throws InterruptedException {
		Thread.sleep(3000);
		MyUtil myUtil = new MyUtil();
		DiagramUtil diagramUtil = new DiagramUtil();
		result = myUtil.queryMyDirInfoPageByParentId(dirName, "", 0);
		JSONObject data = result.getJSONObject("data");
		JSONObject diagramInfoPage = data.getJSONObject("diagramInfoPage");
		JSONArray dataArray = diagramInfoPage.getJSONArray("data");
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject dataObj = (JSONObject) dataArray.get(i);
			JSONObject diagram = dataObj.getJSONObject("diagram");
			if (diagram.getBigDecimal("id").compareTo(diagramUtil.getDiagramIdByName(diagramName))==0) {
				assertTrue(true);
				break;
			} else {
				fail();
			}
		}
	}

	@When("^删除文件夹\"(.*)\"$")
	public void deleteDir(String dirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.deleteDir(dirName);
		assertTrue(result.getBoolean("success"));
		diagramDirList.remove(dirName);
	}

	@Then("^成功删除文件夹\"(.*)\"$")
	public void checkDeleteDir(String dirName) {
		MyUtil myUtil = new MyUtil();
		List dirList = myUtil.getDirList(dirName);
		if (dirList.size() == 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ================Scenario: My_移动文件夹=============== */
	@When("^将文件夹\"(.*)\"移动到新建文件夹\"(.*)\"$")
	public void moveDir(String srcDirName, String targetDirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.moveDir(srcDirName, targetDirName);
		assertEquals(result.getInt("data") , 1);
		diagramDirList.add(targetDirName);
	}

	@Then("^文件夹\"(.*)\"成功被移动到文件夹\"(.*)\"$")
	public void checkMoveDir(String srcDirName, String tagetDirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.queryMyDirInfoPageByParentId(tagetDirName, "", 0);
		JSONObject data = result.getJSONObject("data");
		JSONArray childrenDirs = data.getJSONArray("childrenDirs");
		JSONObject childrenDirObj = (JSONObject) childrenDirs.get(0);
		if (myUtil.getDirIdByName(srcDirName).compareTo(childrenDirObj.getBigDecimal("id"))==0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ============Scenario Outline: My_文件夹内根据输入内容搜索视图====== */
	@When("^在文件夹\"(.*)\"内搜索视图名称为\"(.*)\"的视图$")
	public void searchLikeDiagram(String dirName, String searchKey) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.searchLikeDiagram(dirName, searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^在文件夹\"(.*)\"内视图名称为\"(.*)\"的视图成功搜索出来$")
	public void checkSearchLikeDiagram(String dirName, String searchKey) {
		MyUtil myUtil = new MyUtil();
		// 从查询结果中取值
		JSONObject data = result.getJSONObject("data");
		JSONObject diagramInfoPage = data.getJSONObject("diagramInfoPage");
		Integer totalRows = diagramInfoPage.getInt("totalRows");
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE DIR_ID = " + myUtil.getDirIdByName(dirName)
				+ " AND NAME like '%" + searchKey + "%' AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),totalRows.intValue());
	
	}

	/* ===============Scenario: My_文件夹内搜索视图=============== */
	@When("^在文件夹\"(.*)\"内搜索视图\"(.*)\"$")
	public void searchDiagram(String dirName,String searchKey) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.queryMyDirInfoPageByParentId(dirName, searchKey, 1);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^在文件夹\"(.*)\"内视图\"(.*)\"被成功搜索出来$")
	public void checkSearchDiagram(String dirName,String searchKey) {
		MyUtil myUtil = new MyUtil();
		JSONObject data = result.getJSONObject("data");
		JSONObject diagramInfoPage = data.getJSONObject("diagramInfoPage");
		Integer totalRows = diagramInfoPage.getInt("totalRows");
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE  DIR_ID =" + myUtil.getDirIdByName(dirName)
				+ " AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id + " AND NAME like '%" + searchKey +"%'";
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),totalRows.intValue());
	}

	/* =================Scenario: My_文件夹内搜索文件夹================ */
	@When("^在文件夹\"(.*)\"内搜索文件夹\"(.*)\"$")
	public void searchDir(String targetDirName, String srcDirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.queryMyDirInfoPageByParentId(srcDirName, "", 3);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^在文件夹\"(.*)\"内文件夹\"(.*)\"被成功搜索出来$")
	public void checkSearchDir(String srcDirName,String dirName) {
		MyUtil myUtil = new MyUtil();
		String sql = "SELECT * FROM vc_diagram_dir WHERE PARENT_ID = " + myUtil.getDirIdByName(dirName)
				+ " AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List dirList = JdbcUtil.executeQuery(sql);
		JSONObject data = result.getJSONObject("data");
		JSONArray childrenDirs = data.getJSONArray("childrenDirs");
		if (dirList.size() == childrenDirs.length()) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/*
	 * ==========================Scenario: My_文件夹内搜索组合视图======================
	 */
	@When("^根据视图\"(.*)\"创建组合视图\"(.*)\"同时将组合视图移动到文件夹\"(.*)\"$")
	public void createCombDiagram(String diagramName, String combName, String dirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.createCombDiagram(diagramName, combName, dirName);
		assertTrue(result.getBoolean("success"));
	}

	@When("^在文件夹\"(.*)\"内搜索组合视图$")
	public void searchCombDiagram(String dirName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.queryMyDirInfoPageByParentId(dirName, "", 2);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^在文件夹\"(.*)\"内组合视图被成功搜索出来$")
	public void checkSearchCombDiagram(String dirName) {
		JSONObject data = result.getJSONObject("data");
		MyUtil myUtil = new MyUtil();
		JSONObject diagramInfoPage = data.getJSONObject("diagramInfoPage");
		Integer totalRows = diagramInfoPage.getInt("totalRows");
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE  DIR_ID =" + myUtil.getDirIdByName(dirName)
				+ " AND DIAGRAM_TYPE =2 AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		if (diagramList.size() == totalRows) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^删除组合视图\"(.*)\"$")
	public void deleteComb(String combName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.deleteComb(combName);
		assertTrue(result.getBoolean("success"));
	}

	/* =============Scenario Outline: My_回收站搜索视图，清除视图================= */
	@When("^在回收站内搜索视图名称为\"(.*)\"的视图$")
	public void searchRecycleDiagram(String searchKey) {
		QueryRecycleDiagram qr = new QueryRecycleDiagram();
		result = qr.queryRecycleDiagram(searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^回收站内视图名称为\"(.*)\"的视图成功搜索出来$")
	public void checkSearchRecycleDiagram(String searchKey) {
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE name like '%" + searchKey
				+ "%' AND STATUS =0 AND DOMAIN_ID = "+ QaUtil.domain_id+" AND USER_ID = "+ QaUtil.user_id+" AND DATA_STATUS =1";
		List diagramList = JdbcUtil.executeQuery(sql);
		JSONObject data = result.getJSONObject("data");
		Integer totalRows = data.getInt("totalRows");
		if (diagramList.size() == totalRows) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^在回收站内清除视图\"(.*)\"$")
	public void removeDiagramByIds(String diagramName) {
		RemoveDiagramByIds rd = new RemoveDiagramByIds();
		result = rd.removeDiagramByIds(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"被成功清除$")
	public void checkRemoveDiagramByIds(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE id =" + diagramUtil.getDiagramIdByName(diagramName)
				+ " AND STATUS = 0 AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),0);
	}

	/* ================Scenario: My_回收站还原视图====================== */
	@When("^将回收站的视图\"(.*)\"还原$")
	public void restoreRecycleBinDiagram(String diagramName) {
		RestoreRecycleBinDiagram rrbd = new RestoreRecycleBinDiagram();
		result = rrbd.restoreRecycleBinDiagram(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^回收站的视图\"(.*)\"成功被还原$")
	public void checkRestoreRecycleBinDiagram(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE id = " + diagramUtil.getDiagramIdByName(diagramName)
				+ " AND STATUS = 1 AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),1);
	}

	/* =============Scenario: My_视图重命名，删除视图====================== */
	@When("^给视图\"(.*)\"重命名为\"(.*)\"$")
	public void renameDiagram(String diagramName, String rename) {
		UpdateDiagramNameAndDirById ud = new UpdateDiagramNameAndDirById();
		result = ud.updateDiagramNameAndDirById(rename, diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"重命名为\"(.*)\"成功$")
	public void checkRameDiagram(String diagramName, String rename) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE ID = " + diagramUtil.getDiagramIdByName(rename)
				+ " AND STATUS=1 AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		HashMap diagramHashMap = (HashMap) diagramList.get(0);
		assertEquals(rename, diagramHashMap.get("NAME").toString());
	}

	@Then("^视图\"(.*)\"被成功删除$")
	public void checkDeleteDiagram(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE ID = " + diagramUtil.getDiagramIdByName(diagramName)
				+ " AND STATUS=1 AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(0, diagramList.size());
	}

	/* ======================Scenario: My_克隆视图====================== */
	@When("^视图\"(.*)\"克隆为\"(.*)\"$")
	public void copyDiagramById(String srcName, String newName) {
		CopyDiagramById cd = new CopyDiagramById();
		result = cd.copyDiagramById(newName, srcName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图名为\"(.*)\"成功克隆$")
	public void checkCopyDiagramById(String newName) {
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE NAME = '" + newName + "' AND STATUS = 1 AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(), 1);
		// HashMap diagramHashMap = (HashMap) diagramList.get(0);
		// JSONArray dirIds = new JSONArray();
		// JSONArray diagramIds = new JSONArray();
		// diagramIds.put(diagramHashMap.get("ID"));
		RemoveDirByIds rd = new RemoveDirByIds();
		result = rd.removeDirByIds(newName);
		assertTrue(result.getBoolean("success"));
	}

	/* ====================== Scenario: My_将视图转化为模板==================== */
	@When("^将视图\"(.*)\"转化为模板$")
	public void convertDiagramAndTemplate(String diagramName) {
		ConvertDiagramAndTemplate cd = new ConvertDiagramAndTemplate();
		result = cd.convertDiagramAndTemplate(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图\"(.*)\"成功转化为模板$")
	public void checkConvertDiagramAndTemplate(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		QueryDiagramTemplate qdt = new QueryDiagramTemplate();
		result = qdt.queryDiagramTemplate();
		JSONArray data = result.getJSONArray("data");
		boolean flag =false;
		for (int i = 0; i < data.length(); i++) {
			JSONObject dataObj = (JSONObject) data.get(i);
			JSONObject diagramObj = dataObj.getJSONObject("diagram");
			if (diagramObj.getBigDecimal("id").compareTo(diagramUtil.getDiagramIdByName(diagramName))==0) {
				flag = true;
				break;
			}
		}
		assertTrue(flag);
	}

	/* =================Scenario: My_查看视图版本============ */
	@And("^给视图\"(.*)\"创建名称为\"(.*)\"版本号为\"(.*)\"的版本$")
	public void SaveDiagramVersion(String diagramName, String versionDesc,String versionNo) {
	    if(versionDesc.indexOf(".") > 0){
			   String filePath = Scenario_my.class.getResource("/").getPath() + "testData/dmv/version/" + versionDesc;
			   versionDesc = (new TxtUtil()).readTxt(filePath);
		}
		MyUtil myUtil = new MyUtil();
		result = myUtil.saveDiagramVersion(diagramName, versionDesc,versionNo);
		assertTrue(result.getBoolean("success"));
	}

	@When("^取得视图\"(.*)\"版本$")
	public void queryDiagramVersionByDiagramId(String diagramName) {
		QueryDiagramVersionByDiagramId qdvb = new QueryDiagramVersionByDiagramId();
		result = qdvb.queryDiagramVersionByDiagramId(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^视图版本为\"(.*)\"版本号为\"(.*)\"正确取得$")
	public void checkQueryDiagramVersionByDiagramId(String versionDesc,String versionNo) {
		 if(versionDesc.indexOf(".") > 0){
			   String filePath = Scenario_my.class.getResource("/").getPath() + "testData/dmv/version/" + versionDesc;
			   versionDesc = (new TxtUtil()).readTxt(filePath);
		}
		JSONArray data = result.getJSONArray("data");
		JSONObject dataObj = (JSONObject) data.get(0);
		JSONObject diagramVersion = dataObj.getJSONObject("diagramVersion");
		String versionDescdb = diagramVersion.getString("versionDesc");
		String versionNodb = diagramVersion.getString("versionNo");
		assertEquals(versionDesc, versionDescdb);
		assertEquals(versionNo,versionNodb);
	}

	/* ====================Scenario: My_查看视图导出PNG文件======================== */
//	@When("^查看视图\"(.*)\"导出PNG文件$")
//	public void saveTmpSvg(String diagramName) {
//		SaveTmpSvg sts = new SaveTmpSvg();
//		result = sts.saveTmpSvg(diagramName);
//		assertTrue(result.getBoolean("success"));
//	}
//
//	@Then("^查看视图成功导出PNG文件$")
//	public void checkSaveTmpSvg() {
//		String data = result.getString("data");
//		if (data.contains("svg")) {
//			assertTrue(true);
//		} else {
//			fail();
//		}
//	}

	/* =======================Scenario: My_查看视图导出配置信息======================== */
	@When("^查看CI为\"(.*)\"的视图\"(.*)\"导出配置信息$")
	public void exportDiagramConfigInfo(String ciCode, String diagramName) {
		ExportDiagramConfigInfo edci = new ExportDiagramConfigInfo();
		resultStr = edci.exportDiagramConfigInfo(diagramName, ciCode);
        assertTrue(!resultStr.isEmpty());
	}

	@Then("^查看CI为\"(.*)\"的视图成功导出配置信息$")
	public void checkExportDiagramConfigInfo(String ciCode) {
		//com.uinnova.test.step_definitions.utils.dmv.CiUtil ciUtil = new com.uinnova.test.step_definitions.utils.dmv.CiUtil();
		//JSONArray ciCodes = ciUtil.getCiCodeByClassName(className, 1, 10000);
		//for (int i = 0; i < ciCodes.length(); i++) {
			//String ciCode = (String) ciCodes.get(i);
			assertTrue(resultStr.contains(ciCode));
		//}
	}

	/* ================Scenario: My_查看视图详情信息============== */
	@When("^查看视图点击详情查看小组$")
	public void queryGroupUserInfoList() {
		QueryGroupUserInfoList qgui = new QueryGroupUserInfoList();
		result = qgui.queryGroupUserInfoList(groupIdArray);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图详情小组成功显示$")
	public void checkQueryGroupUserInfoList() {
		String groupIdstr = "";
		for (int i = 0; i < groupIdArray.length(); i++) {
			BigDecimal groupId = (BigDecimal) groupIdArray.get(i);
			if (i == 0) {
				groupIdstr = groupId.toString();
			} else {
				groupIdstr = groupIdstr + "," + groupId.toString();
			}
		}

		String sql = "SELECT ID,GROUP_NAME,GROUP_DESC FROM vc_group WHERE ID in (" + groupIdstr
				+ ") AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id+" order by ID";
		List groupList = JdbcUtil.executeQuery(sql);
		Map map = new HashMap();
		for (int k = 0; k < groupList.size(); k++) {
			HashMap temp = (HashMap) groupList.get(k);
			map.put(temp.get("ID").toString(), temp);
		}

		JSONArray data = result.getJSONArray("data");
		assertTrue(data.length() == groupList.size());
		for (int i = 0; i < data.length(); i++) {
			// HashMap groupHashMap = (HashMap) groupList.get(i);
			JSONObject dataObj = (JSONObject) data.get(i);
			JSONObject group = dataObj.getJSONObject("group");
			String groupID = String.valueOf(group.getBigDecimal("id"));

			HashMap listMap = (HashMap) map.get(groupID);
			assertEquals(group.getString("groupName"), listMap.get("GROUP_NAME"));
			assertEquals(group.getBigDecimal("id"), (BigDecimal) listMap.get("ID"));
		}

	}

	@When("^创建多个小组,小组名称分别为\"(.*)\",小组描述为\"(.*)\"的小组$")
	public void createDupGroup(@Delimiter(",") List<String> groupNameList, String groupDesc) {
		MyUtil myUtil = new MyUtil();
		groupIdArray = myUtil.createDupGroup(groupNameList, groupDesc);
	}

	@And("^将视图\"(.*)\"分享到多个小组$")
	public void shareDiagramToGroups(String diagramName) {
		ShareDiagram sd = new ShareDiagram();
		result = sd.shareDiagram(diagramName, groupIdArray);
		assertTrue(result.getBoolean("success"));
	}

	@When("^删除小组名称为\"(.*)\"的多个小组$")
	public void removeGroupIds(@Delimiter(",") List<String> groupNameList) {
		for (int i = 0; i < groupNameList.size(); i++) {
			RemoveGroupById rg = new RemoveGroupById();
			result = rg.removeGroupById(groupNameList.get(i));
			assertTrue(result.getBoolean("success"));
		}
	}
	
	@Then("^成功删除小组名称为\"(.*)\"的多个小组$")
	public void checkRemoveGroupIds(@Delimiter(",") List<String> groupNameList) {
		for (int i = 0; i < groupNameList.size(); i++) {
			Scenario_group sg = new Scenario_group();
		    sg.checkDeleteGroup(groupNameList.get(i));
		}
	}

	/* =================Scenario: My_查看视图搜索CI,关系=============== */
	@And("^为视图的CI名称为\"(.*)\",\"(.*)\"添加关系\"(.*)\"$")
	public void addDiagramRlt(String ciCode1,String ciCode2,String rltClassName) {
		MyUtil myUtil = new MyUtil();
		result = myUtil.addDiagramRlt(ciCode1, ciCode2,rltClassName);
		assertTrue(result.getBoolean("success"));
	}

	@When("^查看视图搜索CI名称为\"(.*)\",\"(.*)\",关系\"(.*)\"$")
	public void queryCiBetweenRlt(String ciCode1,String ciCode2,String rltClassName) {
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		result = queryCiBetweenRlt.queryCiBetweenRlt(rltClassName, ciCode1, ciCode2);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图成功搜索CI名称为\"(.*)\",\"(.*)\",关系\"(.*)\"$")
	public void checkQueryCiBetweenRlt(String ciCode1,String ciCode2,String rltClassName) {
		CiUtil ciUtil = new CiUtil();
		BigDecimal sourcID = ciUtil.getCiId(ciCode1);
		BigDecimal targerID = ciUtil.getCiId(ciCode2);
		String sql = "SELECT * FROM cc_ci_rlt WHERE source_ci_id IN (" + sourcID + "," + targerID
				+ ")  AND  target_ci_id IN (" + sourcID + "," + targerID + ") and DOMAIN_ID = "+ QaUtil.domain_id;
		List diagramList = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		assertEquals(diagramList.size(),data.length());
	}

	/*
	 * =========================Scenario: My_查看视图查询告警===========================
	 */
	@When("^查看ciCode为\"(.*)\"的关系为\"(.*)\"视图查询告警$")
	public void queryAlarmListByDiagramId(String ciCode,String rltClassName) {
		QueryAlarmListByDiagramId qalbd = new QueryAlarmListByDiagramId();
		result = qalbd.queryAlarmListByDiagramId(rltClassName, ciCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图CI为\"(.*)\"成功查询告警$")
	public void checkQueryAlarmListByDiagramId(String ciCode) {
		if (result.toString().contains("data")) {
			JSONArray data = result.getJSONArray("data");
			//String sql = "SELECT * FROM mon_eap_event_memory WHERE CINAME = '" + ciCode + "' and DOMAIN_ID = "+ QaUtil.domain_id;
			//未加租户
			String sql = "SELECT * FROM mon_eap_event_memory WHERE CINAME = '" + ciCode + "'";
			
			List alarmList = JdbcUtil.executeQuery(sql);
			assertEquals(alarmList.size(),data.length());
		}
	}

	/*
	 * ===========================Scenario: My_查看视图查询性能=========================
	 */
	@When("^查看视图查询ciCode为\"(.*)\"性能$")
	public void queryCurrentPerformancesByCiCodes(String ciCode) {
		QueryCurrentPerformancesByCiCodes qcpf = new QueryCurrentPerformancesByCiCodes();
		result = qcpf.queryCurrentPerformancesByCiCodes(ciCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图成功查询ciCode为\"(.*)\"性能$")
	public void checkQueryCurrentPerformancesByCiCodes(String ciCode) {
		String sql = "SELECT * FROM performance_current WHERE CI_NAME = '" + ciCode + "' and DOMAIN_ID = "+ QaUtil.domain_id;
		List sqlList = JdbcUtil.executeQuery(sql);
		if (result.toString().contains("data")) {
			JSONArray data = result.getJSONArray("data");
			if (sqlList.size() == data.length()) {
				for (int i = 0; i < data.length(); i++) {
					JSONObject dataObj = (JSONObject) data.get(i);
					JSONArray kpiArray = dataObj.getJSONArray("kpiList");
					if (kpiArray.length() == 1) {
						assertTrue(true);
					} else {
						fail();
					}
				}
			} else {
				fail();
			}
		} else {
			fail();
		}
	}

	/* ================== Scenario: My_查看视图根据CIID查询性能==================== */
	/*
	 * @When("^查看视图根据CICODE查询性能$") public void queryCurrentPerformanceByCodes(){
	 * JSONObject param = new JSONObject(); param.put("ids",
	 * Scenario_square.ciIds); QueryCurrentPerformanceByCodes qcpb = new
	 * QueryCurrentPerformanceByCodes(); result =
	 * qcpb.queryCurrentPerformanceByCodes(param);
	 * assertTrue(result.getBoolean("success"));
	 * assertEquals(result.getInt("code"),-1); }
	 */

	/* =========================My_查看视图查看设置======================== */
	@When("^查看\"(.*)\"设置$")
	public void queryThemeByName(String themeName) {
		QueryThemeByName qtbn = new QueryThemeByName();
		result = qtbn.queryThemeByName(themeName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功查看\"(.*)\"设置$")
	public void checkQueryThemeByName(String themeName) {
		JSONObject data = result.getJSONObject("data");
		if (themeName.equals(data.get("themeName"))) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ===================Scenario: My_查看视图查看标签类=================== */
	@When("^查看视图\"(.*)\"搜索标签$")
	public void queryDiagramTagListByIds(String diagramName) {
		QueryDiagramTagListByIds qd = new QueryDiagramTagListByIds();
		result = qd.queryDiagramTagListByIds(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图\"(.*)\"成功搜索标签$")
	public void checkQueryDiagramTagListByIds(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT TAG_ID FROM vc_diagram_tag Where DIAGRAM_ID = " + diagramUtil.getDiagramIdByName(diagramName) +" and DOMAIN_ID = "+ QaUtil.domain_id;
		List tagList = JdbcUtil.executeQuery(sql);
		
		if (tagList.size() == result.getJSONArray("data").length()) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/*
	 * =========================Scenario: My_查看视图查询用户类========================
	 */
	@When("^查看视图\"(.*)\"查看用户$")
	public void queryUserEditAuthByDiagramId(String diagramName) {
		QueryUserEditAuthByDiagramId quea = new QueryUserEditAuthByDiagramId();
		result = quea.queryUserEditAuthByDiagramId(diagramName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图\"(.*)\"成功查看用户$")
	public void checkQueryUserEditAuthByDiagramId(String diagramName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		String sql = "SELECT USER_ID FROM vc_diagram WHERE ID = " + diagramUtil.getDiagramIdByName(diagramName)
				+ " AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List userList = JdbcUtil.executeQuery(sql);
		HashMap userHashMap = (HashMap) userList.get(0);
		if (((BigDecimal) userHashMap.get("USER_ID")).compareTo(QaUtil.user_id)==0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ========================Scenario: My_查看视图查询CI部链接================ */
	@When("^查看视图CI外挂属性$")
	public void queryPluginAttrPage() {
		QueryPluginAttrPage queryPluginAttrPage = new QueryPluginAttrPage();
		result = queryPluginAttrPage.queryPluginAttrPage();
		assertTrue(true);
	}

	@Then("^成功查看视图CI外挂属性,分类名称为\"(.*)\"$")
	public void checkQueryPluginAttrPage(String ClassName) {
		ThemeUtil themeUtil = new ThemeUtil();
		BigDecimal classId = themeUtil.getClassId(ClassName);
		String sql = "SELECT * FROM cc_ci_plugin_attr WHERE CLASS_ID = " + classId + " AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List pluginList = JdbcUtil.executeQuery(sql);
		if (pluginList.size() == result.getJSONObject("data").getInt("totalRows")) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ==========================Scenario: My_查看视图3D查询图标===================== */
	@When("^查看视图3d查看分类\"(.*)\"图标$")
	public void queryImage3dByClassIds(String className) {
		QueryImage3dByClassIds qi = new QueryImage3dByClassIds();
		result = qi.queryImage3dByClassIds(className);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^查看视图成功查询分类\"(.*)\"图标$")
	public void checkQueryImage3dByClassIds(String className) {
		String resultstr = result.toString();
		if(resultstr.contains("data")){
		   JSONArray data = result.getJSONArray("data");
		   JSONObject obj = (JSONObject) data.get(0);
		   CiClassUtil ciClassUtil = new CiClassUtil();
		   BigDecimal classId = ciClassUtil.getCiClassId(className);
		   assertEquals(classId, obj.getBigDecimal("classId"));
		}
	}
	
	/*=======================Scenario Outline: My_收藏夹_搜索=======================*/
	@And("^将多个视图\"(.*)\"移动到收藏夹$")
	public void collectDiagramByIds(@Delimiter(",")List<String>diagramNameList){
		MyUtil myUtil = new MyUtil();
		result = myUtil.collectDiagramByIds(diagramNameList);
		assertTrue(result.getBoolean("success"));
	}
	
	@When("^在收藏夹中通过关键字\"(.*)\"来搜索视图$")
	public void queryEnshDiagramPage(String searchKey){
		QueryEnshDiagramPage queryEnshDiagramPage = new QueryEnshDiagramPage();
		result = queryEnshDiagramPage.queryEnshDiagramPage(searchKey);
		assertTrue(result.getBoolean("success"));
	}
	
    @Then("^在收藏夹中通过关键字\"(.*)\"成功搜索视图$")
    public void checkQueryEnshDiagramPage(String searchKey){
    	String  sql = "Select * FROM vc_diagram_ensh  Where DIAGRAM_ID in (SELECT ID FROM vc_diagram where name like '%"+searchKey + "%' AND STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID ="+ QaUtil.domain_id+") AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
    	List list = JdbcUtil.executeQuery(sql);
    	assertEquals(list.size(),result.getJSONObject("data").getInt("totalRows"));
    }
    
    /*=================Scenario: My_清空回收站================*/
    @When("^清空回收站$")
    public void removeDiagramByIds(){
    	RemoveDiagramByIds removeDiagramByIds = new RemoveDiagramByIds();
    	result = removeDiagramByIds.removeDiagramByIds();
    	assertTrue(result.getBoolean("success"));
    }
    
    @Then("^回收站被成功清空$")
    public void queryRecycleDiagram(){
    	QueryRecycleDiagram queryRecycleDiagram = new QueryRecycleDiagram();
    	result = queryRecycleDiagram.queryRecycleDiagram();
    	JSONObject data = result.getJSONObject("data");
    	Integer totalRows = data.getInt("totalRows");
    	assertEquals(totalRows.intValue(),0);   	
    }
    
    /*==========Scenario Outline: My_查看视图追加视图描述===========*/
   @When("^给视图\"(.*)\"追加视图描述\"(.*)\"$")
   public void updateDiagramBaseInfo(String diagramName,String diagramDesc){
	   UpdateDiagramBaseInfo updateDiagramBaseInfo = new UpdateDiagramBaseInfo();
	   result = updateDiagramBaseInfo.updateDiagramBaseInfo(diagramName, diagramDesc);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功给视图\"(.*)\"追加视图描述\"(.*)\"$")
   public void checkUpdateDiagramBaseInfo(String diagramName,String diagramDesc){
	   QueryDiagramInfoById  queryDiagramInfoById = new QueryDiagramInfoById();
	   result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
	   assertTrue(result.getBoolean("success"));
	   JSONObject param = result.getJSONObject("data");
	   JSONObject diagram = param.getJSONObject("diagram");
	   assertEquals(diagramDesc,diagram.getString("diagramDesc"));
   }
   
   /*=========Scenario: My_查看视图CI相册查询配置信息========*/
   @When("^查询CI相册CI为\"(.*)\"的配置查询信息$")
   public void queryList(@Delimiter(",") List<String> ciCodeList){
	     MyUtil  myUtil = new MyUtil();
	     result = myUtil.queryList(ciCodeList);
   }
   
   @Then("^成功查询CI相册CI为\"(.*)\"的配置查询信息$")
   public void checkQueryList(@Delimiter(",") List<String> ciCodeList){
	    String ciCode = "";
	    
	    for(int i = 0; i< ciCodeList.size(); i++){
	    	if (i == 0){
	    		ciCode = "'" + ciCodeList.get(i) + "'";
	    	}else{
	    		ciCode = ciCode + "," + "'" + ciCodeList.get(i) + "'";
	    	}
	    }
	    
	    String sql = "SELECT ID, CI_CODE,CLASS_ID FROM cc_ci WHERE CI_CODE in (" + ciCode + ") AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " ORDER BY ID";
	    List list = JdbcUtil.executeQuery(sql);
	    assertEquals(list.size(),result.getJSONArray("data").length());
	    JSONArray data = result.getJSONArray("data");
	    for(int i = 0; i < data.length(); i++){
	    	HashMap map = (HashMap)list.get(i);
	    	JSONObject obj = (JSONObject)data.get(i);
	    	JSONObject ci = obj.getJSONObject("ci");
	    	assertEquals((BigDecimal)map.get("ID"),ci.getBigDecimal("id"));
	    	assertEquals(map.get("CI_CODE"),ci.getString("ciCode"));
	    	assertEquals(map.get("CLASS_ID"),ci.getBigDecimal("classId"));
	    }
   }
   
   /*======Scenario: My_查看视图转换为系统模板==========*/
   @When("^将视图\"(.*)\"转换为系统模板$")
   public void convertDiagramAndSystemTemplate(String diagramName){
	   ConvertDiagramAndTemplate  convertDiagramAndTemplate = new ConvertDiagramAndTemplate();
	   result = convertDiagramAndTemplate.convertDiagramAndTemplate(diagramName);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^视图\"(.*)\"成功转换为系统模板$")
   public void checkConvertDiagramAndSystemTemplate(String diagramName){
	   //GetConfigInfo  getConfigInfo = new GetConfigInfo();
	   //result = getConfigInfo.getConfigInfo();
	   //JSONObject data = result.getJSONObject("data");
	   //assertEquals(data.get("haveTemplateAuth"),1);
	   QueryDiagramInfoById  queryDiagramInfoById = new QueryDiagramInfoById();
	   result = queryDiagramInfoById.queryDiagramInfoById(diagramName, true);
	   JSONObject dataObj = result.getJSONObject("data");
	   JSONObject diagram = dataObj.getJSONObject("diagram");
	   assertEquals(diagram.getInt("diagramType"),3);
   }
   
   /*==================My_查看视图告警级别从Base读取=============*/
   @When("^告警级别从Base读取$")
   public void queryEventWallCdt(){
	   QueryEventWallCdt  queryEventWallCdt = new QueryEventWallCdt();
	   result = queryEventWallCdt.queryEventWallCdt();
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功告警级别从Base读取$")
   public void checkQueryEventWallCdt(){
	   QuerySeverityList  querySeverityList = new QuerySeverityList();
	   JSONObject serverityObj = querySeverityList.querySeverityList();
	   JSONObject data = result.getJSONObject("data");
	   JSONArray severities = data.getJSONArray("severities");
	   JSONArray serverityArray = serverityObj.getJSONArray("data");
	   assertEquals(severities.length(),serverityArray.length());
	   for(int i = 0; i < severities.length(); i ++){
		   JSONObject eventObj = (JSONObject)severities.get(i);
		   JSONObject serverityObj11 = (JSONObject)serverityArray.get(i);
		   assertEquals(eventObj.getString("chineseName"),serverityObj11.getString("chineseName"));
		   assertEquals(eventObj.getString("color"),serverityObj11.getString("color"));
		   assertEquals(eventObj.getString("englishName"),serverityObj11.getString("englishName"));
		   assertEquals(eventObj.getInt("severity"),serverityObj11.getInt("severity"));
	   }
   }
   
   /*===================Scenario: My_在我的视图内帅选模板视图=============*/
   @When("^帅选文件夹为\"(.*)\"模板视图$")
   public void queryMyDirInfoPageByParentId(String dirName) throws InterruptedException{
	   MyUtil myUtil = new MyUtil();
	   result = myUtil.queryMyDirInfoPageByParentId(dirName,"",4);
	   assertTrue(result.getBoolean("success"));
   }
   
   @Then("^成功帅选文件夹为\"(.*)\"模板视图$")
   public void checkQueryMyDirInfoPageByParentId(String dirName){
	   JSONObject data = result.getJSONObject("data");
	   MyUtil myUtil = new MyUtil();
	   BigDecimal dirId = myUtil.getDirIdByName(dirName);
	   JSONObject diagramInfoPage = data.getJSONObject("diagramInfoPage");
	   Integer totalRows = diagramInfoPage.getInt("totalRows");
	   String sql = "SELECT ID,NAME FROM vc_diagram WHERE STATUS = 1" 
				+ " AND DIAGRAM_TYPE = 3 AND DATA_STATUS =1 AND USER_ID = " + QaUtil.user_id + " AND DOMAIN_ID = "+ QaUtil.domain_id + " AND DIR_ID =" + dirId;
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),totalRows.intValue());	
   }
}