package com.uinnova.test.step_definitions.testcase.cmv.configureQuery;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.api.cmv.ci.ExportSearchInfo;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
public class Scenario_configureQuery {
	

	JSONArray excelArray;
	public static JSONObject resultJSON;
	//如果需要多个参数，请用":"分割
	@And("^用以下数据进行查询操作:$")
	public void configureQuery(DataTable dt){
		SearchInfoList sil = new SearchInfoList();
		List<List<String>> list = dt.raw();
		String[] ciList = null ;
		String[] tagList = null ;
		String[] wordList  = null;
		for(int i = 1; i < list.size(); i++){
			List<String> tempList =  list.get(i);
			ciList = tempList.get(1).split(":");
			tagList = tempList.get(2).split(":");
			wordList = tempList.get(3).split(":");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultJSON = sil.searchInfoList(Arrays.asList(ciList),Arrays.asList(tagList),Arrays.asList(wordList));
	}
	
	@And("^用以下数据进行数据验证:$")
	public void assertResult(DataTable dt){
		CiClassUtil ccu = new CiClassUtil();
		List<List<String>> list = dt.raw();
		String[] className = null;
		ArrayList <String> classId = new ArrayList();
		String[] ciCode = null;
		boolean ciBoo = true;
		boolean coBoo = true;
		for(int i = 1; i < list.size(); i++){
			List<String> tempList =  list.get(i);
			className = tempList.get(1).split(":");
			ciCode = tempList.get(2).split(":");

		}
		for(int i = 0; i < className.length; i++){
			BigDecimal id = ccu.getCiClassId(className[i]);
			classId.add(id.toString());
		}
		
		JSONArray ja = resultJSON.getJSONObject("data").getJSONArray("records");
		if(ja.length() > 0){
			for(int i = 0; i < ja.length(); i++){
				JSONObject ci = ja.getJSONObject(i).getJSONObject("ci");

				if(!classId.contains(ci.getBigDecimal("classId"))){
					ciBoo = classId.contains(ci.getBigDecimal("classId").toString());
				}
				if(!Arrays.asList(ciCode).contains(ci.getString("ciCode"))){
					//坑爹的json字符串，自带双引号，不去掉不行
					coBoo = Arrays.asList(ciCode).contains(ci.getString("ciCode").replace("\"", ""));
				}
			}
			assertTrue(ciBoo);
			assertTrue(coBoo);
		}else{
			assertTrue(false);
		}
		

	}
	@And("^用以下关键字进行下载:$")
	public void downLoadCi(DataTable dt){
		CiClassUtil ccu = new CiClassUtil();
		ExcelUtil eu = new ExcelUtil();
		ExportSearchInfo esi = new ExportSearchInfo();
		List<List<String>> list = dt.raw();
		ArrayList<String> classIds = new ArrayList();
		ArrayList<String> tagIds = new ArrayList();
		String[] tagNames = null ;
		String[] classNames = null ;
		String[] word  = null;
		for(int i = 1; i < list.size(); i++){
			List<String> tempList =  list.get(i);
			classNames = tempList.get(1).split(":");
			tagNames = tempList.get(2).split(":");
			word = tempList.get(3).split(":");
		}
		if(classNames.length > 0 && !"".equals(classNames[0])){
			for(int i = 0; i < classNames.length; i++){
				BigDecimal id = ccu.getCiClassId(classNames[i]);
				classIds.add(id.toString());
			}
		}
		if(tagNames.length > 0 && !"".equals(tagNames[0])){
			for(int i = 0; i < tagNames.length; i++){
				BigDecimal id = (new TagRuleUtil()).getTagId(tagNames[i]);
				tagIds.add(id.toString());
			}
		}
		List<String> words = Arrays.asList(word);
		ArrayList<String> wordsList = new ArrayList<String>(words);
		String result = esi.exportSearchInfo(tagIds, classIds, wordsList);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		excelArray = eu.readFromExcel(result,list.get(1).get(1));
//		Assert.
		if(excelArray.length() > 0){
			assertTrue(true);
		}else{
			assertTrue(false);
		}
		
	}

	@And("^用以下数据进行验证:$")
	public void compExcelData(DataTable dt){
		List list = dt.raw().get(1);
		JSONObject jo = (JSONObject) excelArray.get(1);
		ArrayList listAfter = new ArrayList(list);
		listAfter.remove(list.get(0));
		for(int i = 0; i < listAfter.size(); i++){
			String dtString = (String) listAfter.get(i);
			String excelString = jo.getString(i+"");
			if(isNum(dtString) && isNum(excelString)){
				assertTrue(Float.parseFloat(dtString) == Float.parseFloat(excelString));
			}else{
				assertTrue(dtString.equals(excelString));
			}
		}
	}
	
	public boolean isNum(String str){
		try{
			Float.parseFloat(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
}
