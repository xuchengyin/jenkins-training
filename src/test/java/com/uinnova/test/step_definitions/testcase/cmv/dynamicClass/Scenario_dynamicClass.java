package com.uinnova.test.step_definitions.testcase.cmv.dynamicClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.dynamicClass.QueryCiAttrNames;
import com.uinnova.test.step_definitions.api.cmv.dynamicClass.QueryDefaultTpl;
import com.uinnova.test.step_definitions.api.cmv.dynamicClass.QueryDefaultTree;
import com.uinnova.test.step_definitions.api.cmv.dynamicClass.RebuildDefaultTree;
import com.uinnova.test.step_definitions.api.cmv.dynamicClass.RemoveDefaultTpl;
import com.uinnova.test.step_definitions.api.cmv.dynamicClass.SaveOrUpdateDefaultTpl;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_dynamicClass {
	
	ArrayList<String> listTplName = new ArrayList();
	
	@After("@deleteTpl")
	public void deleteTpl(){
		JdbcUtil ju = new JdbcUtil();
		for (int i = 0; i < listTplName.size(); i++) {
			String sql = "DELETE FROM CC_DYNAMIC_CLASS_TPL WHERE TPL_NAME = '"+listTplName.get(i)+"'";
			ju.executeUpdate(sql);
		}
		String sql = "DELETE FROM CC_CI_TAG_DEF WHERE TAG_TYPE = 61";
		ju.executeUpdate(sql);
	}
	
	@When("^查询ci属性,用如下参数验证:$")
	public void queryAttr(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryCiAttrNames qcan = new QueryCiAttrNames();
		JSONArray result = qcan.queryCiAttrNames().getJSONArray("data");
		for (int i = 1; i < list.size(); i++) {
			boolean boo = false;
			String attrName = list.get(i).get(1);
			for (int j = 0; j < result.length(); j++) {
				if(result.get(j).equals(attrName)){
					boo = true;
					break;
				}
			}
			assertTrue(boo);
		}
	}
	
	@When("^用以下参数创建树状图:$")
	public void createDynamicClass(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject obj = new JSONObject();
		SaveOrUpdateDefaultTpl soudt = new SaveOrUpdateDefaultTpl();

		for (int i = 1; i < list.size(); i++) {
			String dataStatus = list.get(i).get(0);
			String tplName = list.get(i).get(1);
			String levelNames = list.get(i).get(2);
			String ciClassNames = list.get(i).get(3);
			List levelNameArray = Arrays.asList(levelNames.split(":"));
			List ciClassNamesArray = Arrays.asList(ciClassNames.split(":"));
			soudt.saveOrUpdateDefaultTpl(dataStatus, tplName, levelNameArray, ciClassNamesArray);
		}

	}
	
	@When("^用以下参数创建树状图,创建失败:$")
	public void createDynamicClassFailed(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject obj = new JSONObject();
		SaveOrUpdateDefaultTpl soudt = new SaveOrUpdateDefaultTpl();

		for (int i = 1; i < list.size(); i++) {
			String dataStatus = list.get(i).get(0);
			String tplName = list.get(i).get(1);
			String levelNames = list.get(i).get(2);
			String ciClassNames = list.get(i).get(3);
			List levelNameArray = Arrays.asList(levelNames.split(":"));
			List ciClassNamesArray = Arrays.asList(ciClassNames.split(":"));
			String kw = list.get(i).get(4);
			soudt.saveOrUpdateDefaultTplFailed(dataStatus, tplName, levelNameArray, ciClassNamesArray, kw);
		}
	}
	
	@Then("^用以下参数验证树状图创建成功:$")
	public void compDynamicClass(DataTable dt){
//		|dataStatus|tplName|levelNameList|ciClassNameList|
//		|1|测试树状图|CI Code:整数:小数:枚举:日期|s@&_-|
		List<List<String>> list = dt.raw();
		QueryDefaultTpl qdt = new QueryDefaultTpl();
		JSONObject result = qdt.queryDefaultTpl().getJSONObject("data");
		JSONArray ciClassList = result.getJSONArray("ciClassList");
		JSONObject tpl = result.getJSONObject("tpl");
		CiClassUtil ccu = new CiClassUtil();
		for (int i = 1; i < list.size(); i++) {
			String dataStatus = list.get(i).get(0);
			String tplName = list.get(i).get(1);
			String levelNames = list.get(i).get(2);
			String ciClassNames = list.get(i).get(3);
			List<String> levelNameArray = Arrays.asList(levelNames.split(":"));
			List<String> ciClassNamesArray = Arrays.asList(ciClassNames.split(":"));
			
			//转为jsonobject
			JSONObject levelName = new JSONObject();
			for (int j = 0; j < levelNameArray.size(); j++) {
				levelName.put("levelName"+(j+1), levelNameArray.get(j));
			}
			
			//确定ciclassname是否正确
			for (int j = 0; j < ciClassNamesArray.size(); j++) {
				boolean boo = false;
				for (int j2 = 0; j2 < ciClassList.length(); j2++) {
					JSONObject temp = ciClassList.getJSONObject(j2);
					if(ciClassNamesArray.get(j).equals(temp.getString("className")) && temp.getBigDecimal("id").equals(ccu.getCiClassId(ciClassNamesArray.get(j)))){
						boo = true;
					}
				}
				assertTrue("判断返回值ciClassName是否正确 :", boo);
			}
			
			//判断tpl是否正确
			Iterator it = levelName.keys();
			while(it.hasNext()){
				String key = it.next().toString();
				String level = levelName.getString(key);
				String resultLevel = tpl.getString(key);
				assertEquals("判断返回值levelName是否相等 :", level , resultLevel);
			}
			//判断tplName是否相等
			assertEquals("判断tplName是否相等 :", tplName , tpl.getString("tplName"));
			//判断dataStatus是否相等
			assertEquals("判断dataStatus是否相等 :", Integer.parseInt(dataStatus), tpl.getInt("dataStatus"));
		}
	}
	
	@Then("删除默认树状图\"(.*)\"")
	public void removeTpl(String num){
		RemoveDefaultTpl rdt = new RemoveDefaultTpl();
		JSONObject result = rdt.removeDefaultTpl();
		assertEquals("确认删除记录数 :", Integer.parseInt(num), result.getInt("data"));
	}
	
	
	@Then("^重新构建默认树状图$")
	public void rebuildDefaultTree(){
		RebuildDefaultTree rdt = new RebuildDefaultTree();
		int result = rdt.rebuildDefaultTree().getInt("data");
		assertEquals(result, 2);
	}
	@Then("^查询默认动态分类配置,用以下数据验证:$")
	public void queryDefaultTree(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryDefaultTree qdt = new QueryDefaultTree();
		JSONObject result = qdt.queryDefaultTree();
		JSONArray resultArray = result.getJSONArray("data");
		//先这么写，后续用递归
		boolean boo = false;
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject tempObjA = resultArray.getJSONObject(i);
			String textA = tempObjA.getString("text");
			if(tempObjA.has("children")){
				JSONArray tempObjAC = tempObjA.getJSONArray("children");
				for (int j = 0; j < tempObjAC.length(); j++) {
					JSONObject tempObjB = tempObjAC.getJSONObject(j);
					String textB = tempObjB.getString("text");
					if(tempObjB.has("children")){
						JSONArray tempObjBC = tempObjB.getJSONArray("children");
						for (int k = 0; k < tempObjBC.length(); k++) {
							JSONObject tempObjC = tempObjBC.getJSONObject(k);
							String textC = tempObjC.getString("text");
							if(tempObjC.has("children")){
								JSONArray tempObjCC = tempObjC.getJSONArray("children");
								for (int l = 0; l < tempObjCC.length(); l++) {
									JSONObject tempObjD = tempObjCC.getJSONObject(l);
									String textD = tempObjD.getString("text");
									if(tempObjD.has("children")){
										JSONArray tempObjDC = tempObjD.getJSONArray("children");
										for (int m = 0; m < tempObjDC.length(); m++) {
											JSONObject tempObjE = tempObjDC.getJSONObject(m);
											String textE = tempObjE.getString("text");
											System.out.print("+++++++"+textA+"---"+textB+"---"+textC+"---"+textD+"---"+textE+"---");
											if(tempObjE.has("children")){
												fail("超出5层，请修改测试用例！！！");
												break;
											}
											for (int o = 1; o < list.size(); o++) {
												if(		textA.equals(list.get(o).get(0)) 
														&& textB.equals(list.get(o).get(1))
														&& textC.equals(list.get(o).get(2))
														&& textD.equals(list.get(o).get(3))
														&& textE.equals(list.get(o).get(4))
//														&& textF.equals(list.get(o).get(5))
														){
													boo = true;
												}
											}
//											if(tempObjE.has("children")){
//												JSONArray tempObjEC = tempObjE.getJSONArray("children");
//												for (int n = 0; n < tempObjEC.length(); n++) {
//													JSONObject tempObjF = tempObjEC.getJSONObject(m);
//													String textF = tempObjF.getString("text");
//													
//													
//												}
//											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		assertTrue(boo);
		
	}
}
