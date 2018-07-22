package com.uinnova.test.step_definitions.testcase.base.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.sys.auth.QueryRoleById;
import com.uinnova.test.step_definitions.api.base.sys.auth.QueryRoleModuInfoList;
import com.uinnova.test.step_definitions.api.base.sys.auth.QueryRolePage;
import com.uinnova.test.step_definitions.api.base.sys.auth.RemoveRoleById;
import com.uinnova.test.step_definitions.api.base.sys.auth.SaveFunAuth;
import com.uinnova.test.step_definitions.api.base.sys.auth.SaveOrUpdateRole;
import com.uinnova.test.step_definitions.api.cmv.ciClass.QueryAddingAuthList;
import com.uinnova.test.step_definitions.api.cmv.sys.data.QueryRoleAllDataAuth;
import com.uinnova.test.step_definitions.api.cmv.sys.data.SaveRoleDataAuth;
import com.uinnova.test.step_definitions.api.cmv.sys.fun.GetAuthModuTree;
import com.uinnova.test.step_definitions.api.dmv.sys.data.QueryUserAllDataAuth;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-07 编写人:sunsl 功能介绍:角色管理测试用例类
 */
public class Scenario_auth {
	JSONObject result;
	
	private static List<String> roleList = new  ArrayList<String>();

	/**
	 * 删除角色
	 */
	@After("@CleanRole")
	public void cleanRole(){
		if (!roleList.isEmpty()){
			for (int i=0; i<roleList.size(); i++){
				String roleName = roleList.get(i);
				RemoveRoleById rrb = new RemoveRoleById();
				rrb.removeRoleById(roleName);
				roleList.remove(roleName);
				i--;
			}
		}
	}

	@When("^创建角色名称为\"(.*)\"，角色描述为\"(.*)\"的角色$")
	public void createRole(String roleName, String roleDesc) {
		if (roleDesc.indexOf(".") > 0) {
			String filePath = Scenario_auth.class.getResource("/").getPath() + "testData/auth/" + roleDesc;
			roleDesc = (new TxtUtil()).readTxt(filePath);
		}
		SaveOrUpdateRole su = new SaveOrUpdateRole();
		result = su.saveOrUpdateRole(roleName, roleName, roleDesc, "");
		assertTrue(result.getBoolean("success"));
		roleList.add(roleName);
	}

	@Then("^系统中存在角色名称为\"(.*)\"，角色描述为\"(.*)\"的角色$")
	public void checkCreateRole(String roleName, String roleDesc) {
		if (roleDesc.indexOf(".") > 0) {
			String filePath = Scenario_auth.class.getResource("/").getPath() + "testData/auth/" + roleDesc;
			roleDesc = (new TxtUtil()).readTxt(filePath);
		}
		BigDecimal roleIdInt = result.getBigDecimal("data");
		String roleId = String.valueOf(roleIdInt);
		QueryRoleById qr = new QueryRoleById();
		result = qr.queryRoleById(roleId);
		JSONObject data = result.getJSONObject("data");
		String roleCode = data.getString("roleCode");
		if (roleName.equals(data.getString("roleName")) && roleDesc.equals(data.getString("roleDesc"))) {
			assertTrue("校验角色是否正确， 角色名称为："+roleName,true);
		} else {
			fail();
		}
	}

	@When("^给角色名称为\"(.*)\"的角色添加如下功能权限:$")
	public void addAuth(String roleName, DataTable table) {
		SaveFunAuth sf = new SaveFunAuth();
		result = sf.saveFunAuth(roleName, table);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^角色名称为\"(.*)\"的角色成功添加功能权限如下:$")
	public void checkAddAuth(String roleName, DataTable table) {
//		GetRoleModuIds gm = new GetRoleModuIds();
		QueryRoleModuInfoList qrmil = new QueryRoleModuInfoList();
		
		AuthUtil authUtil = new AuthUtil();
		result = qrmil.queryRoleModuInfoList(authUtil.getRoleId(roleName));
		JSONArray modusArray = result.getJSONArray("data");
		
		JSONArray modus = new JSONArray();
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> moduList = table.raw().get(i);
			JSONObject moduObj = new JSONObject();
			moduObj.put("id", moduList.get(1));
			if(moduList.size() == 3 && (!moduList.get(2).trim().equals("")) && moduList.get(2) != null){	
				String [] tempOpt = moduList.get(2).split(":");
				List<String> tempOptList = Arrays.asList(tempOpt);
				moduObj.put("optCodeList", tempOptList);
			}
			modus.put(moduObj);
		}

		if (modusArray.length() == modus.length()) {
			for(int i = 0; i < modusArray.length(); i++){
				boolean b = false;
				JSONObject tempResult = modusArray.getJSONObject(i);
				for(int j = 0; j < modus.length(); j++){
					JSONObject temp = modus.getJSONObject(j);
					if(tempResult.getInt("id") == temp.getInt("id")){
						if(tempResult.optJSONArray("optCodeList") != null){
							JSONArray tempRelOpt = tempResult.getJSONArray("optCodeList");
							JSONArray tempTabOpt = temp.getJSONArray("optCodeList");
							for(int s = 0; s < tempRelOpt.length(); s++){
								for(int y = 0; y < tempTabOpt.length(); y++){
									if(tempRelOpt.getString(s).equals(tempTabOpt.getString(y))){
										b = true;
									}
								}
							}
						}else{
							b = true;
						}
					}
				}
				assertTrue(b);
			}
			
		} else {
			fail();
		}
	}

	@When("^角色名称为\"(.*)\"的角色修改角色名称为\"(.*)\"，角色描述为\"(.*)\"的角色$")
	public void updateRole(String roleName, String updateRoleName, String updateRoleDesc) {
		SaveOrUpdateRole su = new SaveOrUpdateRole();
		result = su.saveOrUpdateRole(updateRoleName, updateRoleName, updateRoleDesc, roleName);
		assertTrue(result.getBoolean("success"));
		roleList.remove(roleName);
		roleList.add(updateRoleName);
	}

	@Then("^角色名称为\"(.*)\"，角色描述为\"(.*)\"的角色修改成功$")
	public void checkUpdateRole(String updateRoleName, String updateRoleDesc) {
		QueryRoleById qr = new QueryRoleById();
		AuthUtil authUtil = new AuthUtil();
		result = qr.queryRoleById(authUtil.getRoleId(updateRoleName));
		if (updateRoleName.equals(result.getJSONObject("data").getString("roleName"))
				&& updateRoleDesc.equals(result.getJSONObject("data").getString("roleDesc"))) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^删除角色名称为\"(.*)\"的角色$")
	public void deleteRole(String roleName) {
		RemoveRoleById rrb = new RemoveRoleById();
		result = rrb.removeRoleById(roleName);
		assertTrue(result.getBoolean("success"));
		roleList.remove(roleName);
	}

	@Then("^系统中不存在角色名称为\"(.*)\"的角色$")
	public void checkDeleteRole(String updateRoleName) {
		String sql = "SELECT ID FROM sys_role WHERE ROLE_NAME = '" + updateRoleName
				+ "' AND DATA_STATUS = 1 and DOMAIN_ID = " + QaUtil.domain_id;
		List roleList = JdbcUtil.executeQuery(sql);
		if (roleList.size() == 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* =====================Scenario Outline: Auth_角色搜索 ===================== */

	@And("^搜索角色名称包含\"(.*)\"的角色$")
	public void searchRole(String searchKey) {
		QueryRolePage qr = new QueryRolePage();
		result = qr.queryRolePage(searchKey);
		if (result.getJSONObject("data").getJSONArray("data").length() > 0) {
			assertTrue(true);
		}
	}

	@Then("^包含\"(.*)\"关键字的的角色全部搜索出来$")
	public void checkSearchRole(String searchKey) {
		// 从数据库取得数据
		String sql = "SELECT ROLE_NAME FROM sys_role WHERE ROLE_NAME LIKE '%" + searchKey
				+ "%' AND DATA_STATUS =1  and USER_DOMAIN_ID = " + QaUtil.domain_id + " ORDER BY ROLE_CODE";
		ArrayList list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		if (list.size() == data.length()) {
			for (int i = 0; i < list.size(); i++) {
				HashMap roleHashMap = (HashMap) list.get(i);
				JSONObject dataObj = (JSONObject) data.get(i);
				if (roleHashMap.get("ROLE_NAME").equals(dataObj.getString("roleName"))) {
					assertTrue(true);
				} else {
					fail();
				}
			}

		} else {
			fail();
		}
	}

	/* =====================Scenario: Auth_取得功能权限================= */
	@When("^取得所有的功能权限$")
	public void getAuthModuTree() {
		GetAuthModuTree getAuthModuTree = new GetAuthModuTree();
		result = getAuthModuTree.getAuthModuTree();
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功取得所有的功能权限\"(.*)\"$")
	public void checkGetAuthModuTree(@Delimiter(",") List<String> moduList) {
		//先查询父模块
		String sql = "SELECT MODU_CODE,MODU_NAME,PARENT_ID FROM sys_modu where  PARENT_ID = 0 AND MODU_CODE !=0  AND MODU_CODE !=10 AND MODU_CODE !=05 AND MODU_CODE != 80 AND is_dir = 1 order by ORDER_NO,MODU_CODE ";
		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			HashMap moduMap = (HashMap) list.get(i);
			map.put(moduMap.get("MODU_CODE"), moduMap);
		}
		//比较父模块
		assertEquals(list.size(), result.getJSONArray("data").length());
		JSONArray data = result.getJSONArray("data");
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			JSONObject modu = obj.getJSONObject("modu");
			assertEquals(((HashMap) map.get(modu.getString("moduCode"))).get("MODU_NAME"), modu.getString("moduName"));
		}
		//取得子模块
		for (int k = 0; k < moduList.size(); k++) {
            String moduName = moduList.get(k);
			String sql1 = "SELECT MODU_CODE,MODU_NAME,PARENT_ID FROM sys_modu  WHERE domain_id = 1 AND data_status = 1 AND parent_id = (SELECT id FROM sys_modu WHERE PARENT_ID = 0 and domain_id = 1 AND data_status = 1 AND modu_name = '" + moduName +"')";
			List listsql1 = JdbcUtil.executeQuery(sql1);
			HashMap mapsql1 = new HashMap();
			for (int i = 0; i < listsql1.size(); i++) {
				HashMap moduMap = (HashMap) listsql1.get(i);
				mapsql1.put(moduMap.get("MODU_CODE"), moduMap);
			}
            //比较子模块
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = (JSONObject) data.get(i);
				JSONObject modu = obj.getJSONObject("modu");
				if (modu.getString("moduName").equals(moduName)) {
					JSONArray children = obj.getJSONArray("children");
					for (int j = 0; j < children.length(); j++) {
						JSONObject obj1 = (JSONObject) children.get(j);
						JSONObject modu1 = obj1.getJSONObject("modu");
						assertEquals(((HashMap) mapsql1.get(modu1.getString("moduCode"))).get("MODU_NAME"),
								modu1.getString("moduName"));
					}
				}
			}
		}

	}
	
	/*============Scenario: Auth_角色管理_数据权限_新增=========*/
	@When("^给角色\"(.*)\"的分类\"(.*)\"设置新增权限$")
	public void saveRoleDataAuth(String roleName,@Delimiter(",")List<String> classNameList){
		QueryRoleAllDataAuth queryRoleAllDataAuth = new QueryRoleAllDataAuth();
		result = queryRoleAllDataAuth.queryRoleAllDataAuth(roleName);
		JSONObject data = result.getJSONObject("data");
		JSONArray dataAuths = data.getJSONArray("dataAuths");
		SaveRoleDataAuth saveRoleDataAuth = new SaveRoleDataAuth();
		result = saveRoleDataAuth.saveRoleDataAuth(roleName, classNameList, dataAuths);
		assertTrue(result.getBoolean("success"));	
	}
	
	@Then("^分类\"(.*)\"的权限为新增权限$")
	public void checkSaveRoleDataAuth(@Delimiter(",")List<String> classNameList){
		QueryUserAllDataAuth queryUserAllDataAuth = new QueryUserAllDataAuth();
		result = queryUserAllDataAuth.queryUserAllDataAuth();
		CiClassUtil ciClassUtil = new CiClassUtil();
		for (int j = 0; j < classNameList.size(); j++) {
			String className = classNameList.get(j);
			BigDecimal classId = ciClassUtil.getCiClassId(className);
			String sql = "SELECT DATA_RES_ID,AUTH_TYPE FROM cc_ci_role_data where DATA_RES_ID =" + classId
					+ " AND DATA_STATUS =1";
			List list = JdbcUtil.executeQuery(sql);
			HashMap map = new HashMap();
			if (list.size() > 0) {
				map = (HashMap) list.get(0);
			}
			JSONArray dataAuths = result.getJSONArray("data");
			for (int i = 0; i < dataAuths.length(); i++) {
				JSONObject obj = (JSONObject) dataAuths.get(i);
				if (classId.compareTo(obj.getBigDecimal("id")) == 0) {
					if (((BigDecimal) map.get("AUTH_TYPE")).compareTo(new BigDecimal(1001)) == 0) {
						assertTrue(obj.getBoolean("add"));
					}
				}
			}
		}
	}
	
	@When("^添加ci窗口中查询\"(.*)\"分类$")
	public void queryAddingAuthList(@Delimiter(",")List<String>classNameList){
		QueryAddingAuthList  queryAddingAuthList = new QueryAddingAuthList();
		result = queryAddingAuthList.queryAddingAuthList();
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^添加ci窗口中能正确查询出\"(.*)\"分类$")
	public void checkQueryAddingAuthList(@Delimiter(",")List<String> classNameList) throws InterruptedException{
		CiClassUtil  ciClassUtil = new CiClassUtil();
		String classIds = new String();
		for(int i = 0;i < classNameList.size();i++){
			String className = classNameList.get(i);
			BigDecimal classId = ciClassUtil.getCiClassId(className);
			if (i ==0){
				classIds = classId.toString();
			}else{
				classIds = classIds + "," + classId;
			}
		}
		Thread.sleep(3000);
		String sql = "SELECT DATA_RES_ID,AUTH_TYPE FROM cc_ci_role_data where DATA_RES_ID in (" + classIds
				+ ") AND DATA_STATUS =1 AND DATA_RES_TYPE=2";
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		assertEquals(list.size(),data.length());
	}
	
}
