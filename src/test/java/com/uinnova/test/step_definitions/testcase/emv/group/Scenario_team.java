package com.uinnova.test.step_definitions.testcase.emv.group;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.group.AddImage;
import com.uinnova.test.step_definitions.api.dmv.group.RemoveGroupById;
import com.uinnova.test.step_definitions.api.dmv.group.SaveOrUpdateGroupInfo;
import com.uinnova.test.step_definitions.api.emv.group.DeleteGroup;
import com.uinnova.test.step_definitions.api.emv.group.IsGroupAlreadyExist;
import com.uinnova.test.step_definitions.api.emv.group.SaveGroup;
import com.uinnova.test.step_definitions.api.emv.rule.UserList;
import com.uinnova.test.step_definitions.api.emv.severity.UploadVoice;
import com.uinnova.test.step_definitions.testcase.dmv.group.Scenario_group;
import com.uinnova.test.step_definitions.utils.EMV.EmvGroupUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
/**
 * @author yll
 * 
 */
public class Scenario_team {
	JSONObject result;
	public static BigDecimal groupId;
	String beforeCreator;
	private List<String> groupList = new ArrayList<String>(); // 用于记录一共新建了多少小组，
																// 用户After方法清理数据

	 @After("@DelEmvGroup")
	 public void delGroup(){
	 if (!groupList.isEmpty()){
	 for (int i=0; i<groupList.size(); i++){
	 String groupName = groupList.get(i);
	 DeleteGroup rg = new DeleteGroup();
	 result = rg.deleteGroup(groupName);
	 assertTrue(result.getBoolean("success"));
	 groupList.remove(groupName);
	 i--;
	 }
	 }
	 }
	@When("^创建团队名称为\"(.*)\",团队成员为,管理范围为的团队:$")
	public void creatGroup(String groupName, DataTable table) {
		JSONArray userid = new JSONArray();
		JSONArray sourceIds = new JSONArray();
		// 遍历DataTable里面的元素
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			String username = row.get(0);
			String sourceId = row.get(1);
			UserList userList = new UserList();
			// {"code":-1,"data":[{"weChatNo":"","id":100000000003001,"mobileNo":"","userName":"sen","userCode":"sen","email":"yanlili@uinnova.com"}],"success":true}
			JSONObject result = userList.userList(username).getJSONArray("data").getJSONObject(0);
			userid.put(result.getLong("id"));
			sourceIds.put(sourceId);
		}
		// 获取img的值
		String filePath = Scenario_team.class.getResource("/").getPath() + "testData/dmv/group/rttt.jpg";
		UploadVoice uploadVoice = new UploadVoice();
		// {"code":-1,"data":{"voiceUrl":"/122/20180525/100000000014589.jpg","voiceName":"303420.jpg","voicePlayUrl":"http://192.168.1.117:80/vmdb-sso/rsm/cli/read/122/20180525/100000000014589.jpg"},"success":true}
		String img = uploadVoice.uploadVoice(filePath).getJSONObject("data").getString("voiceUrl");
		SaveGroup su = new SaveGroup();
		JSONObject result1 = su.saveGroup(userid, sourceIds, groupName, img, "", 0);
		assertTrue(result1.getBoolean("success"));
		groupList.add(groupName);
		System.out.println(groupList);
	}
	@Then("^系统中存在团队名称为\"(.*)\",管理范围为的团队$")
	public void queryCreatGroup(String groupName, DataTable table) {
		JSONArray sourceIds = new JSONArray();
		JSONObject sourceId = new JSONObject();
		// 遍历DataTable里面的元素
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			String sourceId01 = row.get(1);
			sourceIds.put(sourceId01);
		}
		sourceId.put("sourceId", sourceIds);
		String sql = "SELECT ID,GROUP_NAME,AUTH_REGION FROM mon_group WHERE GROUP_NAME = '" + groupName
				+ "' AND AUTH_REGION = '" + sourceId + "' AND DATA_STATUS =1";
		ArrayList list = JdbcUtil.executeQuery(sql);
		if (list.size() == 1) {
			HashMap groupMap = (HashMap) list.get(0);
			groupId = (BigDecimal) groupMap.get("ID");
			assertEquals(groupId,(BigDecimal) groupMap.get("ID"));
	}}
	
	@And ("^再次新建团队名称为\"(.*)\"的团队:$")
	public void isGroupAlreadyExist(String groupName){
		IsGroupAlreadyExist su=new IsGroupAlreadyExist();
		JSONObject result2 = su.isGroupAlreadyExist(groupName);
		assertTrue(result2.getBoolean("success"));
		
	   }
	
	@When("^团队名称为\"(.*)\"修改为\"(.*)\",团队成员修改为,管理范围修改为的团队:$")
	public void updateGroup(String groupName, String newGroupName, DataTable table) {
		JSONArray userid = new JSONArray();
		JSONArray sourceIds = new JSONArray();
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			String username = row.get(0);
			String sourceId = row.get(1);
			UserList userList = new UserList();
			JSONObject result = userList.userList(username).getJSONArray("data").getJSONObject(0);
			userid.put(result.getLong("id"));
			sourceIds.put(sourceId);
		}
		SaveGroup su = new SaveGroup();
		result = su.saveGroup(userid, sourceIds, groupName, "", newGroupName, 1);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^系统中存在团队名称为\"(.*)\",团队成员为,管理范围为的团队$")
	public void checkUpdateGroup(String newGroupName, DataTable table) {
		EmvGroupUtil groupUtil = new EmvGroupUtil();
		JSONArray sourceIds = new JSONArray();
		JSONObject sourceId = new JSONObject();
		// 遍历DataTable里面的元素
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			String sourceId01 = row.get(1);
			sourceIds.put(sourceId01);
		}
		sourceId.put("sourceId", sourceIds);
		String sql = "SELECT ID,GROUP_NAME,AUTH_REGION FROM mon_group WHERE ID = '" + groupUtil.getGroupId(newGroupName)
				+ "' AND AUTH_REGION = '" + sourceId + "' AND DATA_STATUS =1";
		List groupList = JdbcUtil.executeQuery(sql);
		if (groupList.size() == 1) {
			HashMap groupMap = (HashMap) groupList.get(0);
			assertEquals(newGroupName, groupMap.get("GROUP_NAME").toString());
			assertEquals(sourceId.toString(), groupMap.get("AUTH_REGION").toString());
		}
	}
	@When ("^删除团队名称为\"(.*)\"的团队$")
	public void deleteGroup(String newGroupName){
		DeleteGroup rg = new DeleteGroup();
		result = rg.deleteGroup(newGroupName);
		assertTrue(result.getBoolean("success"));
		groupList.remove(newGroupName);
	}
	@Then("^团队管理页面不存在团队名称为\"(.*)\"的团队$")
	public void checkDeleteGroup(String newGroupName){
		EmvGroupUtil groupUtil = new EmvGroupUtil();
		String sql = "SELECT ID  FROM mon_group WHERE ID = " + groupUtil.getGroupId(newGroupName) + " AND DATA_STATUS = 1" ;
		List groupList = JdbcUtil.executeQuery(sql);
		assertEquals(0, groupList.size());
	}

}
