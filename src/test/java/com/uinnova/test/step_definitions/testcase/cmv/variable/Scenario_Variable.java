package com.uinnova.test.step_definitions.testcase.cmv.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.sys.variable.QueryVariableList;
import com.uinnova.test.step_definitions.api.cmv.sys.variable.RestoreDefaultSettings;
import com.uinnova.test.step_definitions.api.cmv.sys.variable.SaveOrUpdateVaribleByName;
import com.uinnova.test.step_definitions.api.dmv.sys.data.QuerySysLogos;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_Variable {
	
	@When("^修改logo用如下参数:$")
	public void updateVariableSuccess(DataTable dt){
		List<List<String>> list = dt.raw();
		SaveOrUpdateVaribleByName souvbn = new SaveOrUpdateVaribleByName();
		for(int i = 1; i < list.size(); i++){
			String value = null;
			String name = list.get(i).get(1);	
			if(QaUtil.gethasPort()){
				value = QaUtil.getServerUrl()+":1512"+list.get(i).get(2).toString();
			}else{
				value = QaUtil.getServerUrl()+list.get(i).get(2).toString();
			}
			String desc = list.get(i).get(3);
			JSONObject result = souvbn.saveOrUpdateVaribleByName(name, value, desc);
			assertTrue(result.getBoolean("data"));
		}
	}
	
	@When("^修改logo用如下参数失败:$")
	public void updateVariableFailed(DataTable dt){
		List<List<String>> list = dt.raw();
		SaveOrUpdateVaribleByName souvbn = new SaveOrUpdateVaribleByName();
		for(int i = 1; i < list.size(); i++){
			String value = null;
			String name = list.get(i).get(1);
			if(QaUtil.gethasPort()){
				value = QaUtil.getServerUrl()+":1512"+list.get(i).get(2).toString();
			}else{
				value = QaUtil.getServerUrl()+list.get(i).get(2).toString();
			}
			String desc = list.get(i).get(3);
			String kw = list.get(i).get(4);
			JSONObject result = souvbn.saveOrUpdateVaribleByNameFailed(name, value, desc, kw);
			assertEquals(result,null);
		}
	}
	
	@Then("^用如下参数验证修改正确:$")
	public void queryVariable(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryVariableList qsskl = new QueryVariableList();
		JSONObject result = qsskl.queryVariableList();
		assertTrue(result.getBoolean("success"));
		JSONArray resultArray = result.getJSONArray("data");
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject tempObj = resultArray.getJSONObject(i);
			assertTrue(tempObj.getInt("dataStatus") == 1);
			assertTrue(tempObj.getInt("varType") == 2);
			boolean boo = false;
			for (int j = 1; j < list.size(); j++) {
				String name = list.get(j).get(1);
				String value = null;
				if(QaUtil.gethasPort()){
					value = QaUtil.getServerUrl()+":1512"+list.get(j).get(2).toString();
				}else{
					value = QaUtil.getServerUrl()+list.get(j).get(2).toString();
				}
				String desc = list.get(j).get(3);
				if(tempObj.getString("varName").equals(name) && tempObj.getString("varValue").equals(value) && tempObj.getString("varDesc").equals(desc)){
					boo = true;
				}
			}
			assertTrue(boo);
		}
	}
	
	@When("^还原logo用如下参数:$")
	public void restoreDefaultSettings(DataTable dt){
		RestoreDefaultSettings restoreDefaultSettings = new RestoreDefaultSettings();
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size();i++){
			String varName = list.get(i).get(1);
			String varType = list.get(i).get(2);
			String varValue = null;
			String varDesc = list.get(i).get(3);
			JSONObject result = restoreDefaultSettings.restoreDefaultSettings(varName, varType, varValue, varDesc);
		    assertTrue(result.getBoolean("success"));
		}
	}
	
	@Then("^正确还原logo用如下参数:$")
	public void checkRestoreDefaultSettings(DataTable dt) {
		List<List<String>> list = dt.raw();
		QuerySysLogos querySysLogos = new QuerySysLogos();
		JSONObject result = querySysLogos.querySysLogos();
		assertTrue(result.getBoolean("success"));
		JSONArray resultArray = result.getJSONArray("data");
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject obj = resultArray.getJSONObject(i);
			assertTrue(obj.getInt("dataStatus") == 1);
			boolean boo = false;
			int j = i + 1;
			String varName = list.get(j).get(1);
			String varType = list.get(j).get(2);
			int vt = Integer.parseInt(varType);
			String value = null;
			if (QaUtil.gethasPort()) {
				value = QaUtil.getServerUrl() + ":1512" + list.get(j).get(3).toString();
			} else {
				value = QaUtil.getServerUrl() + list.get(j).get(3).toString();
			}
			String varDesc = list.get(j).get(4);
			if (obj.getString("varName").equals(varName) && obj.getInt("varType") == vt
					&& obj.getString("varValue").equals(value) && obj.getString("varDesc").equals(varDesc)) {
				boo = true;
			}

			assertTrue(boo);
		}
	}
}
