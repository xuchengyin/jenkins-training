package com.uinnova.test.step_definitions.testcase.dmv.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramCount;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramTemplate;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDigramEventByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryOpenDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryOpenDiagramInfoByQType;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveDiagramTagBatch;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.ShareDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.UpdateDiagramContent;
import com.uinnova.test.step_definitions.api.dmv.group.QueryGroupDiagramCount;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;
import com.uinnova.test.step_definitions.utils.dmv.SquareUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-15 编写人:sunsl 功能介绍:DMV广场测试用例类
 */
public class Scenario_square {
	JSONObject result,searchNameResult;
	/* ===============Scenario Outline: Square_新建视图============= */
	@When("^利用模板\"(.*)\"创建名称为\"(.*)\",视图描述为\"(.*)\"的视图$")
	public void createDiagram(String TelmplateDiagramName,String name, String diagramDesc) {
		// 取得视图模板
		SaveOrUpdateDiagram su = new SaveOrUpdateDiagram();
		result = su.saveOrUpdateDiagram(TelmplateDiagramName,name,diagramDesc);
		assertTrue(result.getBoolean("success"));
	}

	@And("^将视图\"(.*)\"发布到广场$")
	public void shareDiagram(String name) {
		ShareDiagram sd = new ShareDiagram();
		result = sd.shareDiagram(name);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^系统中存在名称为\"(.*)\",视图描述为\"(.*)\"的视图$")
	public void checkCreateDiagram(String name, String diagramDesc) {
		QueryDiagramTemplate qdt = new QueryDiagramTemplate();
		result = qdt.queryDiagramTemplate();
		JSONArray qdtData = result.getJSONArray("data");
		JSONObject square = qdtData.getJSONObject(0);
		String qdtCi3dPoint = square.getString("ci3dPoint");
		String qdtXml = square.getString("xml");
		// 取得单个视图
		QueryDiagramInfoById qd = new QueryDiagramInfoById();
		result = qd.queryDiagramInfoById(name, true);
		JSONObject data = result.getJSONObject("data");
		JSONObject diagram = data.getJSONObject("diagram");
		String ci3dPoint = data.getString("ci3dPoint");
		String xml = data.getString("xml");
		String diagramName = diagram.getString("name");
		String diagramDiagramDesc = "";
		if (diagram.has("diagramDesc"))
			diagramDiagramDesc = diagram.getString("diagramDesc");
		if (name.equals(diagramName) && diagramDesc.equals(diagramDiagramDesc)
				&& ci3dPoint.equals(qdtCi3dPoint)&& xml.equals(qdtXml)) {
			assertTrue(true);
		} else {
			fail();
		}

	}

	@Then("^删除视图\"(.*)\"$")
	public void deleteDiagram(String name) {
		RemoveDirByIds rd = new RemoveDirByIds();
		rd.removeDirByIds(name);
	}

	/* =================== Scenario Outline:Square_名称搜索视图============ */
	@When("^搜索名称包含\"(.*)\"的视图$")
	public void searchDiagramName(String searchKey) {
		QueryOpenDiagramInfoByQType qod = new QueryOpenDiagramInfoByQType();
		QueryDigramEventByIds qd = new QueryDigramEventByIds();
		searchNameResult = qod.queryOpenDiagramInfoByQType(searchKey,1);
		//result = qd.queryDigramEventByIds(searchNameResult);
		assertTrue(searchNameResult.getBoolean("success"));
	}

	@Then("^包含\"(.*)\"关键字的的视图全部搜索出来$")
	public void checkSearchDiagramName(String searchKey) {
		String ids = "";
		String sql = "SELECT ID,NAME,DIAGRAM_DESC FROM vc_diagram WHERE name like '%" + searchKey
				+ "%' AND STATUS =1 AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id+" ORDER BY OPEN_TIME DESC";
		ArrayList list = JdbcUtil.executeQuery(sql);
		if (list.size() == searchNameResult.getJSONObject("data").getInt("totalRows")) {
			assertTrue(true);
			//JSONArray dataEvent = searchNameResult.getJSONObject("data").getJSONArray("data");
			//Integer eventCount = 0;
			//			for (int i = 0; i < dataEvent.length(); i++) {
			//				JSONObject eventObj = (JSONObject) dataEvent.get(i);
			//				JSONArray diagramEles = eventObj.getJSONArray("diagramEles");
			//				JSONObject diagramObj= (JSONObject)diagramEles.get(0);
			//				BigDecimal diagramId = diagramObj.getBigDecimal("diagramId");
			//eventCount = eventObj.getInt("totalCount");
			//				String enevntSql = "SELECT * FROM `mon_eap_event_memory WHERE CINAME IN (SELECT ci_code FROM cc_ci WHERE id IN ( SELECT ele_id FROM vc_diagram_ele WHERE diagram_id ='"
			//						+ String.valueOf(diagramId) + "'= AND ele_type = 1 and DOMAIN_ID = "+ QaUtil.domain_id+"))  and DOMAIN_ID = "+ QaUtil.domain_id;
			//				ArrayList eventList = JdbcUtil.executeQuery(enevntSql);
			//if (eventList.size() == eventCount) {

			//} else {
			//fail();
			//}
			//}
		}

		// 查询视图的告警
		// String enevntSql ="SELECT * FROM `mon_eap_event_memory WHERE CINAME
		// IN (SELECT ci_code FROM cc_ci WHERE id IN ( SELECT ele_id FROM
		// vc_diagram_ele WHERE diagram_id IN ("+ ids +") AND ele_type = 1))";
	}

	/* ======== Scenario Outline:Square_标签搜索视图======== */
	@And("^给视图\"(.*)\"新建名称为\"(.*)\"的标签$")
	public void createDiagramTag(String name,String tagName) {
		SaveDiagramTagBatch sdtb = new SaveDiagramTagBatch();
		result = sdtb.saveDiagramTagBatch(name,tagName);
		assertTrue(result.getBoolean("success"));
	}

	@When("^搜索包含\"(.*)\"标签的视图$")
	public void searchDiagramTagName(String searchKey) {
		QueryOpenDiagramInfoByQType qd = new QueryOpenDiagramInfoByQType();
		result = qd.queryOpenDiagramInfoByQType(searchKey,3);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^包含\"(.*)\"标签关键字的的视图全部搜索出来$")
	public void checkSearchDiagramTagName(String searchKey) {
		String sql = "SELECT* FROM vc_diagram where ID in (SELECT  DIAGRAM_ID FROM vc_diagram_tag  where TAG_ID in (select ID FROM vc_tag where TAG_NAME like '%"
				+ searchKey + "%' and DOMAIN_ID = "+ QaUtil.domain_id+") ) AND STATUS = 1 AND is_open=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		if (list.size() == result.getJSONObject("data").getInt("totalRows")) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* =================Scenario: Square_作者搜索视图================= */
	@When("^搜索作者是\"(.*)\"的视图$")
	public void searchDiagramUser(String searchKey) {
		QueryOpenDiagramInfoByQType qod = new QueryOpenDiagramInfoByQType();
		result = qod.queryOpenDiagramInfoByQType(searchKey,2);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^是\"(.*)\"的作者的的视图全部搜索出来$")
	public void checkSearchDiagramUser(String searchKey) {
		String sql = "SELECT d.USER_ID USER_ID,o.OP_NAME USER_NAME,COUNT(d.id) COUNT   from     VC_DIAGRAM  d   INNER JOIN SYS_OP o ON o.ID = d.USER_ID  and o.USER_DOMAIN_ID = "+ QaUtil.domain_id+" AND o.OP_CODE like '%" 
				+ searchKey + "%' WHERE d.DATA_STATUS = 1 AND d.STATUS = 1 AND d.IS_OPEN = 1    AND o.DATA_STATUS = 1  and d.DOMAIN_ID = "+ QaUtil.domain_id+"  GROUP BY d.USER_ID,o.OP_NAME   ORDER BY   o.OP_NAME ";
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap diagramUserHashMap = (HashMap) list.get(0);
		Long totalRows = result.getJSONObject("data").getLong("totalRows");
		assertEquals(diagramUserHashMap.get("COUNT").toString(), totalRows.toString());

	}

	@And("^为视图\"(.*)\"添加分类为\"(.*)\"的CI$")
	public void addDiagramCi(String name,String className) {
		QueryPageByIndex qp = new QueryPageByIndex();
		JSONObject ciResult = qp.queryPageByIndex(className, 1);
		UpdateDiagramContent updateDiagramContent = new UpdateDiagramContent();
		HashMap resultHashMap = updateDiagramContent.updateDiagramContentAddCI(ciResult);
		QueryDiagramInfoById qd = new QueryDiagramInfoById();
		result = qd.queryDiagramInfoById(name, true);
		result = updateDiagramContent.updateDiagramContent(name,result,(JSONArray)resultHashMap.get("diagramEles"));
		assertTrue(result.getBoolean("success"));
	}
	/*==================Scenario: Square_CI搜索视图=====================*/
	@When("^通过CI\"(.*)\"搜索视图$")
	public void searchDiagramCI(String ciCode) throws Exception {
		Thread.sleep(5000);
		QueryOpenDiagramInfoByQType qo = new QueryOpenDiagramInfoByQType();
		result = qo.queryOpenDiagramInfoByQType(ciCode,4,0);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^CI\"(.*)\"成功搜索视图$")
	public void checkSearchDiagramCI(String ciCode) {
		String sql = "SELECT ID,NAME FROM vc_diagram WHERE ID in (SELECT DISTINCT b.DIAGRAM_ID as DIAGRAM_ID from VC_DIAGRAM_CI b   INNER JOIN CC_CI_PRO_INDEX a ON a.ID = b.CI_ID  and a.IDX_0 like '%"
				+ ciCode + "%' INNER JOIN CC_CI ci ON ci.id = a.ID  AND ci.domain_id ="+ QaUtil.domain_id+" AND ci.DATA_STATUS = 1  WHERE b.domain_id = "+ QaUtil.domain_id+") AND IS_OPEN=1 AND STATUS=1 AND DATA_STATUS = 1";
		List diagramList = JdbcUtil.executeQuery(sql);
		assertEquals(diagramList.size(),result.getJSONObject("data").getInt("totalRows"));
	}

	/* ============Square_根据已有作者搜索视图================ */
	@When("^根据已有作者搜索视图$")
	public void searchDiagramUser2() {
		SquareUtil squareUtil = new SquareUtil();
		BigDecimal userId = squareUtil.getUserId();
		QueryOpenDiagram qo = new QueryOpenDiagram();
		JSONObject cdt = new JSONObject();
		BigDecimal[] userIds = { userId };
		cdt.put("userIds", userIds);
		result = qo.queryOpenDiagram(cdt);
		assertTrue(result.getBoolean("success"));	
	}
	@Then("^已有作者视图全部搜索出来$")
	public void checkSearchDiagramUser2() {
		SquareUtil squareUtil = new SquareUtil();
		BigDecimal userId = squareUtil.getUserId();
		JSONObject data = result.getJSONObject("data");
		Integer totalRows = data.getInt("totalRows");
		String sql = "SELECT ID from VC_DIAGRAM where IS_OPEN =1 and STATUS = 1  and DATA_STATUS = 1 and  USER_ID in (" + userId + ") and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertEquals(list.size(),totalRows.intValue());
	}

	/* ======================Square_已有标签搜索视图=============== */
	@When("^搜索已有标签的视图$")
	public void searchDiagramTagName2() {
		SquareUtil squareUtil = new SquareUtil();
		BigDecimal tagId = squareUtil.getTagId();
		JSONObject cdt = new JSONObject();
		BigDecimal[] tagIds = { tagId };
		cdt.put("tagIds", tagIds);
		QueryOpenDiagram qod = new QueryOpenDiagram();
		result = qod.queryOpenDiagram(cdt);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^已有标签的的视图全部搜索出来$")
	public void checksearchDiagramTagName2() {
		SquareUtil squareUtil = new SquareUtil();
		BigDecimal tagId = squareUtil.getTagId();
		JSONObject data = result.getJSONObject("data");
		Integer totalRows = data.getInt("totalRows");
		String sql = "SELECT ID from VC_DIAGRAM where is_open=1 and status = 1 and DATA_STATUS = 1  and  id in (select DIAGRAM_ID from VC_DIAGRAM_TAG where TAG_ID in ("
				+ tagId + ")  and DOMAIN_ID = "+ QaUtil.domain_id+" ) order by MODIFY_TIME DESC";
		ArrayList list = JdbcUtil.executeQuery(sql);
		if(list.size() == totalRows){
			assertTrue(true);
		}else{
			fail();
		}
	}

	/*========================= Scenario: Square_查询小组的视图数=================================*/
	@And("^将视图\"(.*)\"分享到小组\"(.*)\"$")
	public void shareDiagramToGroup(String diagramName,String groupName){
		DiagramUtil diagramUtil = new DiagramUtil();
		ShareDiagram sd = new ShareDiagram();
		result = sd.shareDiagram(diagramName,groupName);
		assertTrue(result.getBoolean("success"));
	}
	
	@And("^将多个视图\"(.*)\"分享到小组\"(.*)\"$")
	public void shareDiagramToGroupDumpli(@Delimiter(",")List<String> diagramNameList,String groupName){
		DiagramUtil diagramUtil = new DiagramUtil();
		ShareDiagram sd = new ShareDiagram();
		for(int i = 0; i < diagramNameList.size();i++){
		   String diagramName = diagramNameList.get(i);
		   result = sd.shareDiagram(diagramName,groupName);
		   assertTrue(result.getBoolean("success"));
		}
	}

	@When("^查询小组的视图数$")
	public void queryGroupDiagramCount(){
		QueryGroupDiagramCount qgdc = new QueryGroupDiagramCount();
		result = qgdc.queryGroupDiagramCount();
		assertTrue(true);
	}

	@Then("^小组\"(.*)\"的视图数正确查询出来$")
	public void checkQueryGroupDiagramCount(String groupName){
		JSONArray data = result.getJSONArray("data");
		GroupUtil groupUtil = new GroupUtil();

		Integer diagramCount=0; 
		BigDecimal groupId = new BigDecimal(0);
		for(int i = 0; i < data.length();i++){
			JSONObject obj = (JSONObject)data.get(i);
			JSONObject group = obj.getJSONObject("group");
			groupId = group.getBigDecimal("id");
			if(groupId.compareTo(groupUtil.getGroupId(groupName))==0){
				diagramCount = obj.getInt("diagramCount");
				break;
			}
		}

		String sql = "SELECT DIAGRAM_ID FROM vc_diagram_group WHERE GROUP_ID= " + groupId+" and DOMAIN_ID = "+ QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		if(list.size() == diagramCount){
			assertTrue(true);
		}else{
			fail();
		}
	}
	
	/*===========Scenario: Square_帅选视图默认值===============*/
	@When("^在广场里默认值帅选视图$")
	public void queryOpenDiagramInfoByQType0(){
		QueryOpenDiagramInfoByQType  queryOpenDiagramInfoByQType = new QueryOpenDiagramInfoByQType();
		result = queryOpenDiagramInfoByQType.queryOpenDiagramInfoByQType("", 1, 0);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^广场里的视图全部被帅选出来$")
	public void checkQueryOpenDiagramInfoByQType0(){
		String sql = "SELECT ID From VC_DIAGRAM where IS_OPEN = 1 AND STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		Long totalRows = result.getJSONObject("data").getLong("totalRows");
		assertEquals(new Integer(list.size()).toString(),totalRows.toString());
	}
	
	/*================Scenario: Square_单图帅选视图==========*/
	@When("^在广场里帅选单图视图$")
	public void queryOpenDiagramInfoByQType1(){
		QueryOpenDiagramInfoByQType queryOpenDiagramInfoByQType = new QueryOpenDiagramInfoByQType();
		result = queryOpenDiagramInfoByQType.queryOpenDiagramInfoByQType("", 1, 1);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^广场里的单图全部被帅选出来$")
	public void checkQueryOpenDiagramInfoByQType1(){
		String sql = "SELECT ID FROM VC_DIAGRAM where IS_OPEN = 1 AND DIAGRAM_TYPE = 1 AND STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		Long totalRows = result.getJSONObject("data").getLong("totalRows");
		assertEquals(new Integer(list.size()).toString(),totalRows.toString());
	}
	
	/*=====================Square_组合视图帅选视图============*/
	@When("^在广场里帅选组合视图$")
	public void queryOpenDiagramInfoByQType2(){
		QueryOpenDiagramInfoByQType queryOpenDiagramInfoByQType = new QueryOpenDiagramInfoByQType();
		result = queryOpenDiagramInfoByQType.queryOpenDiagramInfoByQType("", 1, 2);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^广场里的组合视图全部被帅选出来$")
	public void checkQueryOpenDiagramInfoByQType2(){
		String sql = "SELECT ID FROM VC_DIAGRAM where IS_OPEN = 1 AND DIAGRAM_TYPE = 2 AND STATUS = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		Long totalRows = result.getJSONObject("data").getLong("totalRows");
		assertEquals(new Integer(list.size()).toString(),totalRows.toString());
	}
	
	/*==============Square_收藏视图帅选视图==================*/
	@When("^在广场里帅选收藏的视图$")
	public void queryOpenDiagramInfoByQType3(){
		QueryOpenDiagramInfoByQType queryOpenDiagramInfoByQType = new QueryOpenDiagramInfoByQType();
		result = queryOpenDiagramInfoByQType.queryOpenDiagramInfoByQType("", 1, 3);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^广场里的收藏的视图全部被帅选出来$")
	public void checkQueryOpenDiagramInfoByQType3(){
		String sql = "SELECT ID FROM VC_DIAGRAM WHERE ID in (SELECT DIAGRAM_ID FROM VC_DIAGRAM_ENSH WHERE  USER_ID = "+ QaUtil.user_id+" AND DOMAIN_ID = " + QaUtil.domain_id + " AND DATA_STATUS = 1) AND IS_OPEN = 1 AND DOMAIN_ID = " + QaUtil.domain_id + " AND STATUS = 1 AND DATA_STATUS =1";
		List list = JdbcUtil.executeQuery(sql);
		Long totalRows = result.getJSONObject("data").getLong("totalRows");
		assertEquals(new Integer(list.size()).toString(),totalRows.toString());
	}
	
	/*=================Square_广场_查询视图数===============*/
	@When("^查询广场的视图数$")
	public void queryDiagramCount(){
		QueryDiagramCount  queryDiagramCount = new QueryDiagramCount();
		result = queryDiagramCount.queryDiagramCount();
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^广场的视图数成功被查询$")
	public void checkQueryDiagramCount(){
		String sql ="SELECT COUNT(*) AS CNT FROM vc_diagram where (DIAGRAM_TYPE =1 or DIAGRAM_TYPE =3) AND DATA_STATUS =1 AND DOMAIN_ID =" +QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		if(list.size()>0){
		   map = (HashMap)list.get(0);
		}
		String shareSql = "SELECT ID FROM vc_diagram WHERE DIAGRAM_TYPE =1 AND IS_OPEN = 1 AND STATUS =1 AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		List shareList = JdbcUtil.executeQuery(shareSql);
		String groupSql = "SELECT ID FROM vc_diagram where ID in (SELECT DISTINCT DIAGRAM_ID FROM vc_diagram_group) AND DIAGRAM_TYPE =1 AND STATUS = 1 AND DATA_STATUS =1 AND DOMAIN_ID =" + QaUtil.domain_id;
		List groupList = JdbcUtil.executeQuery(groupSql);
		JSONObject data = result.getJSONObject("data");
		int diagramCount = data.getInt("diagramCount");
		int sharedDiagramCount = data.getInt("sharedDiagramCount");
		int groupDiagramCount = data.getInt("groupDiagramCount");
		assertEquals(map.get("CNT").toString(),new Integer(diagramCount).toString());
		assertEquals(shareList.size(),sharedDiagramCount);
		assertEquals(groupList.size(),groupDiagramCount);
	}
}
