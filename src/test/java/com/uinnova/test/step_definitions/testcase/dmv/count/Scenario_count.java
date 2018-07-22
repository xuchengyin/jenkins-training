package com.uinnova.test.step_definitions.testcase.dmv.count;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.count.CountDiagramInfo;
import com.uinnova.test.step_definitions.api.dmv.count.CountGroupDiagramInfo;
import com.uinnova.test.step_definitions.api.dmv.count.CountTotalDiagram;
import com.uinnova.test.step_definitions.api.dmv.count.CountUserDiagramInfo;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-03-21
 * 编写人:sunsl
 * 功能介绍:DMV统计视图数量测试类
 */
public class Scenario_count {
	private JSONObject result;
	
//	@When("^统计视图总数$")
//	public void countTotalDiagram(){
//		CountTotalDiagram countTotalDiagram = new CountTotalDiagram();
//		result = countTotalDiagram.countTotalDiagram();
//		assertTrue(result.getBoolean("success"));
//	}
//	
//	@Then("^成功统计视图总数$")
//	public void checkCountTotalDiagram(){
//		String sql = "select count(1) As COUNT from vc_diagram where DATA_STATUS = 1 and DOMAIN_ID = " + QaUtil.domain_id + " and DIAGRAM_TYPE in (1,3)";
//
//		List list = JdbcUtil.executeQuery(sql);
//		JSONObject data = result.getJSONObject("data");
//		if(list != null && list.size() > 0){
//			HashMap map = (HashMap)list.get(0);
//			Long totalDiagram = data.getLong("totalDiagram");
//			String totalDiagramStr = totalDiagram.toString();
//			//Long count = (Long)map.get("COUNT");
//			//String countStr = count.toString();
//			assertEquals(map.get("COUNT").toString(),totalDiagramStr);
//		}else{
//			fail();
//		}
//		String sql1 = "SELECT count(1) AS COUNT from vc_diagram where DATA_STATUS = 1 and IS_OPEN = 1 and STATUS = 1 and DOMAIN_ID = " + QaUtil.domain_id;
//		List list1 = JdbcUtil.executeQuery(sql1);
//		if(list1 != null && list1.size() > 0){
//			HashMap map = (HashMap)list1.get(0);
//			Long totalOpenDiagram = data.getLong("totalOpenDiagram");
//			String totalOpenDiagramStr = totalOpenDiagram.toString();
//			//Long count = (Long)map.get("COUNT");
//			//String countStr = count.toString();
//			assertEquals(map.get("COUNT").toString(),totalOpenDiagramStr);
//		}else{
//			fail();
//		}
//	}
//	
//	  @When("^统计小组视图总数量$")
//	  public void countGroupDiagramInfo(){
//		  CountGroupDiagramInfo  countGroupDiagramInfo = new CountGroupDiagramInfo();
//		  result = countGroupDiagramInfo.countGroupDiagramInfo();
//		  assertTrue(result.getBoolean("success"));
//	 }
//	  
//	 @Then("^成功统计小组视图总数量$")
//	 public void checkCountGroupDiagramInfo(){
//		 String sql ="select A.ID ID,A.GROUP_NAME GROUP_NAME,count(C.ID) DIAGRAM_COUNT,sum(C.read_count) DIAGRAM_READ_COUNT from VC_GROUP A   LEFT JOIN VC_DIAGRAM_GROUP B on A.ID = B.GROUP_ID and B.DOMAIN_ID = " + QaUtil.domain_id +" LEFT JOIN VC_DIAGRAM C on C.ID = B.DIAGRAM_ID and C.DATA_STATUS = 1 and C.DOMAIN_ID = " + QaUtil.domain_id + " and C.STATUS = 1  WHERE A.DATA_STATUS = 1 and A.DOMAIN_ID = " + QaUtil.domain_id + " GROUP BY A.ID,A.group_name  ORDER BY DIAGRAM_COUNT DESC";
//		 List list = JdbcUtil.executeQuery(sql);
//		 JSONArray data = result.getJSONArray("data");
//		 if(list != null && list.size() > 0){
//			 assertEquals(list.size(), data.length());
//			 for(int i = 0; i < data.length(); i ++){
//				 JSONObject obj = (JSONObject)data.get(i);
//				 HashMap map = (HashMap)list.get(i);
//				 assertEquals((BigDecimal)map.get("ID"),obj.getBigDecimal("id"));
//				 assertEquals((String)map.get("GROUP_NAME"),obj.getString("groupName"));
//				 Long diagramCount = obj.getLong("diagramCount");
//				 Long diagramReadCount = obj.getLong("diagramReadCount");
//				 String diagramCountStr = diagramCount.toString();
//                 String diagramReadCountStr = diagramReadCount.toString();
//                 if(map.get("DIAGRAM_COUNT") != null){
//				    assertEquals((map.get("DIAGRAM_COUNT")).toString(),diagramCountStr);
//                 }
//		
//				 if(map.get("DIAGRAM_READ_COUNT") != null){
//				    assertEquals((map.get("DIAGRAM_READ_COUNT")).toString(),diagramReadCountStr);
//				 }
//			 }
//		 }
//	 }
	 
	 @When("^统计视图分析数量$")
	 public void countDiagramInfo(){
		 CountDiagramInfo countDiagramInfo = new CountDiagramInfo();
		 result = countDiagramInfo.countDiagramInfo();
         assertTrue(result.getBoolean("success"));
	 }
	 
	 @Then("^成功统计视图分析数量$")
	 public void checkCountDiagramInfo(){
		 String sql ="select A.ID ID, A.NAME DIAGRAM_NAME,A.MODIFY_TIME MODIFY_TIME,A.READ_COUNT READ_COUNT,count(B.ID) ENSH_COUNT from VC_DIAGRAM A     LEFT JOIN VC_DIAGRAM_ENSH B on A.ID = B.DIAGRAM_ID and B.DOMAIN_ID = " + QaUtil.domain_id + "  where A.DOMAIN_ID = " + QaUtil.domain_id + " and A.DATA_STATUS = 1 and A.STATUS = 1      GROUP by A.ID,A.NAME,A.MODIFY_TIME,A.READ_COUNT ORDER BY   READ_COUNT DESC";
		 List list = JdbcUtil.executeQuery(sql);
		 JSONArray data = new JSONArray();
		 if(result.toString().contains("data")){
		     data = result.getJSONArray("data");
		 }
		 HashMap putMap = new HashMap();
		 if(list != null && list.size() > 0){
			 for(int i = 0; i< list.size(); i ++){
				 HashMap map = (HashMap)list.get(i);
				 putMap.put(map.get("ID"), map);
			 }
		 }
		 
		 if(list != null && list.size() > 0){
			 assertEquals(list.size(), data.length());
			 for(int i = 0; i< data.length(); i ++){
					JSONObject obj = (JSONObject)data.get(i);
					HashMap map = (HashMap)putMap.get(obj.getBigDecimal("id"));
				    assertEquals((BigDecimal)map.get("ID"),obj.getBigDecimal("id"));
				    assertEquals((String)map.get("DIAGRAM_NAME"),obj.getString("diagramName"));
				    assertEquals((BigDecimal)map.get("MODIFY_TIME"),obj.getBigDecimal("modifyTime"));
				    Long readCount = obj.getLong("readCount");
					 Long enshCount = obj.getLong("enshCount");
					 if(map.get("READ_COUNT") != null){
					   assertEquals((map.get("READ_COUNT")).toString(),readCount.toString());
					 }
					 if(map.get("ENSH_COUNT") != null){
					    assertEquals((map.get("ENSH_COUNT")).toString(),enshCount.toString());
					 }				
			 }
		 }
	 }
	 
	 @When("^统计用户分析数量$")
	 public void countUserDiagramInfo(){
		 CountUserDiagramInfo countUserDiagramInfo = new CountUserDiagramInfo();
		 result = countUserDiagramInfo.countUserDiagramInfo();
		 assertTrue(result.getBoolean("success"));
	 }
	 
	 @Then("^成功统计用户分析数量$")
	 public void checkCountUserDiagramInfo(){
		 String sql = "select A.ID ID,A.OP_CODE OP_CODE,A.OP_NAME OP_NAME,count(B.ID) CREATE_COUNT,count(C.ID) SHARE_COUNT from sys_op A    LEFT JOIN VC_DIAGRAM B on A.id = B.USER_ID and B.DATA_STATUS = 1 and B.STATUS = 1 and B.DOMAIN_ID = " + QaUtil.domain_id + " LEFT JOIN VC_DIAGRAM_GROUP C on A.ID = C.DEPLOY_USER_ID  and C.domain_id = " + QaUtil.domain_id + " and B.ID = C.DIAGRAM_ID where A.DATA_STATUS = 1 and A.STATUS = 1 and A.USER_DOMAIN_ID = "+ QaUtil.domain_id +" GROUP BY A.ID,A.OP_CODE,A.OP_NAME ORDER BY  CREATE_COUNT DESC";
		 List list = JdbcUtil.executeQuery(sql);
		 JSONArray data = result.getJSONArray("data");
		 HashMap putMap = new HashMap();
		 if(list != null && list.size() > 0){
			 for(int i = 0; i < list.size(); i++){
				 HashMap map = (HashMap)list.get(i);
				 putMap.put(map.get("ID"), map);
			 }
		 }
		 if(list != null && list.size() > 0){
			 assertEquals(list.size(),data.length());
			 for(int i = 0; i < data.length(); i ++){
				 JSONObject obj = (JSONObject)data.get(i);
				 HashMap map = (HashMap)putMap.get(obj.getBigDecimal("id"));
				 assertEquals((BigDecimal)map.get("ID"),obj.getBigDecimal("id"));
				 assertEquals((String)map.get("OP_CODE"),obj.getString("opCode"));
				 assertEquals((String)map.get("OP_NAME"),obj.getString("opName"));
				 Long createCount = obj.getLong("createCount");
				 Long shareCount = obj.getLong("shareCount");
				 if(map.get("CREATE_COUNT") != null){
				    assertEquals((map.get("CREATE_COUNT")).toString(),createCount.toString());
				 }
				 if(map.get("SHARE_COUNT") != null){
					 assertEquals((map.get("SHARE_COUNT")).toString(),shareCount.toString());
				 }
			 }
		 }
	 }
}
