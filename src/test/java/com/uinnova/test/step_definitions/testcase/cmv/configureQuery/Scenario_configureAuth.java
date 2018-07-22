package com.uinnova.test.step_definitions.testcase.cmv.configureQuery;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.api.base.sys.data.SaveRoleDataAuth;
import com.uinnova.test.step_definitions.api.cmv.search.QuerySeeSearchKindList;
import com.uinnova.test.step_definitions.api.cmv.search.SearchInfoListWithDataAuth;
import com.uinnova.test.step_definitions.api.cmv.search.SearchInfoPageWithDataAuth;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class Scenario_configureAuth {
	
	SaveRoleDataAuth srda = new SaveRoleDataAuth();
	SearchInfoListWithDataAuth silwda = new SearchInfoListWithDataAuth();
	QuerySeeSearchKindList qsskl = new QuerySeeSearchKindList();
	SearchInfoPageWithDataAuth sipwda = new SearchInfoPageWithDataAuth();
	JSONObject resultJSON = null;
	
	@Then("^用以下参数进行授权Auth:$")
	public void authorization(DataTable dt){
//		String ciClassNames, String roleName,String dataType, String see, String edit, String delete
		List<List<String>> list = dt.raw();
//		String roleName, List<String> ciClassName, List<String> dataType, List<Boolean> see, List<Boolean> edit, List<Boolean> delete, List<Boolean> add
//		|ciClassNames|roleName|dataType|see|edit|delete|
		ArrayList<String> ciClassName = new ArrayList();
		ArrayList<String> dataType = new ArrayList();
		ArrayList<String> see = new ArrayList();
		ArrayList<String> edit = new ArrayList();
		ArrayList<String> delete = new ArrayList();
		ArrayList<String> add = new ArrayList();
		
		for(int i = 1; i < list.size(); i++){
			ciClassName.add(list.get(i).get(0));
			dataType.add(list.get(i).get(2));
			see.add(list.get(i).get(3));
			edit.add(list.get(i).get(3));
			delete.add(list.get(i).get(3));
			add.add(list.get(i).get(3));
		}
		String roleName = list.get(1).get(1);
		JSONObject result = srda.saveRoleDataAuth(roleName, ciClassName, dataType, see, edit, delete, add);
		assertTrue(result.getBoolean("data"));
	}
	//如果需要多个参数，请用":"分割
	@And("^用以下数据进行查询操作Auth:$")
	public void configureQuery(DataTable dt){
//		SearchInfoList sil = new SearchInfoList();
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
		resultJSON = silwda.SearchInfoListWithDataAuth(Arrays.asList(ciList),Arrays.asList(tagList),Arrays.asList(wordList));
	}
	
	
	@And("^用以下数据进行查询操作Auth,查询结果为空:$")
	public void configureQueryFaile(DataTable dt){
//		SearchInfoList sil = new SearchInfoList();
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
		JSONObject result = silwda.SearchInfoListWithDataAuth(Arrays.asList(ciList),Arrays.asList(tagList),Arrays.asList(wordList));
		assertTrue(result.getJSONObject("data").getJSONArray("records").length() == 0);
	}

	@And("^用以下数据进行数据验证Auth:$")
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
					coBoo = Arrays.asList(ciCode).contains(ci.getString("ciCode").replace("\"", ""));
				}
			}
			assertTrue(ciBoo);
			assertTrue(coBoo);
		}else{
			assertTrue(false);
		}
		

	}
	
}
