package com.uinnova.test.step_definitions.testcase.base.pv;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.pv.AddAClickRecord;
import com.uinnova.test.step_definitions.api.base.pv.QueryPvCountPage;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_pv {
	
	List<List<String>> publicList;
	@After("@deleteCount")
	public void deleteCount(){
		JdbcUtil ju = new JdbcUtil();
		ju.executeUpdate("DELETE FROM CC_PV_COUNT WHERE ID > 113");
		ju.executeUpdate("UPDATE CC_PV_COUNT SET PV_COUNT = 0");
	}
	@Before("@deleteCountBefore")
	public void deleteCountBefore(){
		JdbcUtil ju = new JdbcUtil();
		ju.executeUpdate("DELETE FROM CC_PV_COUNT WHERE ID > 113");
		ju.executeUpdate("UPDATE CC_PV_COUNT SET PV_COUNT = 0");
	}
	
	
	@When("^给如下模块添加点击记录:$")
	public void addPvClick(DataTable dt){
		AddAClickRecord aacr = new AddAClickRecord();
		publicList = dt.raw();
		for(int i = 1; i < publicList.size(); i++){
			for(int j = 0; j < Integer.parseInt(publicList.get(i).get(4)); j++){
				JSONObject result = aacr.addAClickRecord(publicList.get(i).get(3));
				assertTrue(result.getBoolean("success"));
			}
		}
	}
	
	@Then("^用如下参数查询最近一小时的PV统计:$")
	public void queryPvCount(DataTable dt){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<List<String>> list = dt.raw();
		QueryPvCountPage qpcp = new QueryPvCountPage();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date endDate = new Date();
		Date startDate=new Date(new Date().getTime()- 60*60*1000);
		for(int i = 1; i < list.size(); i++){
			if(list.get(i).get(5).equals("0")){
				List<String> tempMenu = Arrays.asList(list.get(i).get(1).split(":"));
				ArrayList<String> arrayList = new ArrayList<String>(tempMenu);

				JSONObject result = qpcp.queryPvCountPage(Long.parseLong(sdf.format(startDate)), Long.parseLong(sdf.format(endDate)), "", arrayList, 1, 10000);
				JSONArray data = result.getJSONObject("data").getJSONArray("data");
				int count = 0;
				for(int j = 1; j < publicList.size(); j++){
					if(arrayList.toString().indexOf(publicList.get(j).get(1)) > -1){
						count += Integer.parseInt(publicList.get(j).get(4));
					}
				}
				int count1 = 0;
				for(int j = 0; j < data.length(); j++){
					JSONObject obj = data.getJSONObject(j);
					count1 += obj.getInt("pvCount");
				}
				assertTrue(count == count1);
			}else if(list.get(i).get(5).equals("1")){
				String targetCode = list.get(i).get(3);
				String targetName = list.get(i).get(2);
				ArrayList temp = new ArrayList();
				JSONObject result = qpcp.queryPvCountPage(Long.parseLong(sdf.format(startDate)), Long.parseLong(sdf.format(endDate)), targetName, temp, 1, 10000);
				JSONArray data = result.getJSONObject("data").getJSONArray("data");
				int count = 0;
				for(int j = 1; j < publicList.size(); j++){
					if(publicList.get(j).get(2).equals(targetName)){
						count += Integer.parseInt(publicList.get(j).get(4));
					}
				}
				int count1 = 0;
				for(int j = 0; j < data.length(); j++){
					JSONObject obj = data.getJSONObject(j);
//					if(obj.get("targetName").equals(targetName)){
					count1 += obj.getInt("pvCount");
//					}
				}
				assertTrue(count==count1);
			}else{
				List<String> tempMenu = Arrays.asList(list.get(i).get(1).split(":"));
				ArrayList<String> arrayList = new ArrayList<String>(tempMenu);
				String targetCode = list.get(i).get(3);
				String targetName = list.get(i).get(2);
				JSONObject result = qpcp.queryPvCountPage(Long.parseLong(sdf.format(startDate)), Long.parseLong(sdf.format(endDate)), targetName, arrayList, 1, 10000);
				JSONArray data = result.getJSONObject("data").getJSONArray("data");
				int count = 0;
				for(int j = 1; j < publicList.size(); j++){
					if(arrayList.toString().indexOf(publicList.get(j).get(1)) > -1 && publicList.get(j).get(2).equals(targetName)){
						count = Integer.parseInt(publicList.get(j).get(4));
					}
				}
				
				int count1 = 0;
				for(int j = 0; j < data.length(); j++){
					JSONObject obj = data.getJSONObject(j);
					count1 += obj.getInt("pvCount");
				}
				assertTrue(count == count1);
			}
		}
	}
}
