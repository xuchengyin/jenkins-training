package com.uinnova.test.step_definitions.testcase.pmv.kpiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.impl.store.Query;
import org.influxdb.dto.QueryResult;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.log.Log4JLogger;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryAll;
import com.uinnova.test.step_definitions.api.pmv.Event.QueryEventCount;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryKPIValueRltStr;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryMetricNames;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryTagValueCombination;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryTagValues;
import com.uinnova.test.step_definitions.api.pmv.Rule.QueryCountByCdt;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetCILable2;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetClassTree;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.GetConfigCIClassNames;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.QueryPcTagManageRlts;
import com.uinnova.test.step_definitions.api.pmv.StandardTag.SaveOrUpdatePcTagManageRlts;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.Delete;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.QueryPage;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.pmv.EventUtil;
import com.uinnova.test.step_definitions.utils.pmv.KpiValueRltUtil;
import com.uinnova.test.step_definitions.utils.pmv.MetricUtil;
import com.uinnova.test.step_definitions.utils.pmv.RuleUtil;
import com.uinnova.test.step_definitions.utils.pmv.StandardTagUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author kyn PMV 监控指标
 */
public class Scenario_KpiView {

	JSONArray searchKpiResult = new JSONArray();// 用于比较查询结果
	JSONArray KpiDetail = new JSONArray();// 用于比较查询结果
	private List<String> KpiList = new ArrayList<String>();

	@When("^查询监控指标列表$")
	public void KpiList() throws InterruptedException {
		Thread.sleep(10000);
		QueryMetricNames queryMetricNames = new QueryMetricNames();
		JSONObject result = queryMetricNames.queryMetricNames();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		searchKpiResult = result.getJSONArray("data");
		assertTrue(searchKpiResult.length() > 0);
	}

	@Then("^成功查询全部指标$")
	public void checkKpiList() {

		MetricUtil checkMeasurement = new MetricUtil();
		List result = checkMeasurement.searchMeasurement();
		if (result != null && result.size() > 0) {
			HashMap map = (HashMap) result.get(0);
			String name = (String) map.get("name");
			String job = searchKpiResult.toString();
			assertTrue(job.contains(name));
		}
	}

	@When("^查看指标\"(.*)\"的详细信息$")
	public void KpiDetails(String kpi) {
		QueryTagValueCombination queryTagValue = new QueryTagValueCombination();
		KpiDetail = queryTagValue.queryTagValueCombination(kpi);
		assertTrue(KpiDetail.length() > 0);
	}

	@Then("^成功查询指标\"(.*)\"的详细信息$")
	public void checkKpiDetails(String kpi) {

		MetricUtil checkMetrics = new MetricUtil();
		QueryCountByCdt queryCountByCdt = new QueryCountByCdt();
		QueryEventCount queryEventCount = new QueryEventCount();
		JSONArray param = new JSONArray();
		JSONObject Cdt = new JSONObject();
		Cdt.put("key", "metric");
		Cdt.put("value", kpi);
		param.put(Cdt);
		long EventCount = queryEventCount.queryEventCount(param.toString());
		RuleUtil ruleUtil = new RuleUtil();
		long RuleCount = ruleUtil.RuleCount(kpi);
		EventUtil eventUtil = new EventUtil();
		long ECount = new Double(eventUtil.EventCount(kpi)).longValue();
		List list = checkMetrics.checkMetrics(kpi);
		if (list != null && list.size() > 0) {
			HashMap map = (HashMap) list.get(0);
			String kpiclass = (String) map.get("kpiclass");
			String instance = (String) map.get("instance");
			String ciCode = (String) map.get("ciCode");
			String className = (String) map.get("className");
			String job = KpiDetail.toString();
			assertTrue(job.contains(kpiclass));
			assertTrue(job.contains(instance));
			assertTrue(job.contains(ciCode));
			assertTrue(job.contains(className));
		}
		assertEquals(queryCountByCdt.queryCountByCdt(kpi), RuleCount);
		assertEquals(EventCount, ECount);

	}

}
