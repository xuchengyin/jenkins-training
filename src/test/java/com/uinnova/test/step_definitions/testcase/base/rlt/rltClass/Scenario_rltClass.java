package com.uinnova.test.step_definitions.testcase.base.rlt.rltClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRltClass.QueryById;
import com.uinnova.test.step_definitions.api.base.ciRltClass.QueryList;
import com.uinnova.test.step_definitions.api.base.ciRltClass.RemoveById;
import com.uinnova.test.step_definitions.api.base.ciRltClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_rltClass {
	JSONObject result;
	private static List<String> rltClsList = new  ArrayList<String>();

	@After("@cleanRltCls")
	public void cleanCicls(){
		if (!rltClsList.isEmpty()){
			for (int i=0; i<rltClsList.size(); i++){
				String rltName = rltClsList.get(i);
				JSONObject result = (new RemoveById()).removeById(rltName);
				assertEquals(result.getInt("data"),1);
				rltClsList.remove(rltName);
				i--;
			}
		}
	}

	@When("创建名称为\"(.*)\"的关系分类，其动态效果为\"(.*)\",关系样式为\"(.*)\",关系宽度为\"(.*)\",关系箭头为\"(.*)\",显示类型为\"(.*)\",属性定义如下的:$")
	public void createRltClass(String rltName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineDispType, DataTable table){
		JSONObject  result = (new SaveOrUpdate()).saveOrUpdate(rltName,lineAnime,lineType,lineBorder,lineDirect,lineDispType,table);
		assertTrue(result.getBoolean("success"));
		rltClsList.add(rltName);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("^名称为\"(.*)\"的关系分类创建成功,其动态效果为\"(.*)\",关系样式为\"(.*)\",关系宽度为\"(.*)\",关系箭头为\"(.*)\",显示类型为\"(.*)\",属性定义如下的:$")
	public void checkCreateRltClass(String rltClassName,String lineAnime,String lineType,String lineBorder,String lineDirect,String lineDispType,DataTable table){
		JSONObject qi = (new QueryById()).queryById(rltClassName);
		JSONArray attrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject ciClass = qi.getJSONObject("data").getJSONObject("ciClass");
		BigDecimal rltClsId = (new RltClassUtil()).getRltClassId(rltClassName);
		int flag = 0;
		//比对class与输入结果
		assertEquals(ciClass.getString("classCode"), rltClassName);
		assertEquals(String.valueOf(ciClass.getInt("lineAnime")), lineAnime);
		assertEquals(ciClass.getString("lineType"), lineType);
		assertEquals(String.valueOf(ciClass.getInt("lineBorder")), lineBorder);
		assertEquals(ciClass.getString("lineDirect"), lineDirect);
		//比对attr与输入结果
		for(int i=0;i<attrDefs.length();i++){
			//获取proName、proType、enumValue
			JSONObject attrObj = (JSONObject) attrDefs.get(i);
			List<String> attrListObj = table.raw().get(i+1);
			String proName = null;
			if(attrListObj.get(0).indexOf(".txt")>0){
				String filePath = Scenario_rltClass.class.getResource("/").getPath()+"testData/rlt/"+attrListObj.get(0);
				proName = (new TxtUtil()).readTxt(filePath);
			}else{
				proName = attrListObj.get(0);
			}
			int  proType = Integer.parseInt(attrListObj.get(1)) ;
			String enumValue = attrListObj.get(2);

			assertEquals(attrObj.getString("proName"), proName);
			assertEquals(attrObj.getInt("proType"), proType);
			assertEquals(attrObj.getString("enumValues"), enumValue);
			assertEquals(attrObj.getInt("orderNo"), i+1);
			flag++;
		}
		assertEquals(table.raw().size()-1, flag);
	}

	@When("^复制\"(.*)\"关系分类为\"(.*)\"$")	
	public void copyRltClass(String sourceRltClass,String distRltClass){
		JSONObject qi =(new QueryById()).queryById(sourceRltClass);
		JSONArray attrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject ciClass = qi.getJSONObject("data").getJSONObject("ciClass");
		ciClass.remove("classPath");	
		ciClass.remove("createTime");	
		ciClass.remove("creator");	
		ciClass.remove("dataStatus");	
		ciClass.remove("domainId");	
		ciClass.remove("id");	
		ciClass.remove("isLeaf");	
		ciClass.remove("id");	
		ciClass.remove("modifier");	
		ciClass.remove("modifyTime");	
		ciClass.put("classCode", distRltClass);
		ciClass.put("className", distRltClass);
		ciClass.put("classStdCode",distRltClass.toUpperCase());
		for(Object attrDefObj : attrDefs){
			((JSONObject) attrDefObj).remove("classId");
			((JSONObject) attrDefObj).remove("createTime");
			((JSONObject) attrDefObj).remove("creator");
			((JSONObject) attrDefObj).remove("dataStatus");
			((JSONObject) attrDefObj).remove("domainId");
			((JSONObject) attrDefObj).remove("id");
			((JSONObject) attrDefObj).remove("isCiDisp");
			((JSONObject) attrDefObj).remove("isMajor");
			((JSONObject) attrDefObj).remove("isRequired");
			((JSONObject) attrDefObj).remove("modifier");
			((JSONObject) attrDefObj).remove("modifyTime");
			((JSONObject) attrDefObj).remove("mpCiField");
		}
		JSONObject result = (new SaveOrUpdate()).saveOrUpdate(attrDefs, ciClass);
		assertTrue(result.getBoolean("success"));
		rltClsList.add(distRltClass);
	}

	@Then("^\"(.*)\"关系分类成功复制为\"(.*)\"$")
	public void verfiyCopyRltClass(String sourceRltClass,String distRltClass){
		JSONObject source = (new QueryById().queryById(sourceRltClass));
		JSONObject dist = (new QueryById().queryById(distRltClass));
		//对比ciClass
		JSONArray ciClass = new JSONArray();
		ciClass.put(dist.getJSONObject("data").getJSONObject("ciClass"));
		ciClass.put(source.getJSONObject("data").getJSONObject("ciClass"));
		String s1,s2;
		ArrayList list = new ArrayList();
		for(Object obj : ciClass){
			HashMap map  =  new HashMap();
			map.put("lineAnime", ((JSONObject)obj).getInt("lineAnime"));
			map.put("lineBorder", ((JSONObject)obj).getInt("lineBorder"));
			map.put("lineColor", ((JSONObject)obj).getString("lineColor"));
			map.put("lineDirect", ((JSONObject)obj).getString("lineDirect"));
			map.put("lineType", ((JSONObject)obj).getString("lineType"));
			list.add(map);
		}
		assertEquals(list.get(0), list.get(1));
		//对比attrDefs
		JSONArray sAttrDef = source.getJSONObject("data").getJSONArray("attrDefs");
		JSONArray dAttrDef = dist.getJSONObject("data").getJSONArray("attrDefs");
		for(int i=0;i<sAttrDef.length();i++){
			JSONObject sobj= (JSONObject)sAttrDef.get(i);
			JSONObject dobj= (JSONObject)dAttrDef.get(i);
			assertEquals(sobj.getString("proName"), dobj.getString("proName"));
			assertEquals(sobj.getString("enumValues"), dobj.getString("enumValues"));
			assertEquals(sobj.getInt("orderNo"),dobj.getInt("orderNo"));
			assertEquals(sobj.getInt("proType"),dobj.getInt("proType"));
		}
	}

	@When("^删除名称为\"(.*)\"的关系分类及数据$")
	public void deleteRltClass(String rltClassName){
		JSONObject result = (new RemoveById()).removeById(rltClassName);
		assertEquals(result.getInt("data"),1);
		rltClsList.remove(rltClassName);
	}

	@Then("^关系分类\"(.*)\"分类及数据删除成功")
	public void verfifyDelRltCls(String rltClassName){
		BigDecimal clsId = (new RltClassUtil()).getRltClassId(rltClassName);
		assertEquals(clsId, new BigDecimal(0));
	}

	@When("^创建名称为\"(.*)\"的关系分类$")
	public void createRltClsWithoutAttr(String rltClsName){
		JSONObject result = (new SaveOrUpdate()).saveOrUpdateWithoutAttr(rltClsName);
		assertTrue(result.toString().indexOf("true")>0);
		rltClsList.add(rltClsName);
	}

	@And("^搜索名称包含\"(.*)\"的关系分类$")
	public void searchRltCls(String searchKey){
		result = (new QueryList()).queryList(searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^包含\"(.*)\"关键字的的关系分类全部搜索出来$")
	public void verifySearchRltCls(String searchKey){
		if(searchKey.indexOf(".txt")>0){
			String filePath = Scenario_rltClass.class.getResource("/").getPath()+"testData/rlt/"+searchKey;
			searchKey = (new TxtUtil()).readTxt(filePath);
		}
		JSONArray data  = result.getJSONArray("data");
		String searchResult = "";
		for(Object obj :data){
			searchResult += ((JSONObject)obj).getJSONObject("ciClass").getString("className");
		}
		String sql = "SELECT * FROM cc_ci_class c where CLASS_CODE like '%"+searchKey.trim().toUpperCase()+"%' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertNotNull(list);
		assertTrue(list.size()>0);
		HashMap map  = (HashMap)list.get(0);
		int flag = 0;
		for( Object obj : list){
			if(searchResult.indexOf(((HashMap) obj).get("CLASS_CODE").toString())>=0){
				flag++;
			}
		}
		assertTrue(flag==list.size());
	}

	/*@When("^创建名称为\"(.*)\"的关系分类,属性定义如下:$")
	public void createRltClsWithAttr(String rltName,DataTable table){
		JSONObject result = (new SaveOrUpdate()).saveOrUpdateUseDefaultLine(rltName, table);//不能用
		assertTrue(result.getBoolean("success"));
	}
	 */


}
