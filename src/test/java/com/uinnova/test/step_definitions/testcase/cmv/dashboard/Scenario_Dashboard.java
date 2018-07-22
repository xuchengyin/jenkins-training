package com.uinnova.test.step_definitions.testcase.cmv.dashboard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.Dashboard.QueryDashBoardPage;
import com.uinnova.test.step_definitions.api.cmv.Dashboard.RemoveDashBoardById;
import com.uinnova.test.step_definitions.api.cmv.Dashboard.SaveOrUpdateDashboard;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * CMV-仪表盘测试用例类
 * @author wsl
 *
 */
public class Scenario_Dashboard {
	private JSONObject queryDashboardResult = new JSONObject();//用于比较查询结果
	private  List<String> dashboardList = new ArrayList<String>(); 

	@After("@delDashboard")
	public void delDashboard(){
		if (!dashboardList.isEmpty()){
			for (int i=0; i<dashboardList.size(); i++){
				String dashboardName = dashboardList.get(i);
				RemoveDashBoardById rd = new RemoveDashBoardById();
				JSONObject result = rd.removeDashBoardById(dashboardName);
				dashboardList.remove(dashboardName);
				i--;
			}
		}
	}

	@When("^新建名称为\"(.*)\"的仪表盘$")
	public void createCiQualityRule(String dashboardName){
		SaveOrUpdateDashboard su = new SaveOrUpdateDashboard();
		su.saveOrUpdateDashboard(dashboardName);
		dashboardList.add(dashboardName);
	}

	@Then("^成功新建名称为\"(.*)\"的仪表盘$")
	public void checkCreateCiQualityRule(String dashboardName){
		String sql = "  select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条激活的记录",1,dbList.size());
	}

	@And("^再次新建名称为\"(.*)\"的仪表盘失败,kw=\"(.*)\"$")
	public void againCreateCiQualityRule(String dashboardName, String kw){
		SaveOrUpdateDashboard su = new SaveOrUpdateDashboard();
		JSONObject result = su.saveOrUpdateDashboardAgain(dashboardName, kw);
		assertEquals(null, result);
	}

	@When("^修改名称为\"(.*)\"的仪表盘名称为\"(.*)\"$")
	public void modifyCiQualityRule(String dashboardName, String newDashboardName){
		SaveOrUpdateDashboard su = new SaveOrUpdateDashboard();
		su.updateDashboard(dashboardName, newDashboardName);
		dashboardList.remove(dashboardName);
		dashboardList.add(newDashboardName);
	}

	@Then("^成功修改名称为\"(.*)\"的仪表盘名称为\"(.*)\"$")
	public void checkModifyCiQualityRule(String dashboardName, String newDashboardName){
		String oldSql = "  select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   order by  MODIFY_TIME DESC";
		List oldDbList =  JdbcUtil.executeQuery(oldSql);
		if (dashboardName.compareToIgnoreCase(newDashboardName)==0)
			assertEquals("名称不改的时候",1,oldDbList.size());
		else
			assertEquals("原来名称不存在",0,oldDbList.size());
		String sql = "  select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+newDashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1    order by  MODIFY_TIME DESC";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在更名后的记录",1,dbList.size());
	}


	@When("^按照关键字\"(.*)\"搜索仪表盘$")
	public void queryCiQualityRule(String dashboardName){
		QueryDashBoardPage qup = new QueryDashBoardPage();
		queryDashboardResult = qup.queryDashBoardPage(dashboardName, 1, 1000);
		queryDashboardResult = queryDashboardResult.getJSONObject("data");
	}

	@Then("^成功按照关键字\"(.*)\"搜索仪表盘$")
	public void checkQueryCiQualityRule(String dashboardName){
		String sql = "select ID, DASHBOARD_NAME, DASHBOARD_DESC, DASHBOARD_TYPE, STATUS, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME, MODIFY_TIME  "
				+" from CC_CI_QUALITY_DASHBOARD   where      DASHBOARD_NAME like '%"+dashboardName.trim()+"%'   "
				+" and    DASHBOARD_TYPE = 1  and    STATUS = 1    and    DOMAIN_ID = "+QaUtil.domain_id+"   and    DATA_STATUS = 1  order by CREATE_TIME  ";
		List dbList =  JdbcUtil.executeQuery(sql);
		JSONArray arr = queryDashboardResult.getJSONArray("data");
		assertEquals(dbList.size(), arr.length());
		assertEquals(dbList.size(), queryDashboardResult.getInt("totalRows"));
		for (int i=0; i<arr.length(); i++){
			JSONObject tempObj = arr.getJSONObject(i);
			Map tempMap = (Map) dbList.get(i);
			assertEquals(tempObj.getBigDecimal("id"),tempMap.get("ID"));
			assertEquals(tempObj.getBigDecimal("status"),tempMap.get("STATUS"));
			assertEquals(tempObj.getString("dashboardName"),tempMap.get("DASHBOARD_NAME"));
			assertEquals(tempObj.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
			assertEquals(tempObj.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
			assertEquals(tempObj.getBigDecimal("dashboardType"),tempMap.get("DASHBOARD_TYPE"));
		//	assertEquals(tempObj.getString("creator"),tempMap.get("CREATOR"));
			assertEquals(tempObj.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
			assertEquals(tempObj.getString("modifier"),tempMap.get("MODIFIER"));
			assertEquals(tempObj.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		}
	}


	@When("^删除名称为\"(.*)\"的仪表盘$")
	public void removeRuleById(String dashboardName){
		RemoveDashBoardById rd = new RemoveDashBoardById();
		rd.removeDashBoardById(dashboardName);
		dashboardList.remove(dashboardName);
	}

	@Then("^成功删除名称为\"(.*)\"的仪表盘$")
	public void checkRemoveRuleById(String dashboardName){
		String dashboardSql = "  select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 ";
		List dashboardList =  JdbcUtil.executeQuery(dashboardSql);
		assertEquals("数据库中不存在该仪表盘记录", 0, dashboardList.size());
		//同时删除仪表盘图标
		String sql = "  select ID  from cc_ci_quality_chart   where   DASHBOARD_ID = (select ID from cc_ci_quality_dashboard where DASHBOARD_NAME='"+dashboardName.trim()+"'  and    DOMAIN_ID = "+QaUtil.domain_id+" and DATA_STATUS=1)"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中不存在该仪表盘的图表记录", 0, dbList.size());
	}

}
