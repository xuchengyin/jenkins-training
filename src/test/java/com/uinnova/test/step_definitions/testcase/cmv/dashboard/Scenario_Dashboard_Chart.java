package com.uinnova.test.step_definitions.testcase.cmv.dashboard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.cmv.Dashboard.RemoveQualityChartById;
import com.uinnova.test.step_definitions.api.cmv.Dashboard.SaveOrUpdateQualityChartInfo;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryFailureCiInfoPage;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * CMV-仪表盘图表测试用例类
 * @author wsl
 *
 */
public class Scenario_Dashboard_Chart {
	private Map<String, List<String>> dashboardChartMap = new HashMap<String, List<String>>(); 

	@After("@delChart")
	public void delChart(){
		if (!dashboardChartMap.isEmpty()){
			RemoveQualityChartById remove = new RemoveQualityChartById();
			for(String key : dashboardChartMap.keySet()){ 
				List valueList = dashboardChartMap.get(key); 
				for (int i=0; i<valueList.size(); i++){
					String chartName = (String) valueList.get(i);
					remove.removeQualityChartById(key, chartName);
					valueList.remove(i);
					i--;
				}
			}
		}
	}



	@When("^给仪表盘\"(.*)\"添加\"(.*)\"图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"$")
	public void createDashboardChart(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		if (Contants.DASHBOARD_CHARTTYPE_TIME.compareToIgnoreCase(chartType)==0){
			su.saveOrUpdateQualityChartInfo(dashboardName, chartTitle, ruleType, ruleName);
		}

		List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.add(chartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);
	}

	@Then("^成功给仪表盘\"(.*)\"添加\"(.*)\"图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"$")
	public void checkCreateDashboardChart(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName){
		String sql = "select ID from CC_CI_QUALITY_CHART where title='"+chartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条记录",1,dbList.size());
	}

	@And("^再次给仪表盘\"(.*)\"添加\"(.*)\"图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"失败,kw=\"(.*)\"$")
	public void againCreateDashboardChart(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName, String kw){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		JSONObject result = su.saveOrUpdateQualityChartInfoAgain(dashboardName, chartTitle, ruleType, ruleName, kw);
		assertEquals(null, result);
	}

	@When("^修改仪表盘\"(.*)\"标题为\"(.*)\"的图表新标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"$")
	public void modifyDashBoradChart(String dashboardName, String chartTitle, String newChartTitle, String ruleType, String ruleName){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		JSONObject result = su.updateQualityChartInfo(dashboardName, chartTitle, newChartTitle, ruleType, ruleName);
		List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.remove(chartTitle);
		chartTitleList.add(newChartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);
	}

	@Then("^成功修改仪表盘\"(.*)\"标题为\"(.*)\"的图表新标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"$")
	public void checkMmodifyDashBoradChart(String dashboardName, String chartTitle, String newChartTitle, String ruleType, String ruleName){
		String oldSql = "select ID from CC_CI_QUALITY_CHART where title='"+chartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List oldDbList =  JdbcUtil.executeQuery(oldSql);
		if (chartTitle.compareToIgnoreCase(newChartTitle)==0)
			assertEquals("名称不改的时候",1,oldDbList.size());
		else
			assertEquals("原来名称不存在",0,oldDbList.size());
		String sql = "select ID from CC_CI_QUALITY_CHART where title='"+newChartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在更名后的记录",1,dbList.size());
	}


	@When("^给仪表盘\"(.*)\"添加\"(.*)\"类型的图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"低阈值为\"(.*)\"高阈值为\"(.*)\"$")
	public void createDashboardChartCurrent(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		if (Contants.DASHBOARD_CHARTTYPE_CURRENT.compareToIgnoreCase(chartType)==0){
			su.saveOrUpdateQualityChartInfo(dashboardName, chartTitle, ruleType, ruleName, thresholdFloorValue, thresholdUpperValue);
		}

		List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.add(chartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);
	}

	@Then("^成功给仪表盘\"(.*)\"添加\"(.*)\"类型的图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"低阈值为\"(.*)\"高阈值为\"(.*)\"$")
	public void checkCreateDashboardChartCurrent(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		String sql = "select ID from CC_CI_QUALITY_CHART where title='"+chartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在一条激活的记录",1,dbList.size());
	}

	@And("^再次给仪表盘\"(.*)\"添加\"(.*)\"类型的图表,标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"低阈值为\"(.*)\"高阈值为\"(.*)\"失败,kw=\"(.*)\"$")
	public void againCreateDashboardChartCurrent(String dashboardName, String chartType, String chartTitle, String ruleType, String ruleName, String thresholdFloorValue, String thresholdUpperValue, String kw){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		JSONObject result = su.saveOrUpdateQualityChartInfoAgain(dashboardName, chartTitle, ruleType, ruleName, thresholdFloorValue, thresholdUpperValue, kw);
		assertEquals(null, result);
	}

	@When("^修改仪表盘\"(.*)\"标题为\"(.*)\"的当前值图表新标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"低阈值为\"(.*)\"高阈值为\"(.*)\"$")
	public void modifyDashBoradChartCurrent(String dashboardName, String chartTitle, String newChartTitle, String ruleType, String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		SaveOrUpdateQualityChartInfo su = new SaveOrUpdateQualityChartInfo();
		JSONObject result = su.updateQualityChartInfo(dashboardName, chartTitle, newChartTitle, ruleType, ruleName, thresholdFloorValue, thresholdUpperValue);
		List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.remove(chartTitle);
		chartTitleList.add(newChartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);
	}

	@Then("^成功修改仪表盘\"(.*)\"标题为\"(.*)\"的当前值图表新标题为\"(.*)\"规则为\"(.*)\"\"(.*)\"低阈值为\"(.*)\"高阈值为\"(.*)\"$")
	public void checkMmodifyDashBoradChartCurrent(String dashboardName, String chartTitle, String newChartTitle, String ruleType, String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		String oldSql = "select ID from CC_CI_QUALITY_CHART where title='"+chartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List oldDbList =  JdbcUtil.executeQuery(oldSql);
		if (chartTitle.compareToIgnoreCase(newChartTitle)==0)
			assertEquals("名称不改的时候",1,oldDbList.size());
		else
			assertEquals("原来名称不存在",0,oldDbList.size());
		String sql = "select ID from CC_CI_QUALITY_CHART where title='"+newChartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1   "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中存在更名后的记录",1,dbList.size());
	}
	
	@When("^查看仪表盘\"(.*)\"的图表\"(.*)\"的数据$")
	public void queryData(String dashboardName, String chartTitle){
		QueryFailureCiInfoPage queryFailureCiInfoPage = new QueryFailureCiInfoPage();
		//queryFailureCiInfoPage.queryFailureCiInfoPage(ruleId, classId, pageNum, pageSize)
		/*List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.remove(chartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);*/
	}
	
	@Then("^成功查看仪表盘\"(.*)\"的图表\"(.*)\"的数据$")
	public void checkQueryData(String dashboardName, String chartTitle){
		
	}

	@When("^删除仪表盘\"(.*)\"的图表\"(.*)\"$")
	public void removeRuleById(String dashboardName, String chartTitle){
		RemoveQualityChartById rd = new RemoveQualityChartById();
		rd.removeQualityChartById(dashboardName, chartTitle);
		List<String> chartTitleList = new ArrayList<String>();
		if (!dashboardChartMap.isEmpty() && dashboardChartMap.containsKey(dashboardName))
			chartTitleList = dashboardChartMap.get(dashboardName);
		chartTitleList.remove(chartTitle);
		dashboardChartMap.put(dashboardName, chartTitleList);
	}

	@Then("^成功删除仪表盘\"(.*)\"的图表\"(.*)\"$")
	public void checkRemoveRuleById(String dashboardName, String chartTitle){
		String sql = "select ID from CC_CI_QUALITY_CHART where title='"+chartTitle.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1 "
				+" and DASHBOARD_ID = (select ID  from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"' "
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1)   ";
		List dbList =  JdbcUtil.executeQuery(sql);
		assertEquals("数据库中不存在记录", 0, dbList.size());
	}

}
