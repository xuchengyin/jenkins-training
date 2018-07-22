package com.uinnova.test.step_definitions.testcase.base.sys.vframe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.sys.vframe.GetAuthMenuTreeByRootModuCode;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 2017-12-8
 *
 */
public class Scenario_vframe {
	private JSONArray menuArray;

	@When("查询模块编码(.*)授权菜单")
	public void getAuthMenuTreeByRootModuCode(String rootCode){
		GetAuthMenuTreeByRootModuCode getAuthMenuTreeByRootModuCode = new GetAuthMenuTreeByRootModuCode();
		JSONObject result = getAuthMenuTreeByRootModuCode.getAuthMenuTreeByRootModuCode(rootCode);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		menuArray = result.getJSONArray("data");
		assertTrue(menuArray.length()>0);
	}



	@Then("成功查询模块编码(.*)授权菜单")
	public void checkGetAuthMenuTreeByRootModuCode(String rootCode){
		//String sql = "select * from sys_menu where DATA_STATUS=1 and PARENT_ID = (select id from sys_menu where DATA_STATUS =1 and MENU_CODE='"+rootCode+"') ORDER BY MENU_CODE";
/*		String sql = "	select a.id as MENU_ID , a.PARENT_ID , a.MENU_NAME, a.MENU_CODE, a.MENU_IMG , b.MODU_URL from  "
				+" (select * from sys_menu where DATA_STATUS=1 and PARENT_ID = (select id from sys_menu where DATA_STATUS =1 and DOMAIN_ID=" + QaUtil.domain_id+" and MENU_CODE='"+rootCode+"') ORDER BY MENU_CODE) a "
				+" LEFT JOIN sys_modu b  on a.modu_id = b.ID and b.MODU_CODE like '"+rootCode+"%' and b.DATA_STATUS=1 and b.DOMAIN_ID=" + QaUtil.domain_id;*/
		String sql = "	select a.id as MENU_ID , a.PARENT_ID , a.MENU_NAME, a.MENU_CODE, a.MENU_IMG , b.MODU_URL from  "
				+" (select * from sys_menu where DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id +" and PARENT_ID = (select id from sys_menu where DATA_STATUS =1 and MENU_CODE='"+rootCode+"' and DOMAIN_ID = "+ QaUtil.domain_id+" ) ORDER BY MENU_CODE) a "
				+" LEFT JOIN sys_modu b  on a.modu_id = b.ID and b.MODU_CODE like '"+rootCode+"%' and b.DATA_STATUS=1 and b.DOMAIN_ID = "+ QaUtil.domain_id;

		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(menuArray.length(), list.size());
		for (int i=0; i<menuArray.length(); i++){
			JSONObject menuObj =menuArray.getJSONObject(i);
			Map map = (Map) list.get(i);
			assertEquals(menuObj.getBigDecimal("id"),map.get("MENU_ID"));
			assertEquals(menuObj.getBigDecimal("parentId"),map.get("PARENT_ID"));
			assertTrue(menuObj.getString("icon").contains((CharSequence) map.get("MENU_IMG")));
			assertEquals(menuObj.getString("text"),map.get("MENU_NAME"));
			assertEquals(menuObj.getString("code"),map.get("MENU_CODE"));
			//assertTrue(menuObj.getString("url").contains((CharSequence) map.get("MODU_URL"))); //遗留问题， 模拟告警的MODU_URL 是null ， 直接执行sql语句是有值的， 其他菜单没有问题。
			if (menuObj.has("children")){
				String menuId = String.valueOf(menuObj.getBigDecimal("id"));
				String childSql = "	select a.id as MENU_ID , a.PARENT_ID , a.MENU_NAME, a.MENU_CODE, a.MENU_IMG , b.MODU_URL from  "
						+ "(select * from sys_menu where DATA_STATUS=1 and PARENT_ID = "+ menuId +" and DOMAIN_ID=" + QaUtil.domain_id+" ORDER BY MENU_CODE) a "
						+ " LEFT JOIN sys_modu b  on a.modu_id = b.ID and b.DATA_STATUS=1 and DOMAIN_ID=" + QaUtil.domain_id;

				JSONArray childArray = menuObj.getJSONArray("children");
				ArrayList childList = (ArrayList) JdbcUtil.executeQuery(childSql);
				assertTrue(childArray.length() == childList.size());
				for (int j =0; j<childArray.length(); j++ ){
					JSONObject childMenuObj =childArray.getJSONObject(j);
					Map childMap = (Map) childList.get(j);
					assertEquals(childMenuObj.getBigDecimal("id"),childMap.get("MENU_ID"));
					assertEquals(childMenuObj.getBigDecimal("parentId"),childMap.get("PARENT_ID"));
					assertTrue(childMenuObj.getString("icon").contains("null"));
					assertEquals(childMenuObj.getString("text"),childMap.get("MENU_NAME"));
					assertEquals(childMenuObj.getString("code"),childMap.get("MENU_CODE"));
					assertTrue(childMenuObj.getString("url").contains((CharSequence) childMap.get("MODU_URL")));
				}
			}
		}
	}

}
