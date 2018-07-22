package com.uinnova.test.step_definitions.testcase.pmv.kpiValueRlt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.pmv.Metrics.QueryKPIValueRltStr;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.Delete;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.QueryPage;
import com.uinnova.test.step_definitions.api.pmv.kpiValueRlt.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.pmv.KpiValueRltUtil;
import com.uinnova.test.step_definitions.utils.pmv.MetricUtil;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author kyn PMV 特殊字符映射
 */
public class Scenario_kpiValueRlt {

	@Then("^存在指标类为\"(.*)\",指标为\"(.*)\",指标值为\"(.*)\"的数据$")
	public void checkCharMap(String kpiclass, String metric, String value) throws InterruptedException {
		Thread.sleep(10000);
		QueryPage queryPage = new QueryPage();
		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);
		String kpiValueStrs = allkpi.getString("kpiValueStrs");
		assertTrue(kpiValueStrs.contains(value));
	}

	@When("^给指标类为\"(.*)\"指标为\"(.*)\"的数据添加值映射$")
	public void againcreateVauleMap(String kpiclass, String metric, DataTable table) throws InterruptedException {
		QueryPage queryPage = new QueryPage();
		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);

		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
		SaveOrUpdate su = new SaveOrUpdate();
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			rltsjsonArray.put(new JSONObject().put("key", row.get(0)).put("value", row.get(1)));
		}
		JSONObject result = su.saveOrUpdate(allkpi.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		Thread.sleep(5000);
	}

	@Then("^验证指标类为\"(.*)\"指标为\"(.*)\"的数据添加值映射成功$")
	public void checkVauleMap(String kpiclass, String metric, DataTable table) throws InterruptedException {
		QueryPage queryPage = new QueryPage();
		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);
		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
		JSONArray rlts =new JSONArray();		
		for (int i = 1; i < table.raw().size(); i++) {
			List<String> row = table.raw().get(i);
			JSONObject rlt = new JSONObject();
			rlt.put("key", row.get(0));
			rlt.put("value", row.get(1));
			rlts.put(rlt);	
		}
		assertEquals(rltsjsonArray.toString(),rlts.toString());
	}

	@Then("^验证存在指标类为\"(.*)\"指标为\"(.*)\"指标值为\"(.*)\"的性能数据$")
	public void checkprefDataBysql(String kpiclass, String metric, int value) {
		MetricUtil getMetricValue = new MetricUtil();
		List list = getMetricValue.getMetricValue(kpiclass, metric);
		if (list != null && list.size() > 0) {
			HashMap map = (HashMap) list.get(0);
			assertEquals(map.get("value"), String.valueOf((float) value));
		}
	}
//	@When("^给指标类为\"(.*)\"指标为\"(.*)\"的数据添加值映射\"(.*)\"=\"(.*)\"$")
//	public void createVauleMap(String kpiclass, String metric, String key, String value) throws InterruptedException {
//		QueryPage queryPage = new QueryPage();
//		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);
//		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
//		SaveOrUpdate su = new SaveOrUpdate();
//		rltsjsonArray.put(new JSONObject().put("key", key).put("value", value));
//		JSONObject result = su.saveOrUpdate(allkpi.toString());
//		assertTrue(result.getBoolean("success"));
//		assertEquals(result.getInt("code"), -1);
//		Thread.sleep(5000);
//	}
//	@Then("^验证指标类为\"(.*)\"指标为\"(.*)\"的数据添加值映射成功\"(.*)\"=\"(.*)\"$")
//	public void checkVauleMap(String kpiclass, String metric, String key, String value) throws InterruptedException {
//		QueryPage queryPage = new QueryPage();
//		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);
//		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
//		JSONObject rlt = new JSONObject();
//		rlt.put("key", key);
//		rlt.put("value", value);
//		for (int k = 1; k < rltsjsonArray.length(); k++) {
//			JSONObject rltsjson = rltsjsonArray.getJSONObject(k);
//			assertTrue(rltsjson.toString().equals(rlt.toString()));
//		}
//	}
	@When("^删除指标为\"(.*)\"的性能数据$")
	public void deleteprefData(String metric) {
		MetricUtil deleteMetricValue = new MetricUtil();
		String result = deleteMetricValue.deleteMetricValue(metric);
		assertEquals(result, null);
	}

	@Then("^指标为\"(.*)\"的性能数据删除成功$")
	public void checkprefData(String metric) {
		MetricUtil checkMeasurement = new MetricUtil();
		List list = checkMeasurement.checkMeasurement(metric);
		assertEquals(list, null);
	}



	@When("^删除指标类为\"(.*)\"指标为\"(.*)\"指标值为\"(.*)\"的值映射$")
	public void deleteVauleMap(String kpiclass, String metric, String str) {
		QueryPage queryPage = new QueryPage();
		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);

		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
		for (int i = 0; i < rltsjsonArray.length(); i++) {
			JSONObject jsonData = (JSONObject) rltsjsonArray.get(i);
			if (jsonData.getString("key").equals(str)) {
				rltsjsonArray.remove(i);
			}
		}
		SaveOrUpdate su = new SaveOrUpdate();
		JSONObject result = su.saveOrUpdate(allkpi.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("^指标类为\"(.*)\"指标为\"(.*)\"指标值为\"(.*)\"的值映射删除成功$")
	public void checkDeleteVauleMap(String kpiclass, String metric, String str) {
		QueryPage queryPage = new QueryPage();
		JSONObject allkpi = queryPage.queryPage(kpiclass, metric);
		JSONArray rltsjsonArray = allkpi.getJSONArray("rlts");
		JSONArray rlts =new JSONArray();		
          boolean boo = false;  
          if(!rltsjsonArray.toString().contains(str)){  
        	   boo = true; 
          }  
  
		assertTrue(boo);

	}

	@When("^删除指标类为\"(.*)\",指标为\"(.*)\"的char数据$")
	public void deleteChar(String kpiclass, String metric) {
		KpiValueRltUtil kpiValueRltUtil = new KpiValueRltUtil();
		BigDecimal Id = kpiValueRltUtil.getcharMapID(kpiclass, metric);
		Delete delete = new Delete();
		JSONObject result = delete.delete(Id);
	}

	@Then("^指标类为\"(.*)\",指标为\"(.*)\"的char数据删除成功$")
	public void checkDeleteChar(String kpiclass, String metric) {
		String sql = "Select ID from pc_kpi_value_rlt where KPI='" + metric + "' and KPI_CLASS ='" + kpiclass
				+ "' ";
		List charMapList = JdbcUtil.executeQuery(sql);
		assertTrue(charMapList.size() == 0);

	}
}
