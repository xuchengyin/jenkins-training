package com.uinnova.test.step_definitions.testcase.base.monitor.performances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.monitor.performance.ExportPerformanceTpl;
import com.uinnova.test.step_definitions.api.cmv.monitor.performance.GetKpiByCiCode;
import com.uinnova.test.step_definitions.api.cmv.monitor.performance.GetPerformanceList;
import com.uinnova.test.step_definitions.api.cmv.monitor.performance.ImportPerformances;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_performances {
	String excelPath;
	@When("^下载性能数据模板使用ciCode为\"(.*)\"$")
	public void downloadPerformancesTpl(String ciCode){
		ExportPerformanceTpl ept = new ExportPerformanceTpl();
		excelPath = ept.exportPerformanceTpl(ciCode);
	}
	
	@Then("^用以下参数验证模板:$")
	public void compPerformancesTpl(DataTable dt){
		if(excelPath == null)return;
//		excelPath = "D:\\workspace\\tariser_auto\\src\\test\\resources\\testData\\cmv\\monitor\\performances\\performancesTpl.xls";
		ExcelUtil eu = new ExcelUtil();
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i++){
//			|指标名称|时间差|性能值|//indexName  指标名称(sheet)
			String indexName = list.get(i).get(0);
			String [] column = list.get(i).get(1).split(":");
			JSONArray readResult = eu.readFromExcel(excelPath, indexName);
			assertTrue(readResult.length() > 0);
			assertEquals(column[0],readResult.getJSONObject(0).getString("0"));
			assertEquals(column[1],readResult.getJSONObject(0).getString("1"));
		}
	}
	
	@When("^用以下参数上传性能数据:$")
	public void uploadPerformances(DataTable dt){
		ImportPerformances ip = new ImportPerformances();
		String result = ip.importPerformances(Scenario_performances.class.getResource("/").getPath()+"testData/cmv/monitor/performances/"+dt.raw().get(1).get(0), dt.raw().get(1).get(1));
		JSONObject resultObj = new JSONObject(result);
		assertTrue(resultObj.getBoolean("success"));
	}
	
	@Then("^用以下参数验证\"(.*)\"上传成功:$")
	public void compPerformances(String ciCode, DataTable dt){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GetPerformanceList gpl = new GetPerformanceList();
		List<List<String>> list = dt.raw();
		JSONObject resultObj = gpl.getPerformanceList(ciCode);
		JSONArray data = resultObj.getJSONArray("data");
		JSONArray timeAndValue = data.getJSONObject(0).getJSONArray("timeAndVals");
		assertEquals(timeAndValue.length(),list.size()-1);
		assertEquals(data.getJSONObject(0).getString("ciName"),ciCode);
		assertEquals(data.getJSONObject(0).getString("kpiName"),list.get(1).get(0));
		for(int i = 1; i < list.size(); i++){
			String performancesDiff = list.get(i).get(2);
			boolean boo = false;
			//xiongjian新加删除功能，上传后自动删除对应ci的所有性能，所以，需要有长度校验了。
			for(int j = 0; j < timeAndValue.length(); j++){
				JSONObject tempObj = timeAndValue.getJSONObject(j);
				if(tempObj.getString("val").equals(performancesDiff)){
					boo = true;
					break;
				}
			}
			assertTrue(boo);
		}
	}
	
	@When("^查看\"(.*)\"挂有KPI$")
	public void getKpi(String ciCode){
		GetKpiByCiCode gkbcc = new GetKpiByCiCode();
		JSONObject result = gkbcc.getKpiByCiCode(ciCode);
		assertEquals(result.getInt("data"),1);
	}

}
