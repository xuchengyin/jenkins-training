package com.uinnova.test.step_definitions.testcase.dmv.group;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramByGroupId;
import com.uinnova.test.step_definitions.api.dmv.group.AddImage;
import com.uinnova.test.step_definitions.api.dmv.group.QueryGroupInfoList;
import com.uinnova.test.step_definitions.api.dmv.group.QueryGroupLogPage;
import com.uinnova.test.step_definitions.api.dmv.group.RemoveGroupById;
import com.uinnova.test.step_definitions.api.dmv.group.SaveOrUpdateGroupInfo;
import com.uinnova.test.step_definitions.api.dmv.group.TransfeGroupOwner;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

/**
 * 编写时间:2017-11-22
 * 编写人:sunsl
 * 功能介绍:DMV小组的测试用例
 */
public class Scenario_group {
	JSONObject result;
	public static BigDecimal groupId;
	String beforeCreator;
	private List<String> groupList = new ArrayList<String>(); //用于记录一共新建了多少小组， 用户After方法清理数据

	@After("@DelDmvGroup")
	public void delGroup(){
		if (!groupList.isEmpty()){
			for (int i=0; i<groupList.size(); i++){
				String groupName = groupList.get(i);
				RemoveGroupById rg = new RemoveGroupById();
				result = rg.removeGroupById(groupName);
				assertTrue(result.getBoolean("success"));
				groupList.remove(groupName);
				i--;
			}
		}
	}
	

	/*=========== Scenario Outline: Group_小组增删改===========*/
	@When("^创建小组名称为\"(.*)\",小组描述为\"(.*)\"的小组$")
	public void createGroup(String groupName,String groupDesc){
		String filePath = Scenario_group.class.getResource("/").getPath() +"testData/dmv/group/rttt.jpg";
		AddImage  addImage= new AddImage();
		result = addImage.addImage(filePath);
		String data = result.getString("data");
		SaveOrUpdateGroupInfo  su = new SaveOrUpdateGroupInfo();
		result = su.saveOrUpdateGroupInfo(groupName,"",groupDesc,data,0);
		assertTrue(result.getBoolean("success"));
		groupList.add(groupName);
	}

	@Then("^系统中存在小组名称为\"(.*)\",小组描述为\"(.*)\"的小组$")
	public void checkCreateGroup(String groupName,String groupDesc){
		String sql = "SELECT ID,GROUP_NAME,GROUP_DESC FROM vc_group WHERE GROUP_NAME = '" + groupName +"' AND GROUP_DESC = '"+groupDesc+"' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);

		if(list.size()==1){
			HashMap groupMap = (HashMap)list.get(0);
			groupId = (BigDecimal)groupMap.get("ID");
			assertTrue(true);
		}else{
			fail();
		}
	}

	@When("^小组名称为\"(.*)\"的小组修改小组名称为\"(.*)\",小组描述为\"(.*)\"$")
	public void updateGroup(String groupName,String updateGroupName,String updateGroupDesc){
		SaveOrUpdateGroupInfo  su = new SaveOrUpdateGroupInfo();
		result = su.saveOrUpdateGroupInfo(groupName,updateGroupName,updateGroupDesc,"",1);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^小组名称为\"(.*)\",小组描述为\"(.*)\"的小组修改成功$")
	public void checkUpdateGroup(String updateGroupName,String updateGroupDesc){
		GroupUtil groupUtil = new GroupUtil();

		String sql = "SELECT ID,GROUP_NAME,GROUP_DESC FROM vc_group WHERE ID = " + groupUtil.getGroupId(updateGroupName) + " AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List groupList = JdbcUtil.executeQuery(sql);
		if(groupList.size()==1){
			HashMap groupMap = (HashMap)groupList.get(0);
			String dbGroupName = groupMap.get("GROUP_NAME").toString();
			String dbGroupDesc = groupMap.get("GROUP_DESC").toString();
			if(updateGroupName.equals(dbGroupName) && updateGroupDesc.equals(dbGroupDesc)){
				assertTrue(true);
			}else{
				fail();
			}
		}
	}

	@When("^删除小组名称为\"(.*)\"的小组$")
	public void deleteGroup(String updateGroupName){
		RemoveGroupById rg = new RemoveGroupById();
		result = rg.removeGroupById(updateGroupName);
		assertTrue(result.getBoolean("success"));
		groupList.remove(updateGroupName);
	}

	@Then("^系统中不存在小组名称为\"(.*)\"的小组$")
	public void checkDeleteGroup(String updateGroupName){
		GroupUtil groupUtil = new GroupUtil();
		String sql = "SELECT ID  FROM vc_group WHERE ID = " + groupUtil.getGroupId(updateGroupName) + " AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List groupList = JdbcUtil.executeQuery(sql);
		if(groupList.size() ==0){
			assertTrue(true);
		}else{
			fail();
		}
	}

	/*================Scenario: Square_查看小组=============*/
	@When("^查看小组小组名称为\"(.*)\"$")
	public void queryDiagramByGroupId(String groupName){
		QueryDiagramByGroupId  qd = new QueryDiagramByGroupId();
		result = qd.queryDiagramByGroupId(groupName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^小组\"(.*)\"的视图查询出来$")
	public void checkQueryDiagramByGroupId(String groupName){
		GroupUtil groupUtil = new GroupUtil();
		String sql = "SELECT DIAGRAM_ID FROM vc_diagram_group WHERE GROUP_ID = " + groupUtil.getGroupId(groupName) +" and DOMAIN_ID = "+ QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		if (list.size()==data.length()){
			assertTrue(true);
		}else{
			fail();
		}
	}

	/*============Scenario: Group_转让所有权,动态日志=============*/
	@When("^小组\"(.*)\"转让所有权$")
	public void transfeGroupOwner(String groupName){
		GroupUtil groupUtil = new GroupUtil();
		String sql = "SELECT CREATOR FROM vc_group WHERE ID = " + groupUtil.getGroupId(groupName) +" and DOMAIN_ID = "+ QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		HashMap creatorMap = (HashMap)list.get(0);
		String beforeCreator = creatorMap.get("CREATOR").toString();
		BigDecimal id = groupUtil.getUserId(groupName);
		TransfeGroupOwner  tg = new TransfeGroupOwner();
		result = tg.transfeGroupOwner(groupName,id);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^小组\"(.*)\"成功转让所有权$")
	public void checkTransfeGroupOwner(String groupName){
		GroupUtil groupUtil = new GroupUtil();
		String sql = "SELECT CREATOR FROM vc_group WHERE ID = " + groupUtil.getGroupId(groupName) + " AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		HashMap creatorMap = (HashMap)list.get(0);
		String afterCreator = creatorMap.get("CREATOR").toString();
		if(!afterCreator.equals(beforeCreator)){
			assertTrue(true);
		}else{
			fail();
		}
	}

	@Then("^小组\"(.*)\"成功记录动态日志$")
	public void queryGroupLogPage(String groupName){
		QueryGroupLogPage qg = new QueryGroupLogPage();
		result = qg.queryGroupLogPage(groupName);
		JSONObject data = result.getJSONObject("data");
		JSONArray array = data.getJSONArray("data");     
		for(int i = 0;i< array.length(); i++){
			JSONObject obj = array.getJSONObject(i);
			if(obj.getString("logDesc").contains("出让所有权给")){
				assertTrue(true);
				break;
			}else{
				if(i == array.length()-1){
					fail();
				}
			}
		}

	}
	
	/*==============Scenario: Group_小组查询视图数==========*/
	@When("^小组查询视图数$")
	public void queryGroupInfoList(){
		QueryGroupInfoList queryGroupInfoList = new QueryGroupInfoList();
		result = queryGroupInfoList.queryGroupInfoList();
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^小组成功查询视图数$")
	public void checkQueryGroupInfoList(){
		String sql = "SELECT A.ID AS ID ,A.GROUP_NAME AS GROUP_NAME,A.CREATE_TIME AS CREATE_TIME,COUNT(C.ID) AS CNT from VC_GROUP A LEFT JOIN VC_DIAGRAM_GROUP B ON A.ID = B.GROUP_ID AND B.DOMAIN_ID ="+ QaUtil.domain_id +" LEFT JOIN VC_DIAGRAM C ON B.DIAGRAM_ID = C.ID"
                    +" AND C.STATUS = 1 AND C.DATA_STATUS = 1 AND C.DOMAIN_ID = "+ QaUtil.domain_id + " WHERE A.DATA_STATUS = 1 AND A.DOMAIN_ID = "+ QaUtil.domain_id +" and A.ID in(select D.GROUP_ID from VC_GROUP_USER D where D.USER_ID= " + QaUtil.user_id +" and DOMAIN_ID = "+QaUtil.domain_id+")"
                    + " GROUP BY A.ID,A.GROUP_NAME,A.CREATE_TIME ORDER BY A.CREATE_TIME DESC";
		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		for(int j = 0; j < list.size(); j++){
			HashMap grpMap = (HashMap)list.get(j);
			map.put(grpMap.get("ID"), grpMap);
		}
		JSONArray data = result.getJSONArray("data");
		if(data.length() > 0 && list.size()>0){
		    for(int i = 0;i < data.length(); i++){
			   JSONObject obj = (JSONObject)data.get(i);
			   int diagramCount = obj.getInt("diagramCount");
			   JSONObject group = obj.getJSONObject("group");
			   Integer diagramCountI = new Integer(diagramCount);
			   BigDecimal id = group.getBigDecimal("id");
			   HashMap grpMap = (HashMap)map.get(id);
			   String cntStr = grpMap.get("CNT").toString();
			   Assert.assertEquals(cntStr,diagramCountI.toString());
			   Assert.assertEquals(grpMap.get("GROUP_NAME"),group.get("groupName"));
			   Assert.assertEquals(grpMap.get("ID").toString(),id.toString());
			  
		    }
		}
	}
}
