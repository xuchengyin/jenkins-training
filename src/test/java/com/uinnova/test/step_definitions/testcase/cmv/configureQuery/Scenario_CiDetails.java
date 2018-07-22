package com.uinnova.test.step_definitions.testcase.cmv.configureQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.api.base.ciRlt.SaveOrUpdateBatch;
import com.uinnova.test.step_definitions.api.base.sys.data.SaveRoleDataAuth;
import com.uinnova.test.step_definitions.api.cmv.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.cmv.ci.RemoveHaveAuthByIds;
import com.uinnova.test.step_definitions.api.cmv.ci.UpdateCiCommBatch;
import com.uinnova.test.step_definitions.api.cmv.ciClass.QuerySourceOrTargetClassList;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.ExportRltTargetCi;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryCiRltCount;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryRcaByCiId;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryRltClassCiInfoPage;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.RemoveByCdt;
import com.uinnova.test.step_definitions.api.cmv.ciclassForm.QueryByClassId;
import com.uinnova.test.step_definitions.api.cmv.ciclassForm.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.cmv.monitor.event.QueryEventDetails;
import com.uinnova.test.step_definitions.api.cmv.monitor.performance.GetPerformanceList;
import com.uinnova.test.step_definitions.api.noah.rest.EventImport;
import com.uinnova.test.step_definitions.api.noah.rest.PerformanceImport;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * @author lidw
 * 说明：本类属于配置查询界面，点击具体CI之后，左侧出现的详情界面的测试用例。
 *
 */

public class Scenario_CiDetails {
	ArrayList tempCiCode;
	String ciCodeName;
	String filePath;
	JSONArray ciRltLinesResult;
	@When("^修改表单如下:$")
	public void ciClassFormSetting(DataTable dt){

		List<List<String>> list = dt.raw();
		CiClassUtil ccu = new CiClassUtil();
		JSONObject form = new JSONObject();
		JSONArray classFormInfos = new JSONArray();
		//获取CI名字所对应的ID
		BigDecimal ciId = ccu.getCiClassId(list.get(1).get(list.get(1).size()-1));
		ArrayList tempOrderNo = new ArrayList();
		for(int i = 1; i < list.size(); i++){
			tempOrderNo.add(list.get(i).get(2));
		}
	    HashSet hs = new HashSet(tempOrderNo);
	    tempOrderNo.clear();
	    tempOrderNo.addAll(hs);
	    //遍历list，组装JSON
	    for(int i = 0; i < tempOrderNo.size(); i++){
	    	JSONObject tempObject = new JSONObject();
	    	JSONObject ciClassForm = new JSONObject();
	    	JSONArray attrDisps = new JSONArray();
	    	for(int j = 1; j < list.size(); j++){
	    		JSONObject attrDispsObjectTemp = new JSONObject();
		    	if(list.get(j).get(2).equals(tempOrderNo.get(i))){
		    		ciClassForm.put("classId", ciId);
		    		ciClassForm.put("title", list.get(j).get(0));
		    		ciClassForm.put("formCols", list.get(j).get(1));
		    		ciClassForm.put("orderNo", list.get(j).get(2));
		    		attrDispsObjectTemp.put("classId", ciId);
		    		
		    		QueryById qbi = new QueryById();
		    		JSONObject tempAttrId = qbi.queryById(list.get(1).get(list.get(1).size()-1));
		    		for(int b = 0; b < tempAttrId.getJSONObject("data").getJSONArray("attrDefs").length(); b++){
		    			JSONObject temp = tempAttrId.getJSONObject("data").getJSONArray("attrDefs").getJSONObject(b);
		    			if(temp.get("proName").equals(list.get(j).get(3))){
		    				attrDispsObjectTemp.put("attrId", temp.get("id"));//这里需要通过属性名获取属性ID
		    				break;
		    			}
		    		}
		    		attrDispsObjectTemp.put("formRowNum", list.get(j).get(4));
		    		attrDispsObjectTemp.put("formColNum", list.get(j).get(5));
		    		attrDisps.put(attrDispsObjectTemp);
		    	}else{
		    		continue;
		    	}
		    }
	    	tempObject.put("ciClassForm", ciClassForm);
	    	tempObject.put("attrDisps", attrDisps);
	    	classFormInfos.put(tempObject);
	    }
		//组装classFromInfos
		form.put("ClassId", ciId);
		form.put("classFormInfos", classFormInfos);
		SaveOrUpdate sou = new SaveOrUpdate();
		JSONObject result = sou.saveOrUpdate(form);
		assertTrue(result.getBoolean("success"));
		QueryByClassId qbc = new QueryByClassId();
		JSONObject getResult = qbc.queryByClassId(ciId.toString());
		for(int i = 0; i < getResult.getJSONArray("data").length(); i++){
			JSONObject tempObj = getResult.getJSONArray("data").getJSONObject(i);
			assertTrue(tempObj.getJSONObject("ciClassForm").getBigDecimal("classId").toString().equals(form.getBigDecimal("ClassId").toString()));
			ArrayList tempTitle = new ArrayList();
			for(int j = 0; j < form.getJSONArray("classFormInfos").length(); j++){
				JSONObject tempObj1 = form.getJSONArray("classFormInfos").getJSONObject(j);
				tempTitle.add(tempObj1.getJSONObject("ciClassForm").getString("title"));
			}
			boolean tempB = false;
			for(int j = 0; j < tempTitle.size(); j++){
				if(tempObj.getJSONObject("ciClassForm").getString("title").equals(tempTitle.get(j))){
					tempB = true;
					break;
				}
			}
			assertTrue(tempB);
		}
	}
	

	
	@Then("^批量删除以下主键的ci：$")
	public void removeHaveAuthByIds(DataTable dt){
		CiClassUtil ccu = new CiClassUtil();
		SaveRoleDataAuth srda = new SaveRoleDataAuth();
		CiUtil cu = new CiUtil();
		RemoveHaveAuthByIds rhabi = new RemoveHaveAuthByIds();
		List<List<String>> al = dt.raw();
		ArrayList tempIds = new ArrayList();
		tempCiCode = new ArrayList();
		String className = al.get(1).get(0);
		JSONObject obj = new JSONObject();
		for(int i = 1; i < al.size(); i++ ){
			BigDecimal id = cu.getCiId(al.get(i).get(1));
			tempIds.add(id);
			tempCiCode.add(al.get(i).get(1));
			ciCodeName = al.get(i).get(2);
		}
		//给管理员用户授权，使之可以删除数据(在cmv中)。
//		JSONObject autoObj = new JSONObject("{\"roleId\":\"1\",\"dataAuths\":[{\"id\":\"1\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"2\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"3\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"4\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"5\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"6\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"7\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"8\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"9\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"10\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"11\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"12\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"13\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"14\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"15\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"16\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"17\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"18\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"19\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"20\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"21\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"23\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"24\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"25\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"26\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000093\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000095\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000096\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000097\",\"dataType\":\"2\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\""+ccu.getCiClassId(className)+"\",\"dataType\":\"2\",\"see\":1,\"edit\":1,\"delete\":1},{\"id\":\"100000000000027\",\"dataType\":\"1\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000028\",\"dataType\":\"1\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000029\",\"dataType\":\"1\",\"see\":1,\"edit\":0,\"delete\":0},{\"id\":\"100000000000030\",\"dataType\":\"1\",\"see\":1,\"edit\":0,\"delete\":0}]}");
		
		
		
//		JSONObject autoResult = srda.saveRoleDataAuth("admin", "@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_", 2, true, true, true, true);
//		@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_
//		assertTrue(autoResult.getBoolean("success"));
		//授权之后，进行删除
		obj.put("ids", tempIds);
		JSONObject result = rhabi.removeHaveAuthByIds(obj);
//		{"code":-1,"data":2,"success":true}
		assertTrue(result.getBoolean("success"));
//		{ciIds:[ci-id数组]}
//		cu.getCiId(ciCode);
	}
	
	@Then("^验证删除操作$")
	public void assertDeleteResult(){
		//验证删除是否成功，进行查询
		JSONObject searchResult  = Scenario_configureQuery.resultJSON;
		for(int i = 0; i < searchResult.getJSONObject("data").getJSONArray("records").length(); i++){
			JSONObject tempObj = searchResult.getJSONObject("data").getJSONArray("records").getJSONObject(i);
//			tempObj.getJSONObject("attrs").getString("CI CODE");
			for(int j = 0; j < tempCiCode.size(); j++){
				
				assertTrue(!tempCiCode.get(j).equals(tempObj.getJSONObject("attrs").getString(ciCodeName)));
			}
		}

	}

	@Then("^批量修改以下主键的ci：$")
	public void updateByIds(DataTable dt){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UpdateCiCommBatch uccb = new UpdateCiCommBatch();
		JSONObject result = uccb.updateCiCommBatch(dt);
//		{"data":{"failCount":0,"totalCount":3,"successCount":3,"noAuthCount":0},"code":-1,"success":true}
		assertTrue(result.getBoolean("success"));
	}
	
	
	//新建关联关系/删除/导出
	
	@Then("^创建如下CI关系:$")
	public void createRltForCI(DataTable table){
		SaveOrUpdateBatch soub = new SaveOrUpdateBatch();
		JSONObject tempResult = soub.saveOrUpdateBatch(table);
		assertTrue(tempResult.getBoolean("success"));
		//创建后进行验证

		QueryRltClassCiInfoPage qrccip = new QueryRltClassCiInfoPage();
		String rltName = table.raw().get(1).get(0);
//		String targetCiCode = table.raw().get(1).get(2);
		ArrayList<String> sourceCiCodeList = new ArrayList();
		ArrayList<String> targetCiCodeListtemp = new ArrayList();
		for(int i = 1; i < table.raw().size(); i++){
			sourceCiCodeList.add(table.raw().get(i).get(1));
		}
		for(int i = 1; i < table.raw().size(); i++){
			targetCiCodeListtemp.add(table.raw().get(i).get(2));
		}
		HashSet set = new HashSet(sourceCiCodeList);
		HashMap map = new HashMap();
		sourceCiCodeList = new ArrayList(set);
		for(int i = 0; i < sourceCiCodeList.size(); i++){
			String temp = new String();
			for(int j = 1; j < table.raw().size(); j++){
				if(table.raw().get(j).get(1).equals(sourceCiCodeList.get(i))){
					temp = table.raw().get(j).get(2);
				}
			}
			map.put(sourceCiCodeList.get(i), temp);
		}
		for(int i = 0; i < sourceCiCodeList.size(); i++){
			String sourceCiCode = map.get(sourceCiCodeList.get(i)).toString();

				JSONObject resultObj = qrccip.queryRltClassCiInfoPage(rltName, sourceCiCodeList.get(i), sourceCiCode);
				JSONArray resultObjList = resultObj.getJSONObject("data").getJSONArray("data");

				ArrayList <String>targetCiCodeList = new ArrayList();
				for(int n = 0; n < resultObjList.length(); n++){
					JSONObject tempObj = resultObjList.getJSONObject(n);
					targetCiCodeList.add(tempObj.getJSONObject("ci").getString("ciCode"));
				}
				Collections.sort(targetCiCodeList); 
				boolean tempBoo = false;
				for(int n = 0; n < targetCiCodeList.size(); n++){
					if(sourceCiCode.equals(targetCiCodeList.get(n))){
						tempBoo = true;
						break;
					}
				}
				assertTrue(tempBoo);
		}	

	}
	@Then("^导出当前关系,导出源ciCode为\"(.*)\",目标ciClass为\"(.*)\",关系名为\"(.*)\"的所有关系$")
	public void exportAllRlt(String sourceCiCode,String targetClassName,String rltName){//classId为关系类ID
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltClassUtil rcu = new RltClassUtil();
		ExportRltTargetCi ertc = new ExportRltTargetCi();
		BigDecimal targetClassId = ccu.getCiClassId(targetClassName);
		BigDecimal sourceCiId = cu.getCiId(sourceCiCode);
		BigDecimal rltClassId = rcu.getRltClassId(rltName);
		filePath = ertc.exportRltTargetCi(sourceCiId.toString(), null, null, targetClassId.toString(), rltClassId.toString());
	}
	
	@Then("^用rltName为\"(.*)\",sourceCiCode为\"(.*)\",targetCiCode为\"(.*)\"进行导出验证验证$")
	public void compExportAllRlt(String rltName,String sourceCiCode,String targetCiCode){
		QueryRltClassCiInfoPage qrccip = new QueryRltClassCiInfoPage();
		ExcelUtil eu = new ExcelUtil();
		CiClassUtil ccu = new CiClassUtil();
		
		JSONObject obj = qrccip.queryRltClassCiInfoPage(rltName, sourceCiCode, targetCiCode);
		JSONArray data = obj.getJSONObject("data").getJSONArray("data");
		JSONArray excelResult = eu.readFromExcel(filePath, ccu.getCiClassNameByCiCode(targetCiCode));
		ArrayList<String> excelArray = new ArrayList();
		ArrayList<String> resultArray = new ArrayList();
		ArrayList tempKeyAttrs = new ArrayList();
		//验证下载内容和查询到的内容是否一致
		//开始统一结构
		for(int i = 0; i < data.length(); i++){
			JSONObject tempObj = data.getJSONObject(i).getJSONObject("attrs");
			Iterator<String> it=  tempObj.keys();
			ArrayList tempValueAttrs = new ArrayList();
			while(it.hasNext()){
				String key = it.next();
				String value = tempObj.getString(key);
				if(i == 0){
					tempKeyAttrs.add(key.toUpperCase());
				}
				tempValueAttrs.add(value);
			}
			if(i== 0){
				Collections.sort(tempKeyAttrs);
				resultArray.add(tempKeyAttrs.toString());
			}
			Collections.sort(tempValueAttrs);
			resultArray.add(tempValueAttrs.toString());
		}
		for(int i = 0; i < excelResult.length(); i++){
			JSONObject tempObj = excelResult.getJSONObject(i);
			ArrayList tempValueExcel = new ArrayList();
			Iterator<String> it= tempObj.keys();
			while(it.hasNext()){
				String key = it.next();
				String value = tempObj.getString(key);
				if(i == 0){
					tempValueExcel.add(value.toUpperCase());
				}else{
					tempValueExcel.add(value);
				}
			}
			Collections.sort(tempValueExcel);
			excelArray.add(tempValueExcel.toString());
		}
//		统一结构完毕
//		进行对比
		if(resultArray.size() == excelArray.size()){
			for(int i = 0; i < resultArray.size(); i++){
				boolean comp = false;
				for(int j = 0; j < excelArray.size(); j++){
					if(resultArray.get(i).equals(excelArray.get(j))){
						comp = true;
						break;
					}
				}
				assertTrue(comp);
			}
		}else{
			assertTrue(false);
		}
	}
	/**
	 * @author ldw
	 *由于接口修改，重写此方法    2018-5-23
	 * */
	@Then("^用如下数据验证关系数量:$")
	public void getCount(DataTable dt){
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		List<List<String>> list = dt.raw();
		QueryCiRltCount qcrc = new QueryCiRltCount();
		RltClassUtil rcu = new RltClassUtil();
		List<String> ciCodes = new ArrayList();
		List<String> ciClassNames = new ArrayList();
		for (int i = 1; i < list.size(); i++) {
			String ciCode = list.get(i).get(0);
			String ciClassName = list.get(i).get(1);
			ciCodes.add(ciCode);
			ciClassNames.add(ciClassName);
		}
		if(ciCodes.size() != ciClassNames.size())assertTrue("输入的ciCodes与ciClassNames数量不同: ", false);
		JSONObject result = qcrc.queryCiRltCount(ciCodes, ciClassNames);
		assertTrue(result.getBoolean("success"));
		JSONObject data = result.getJSONObject("data");
		for (int i = 1; i < list.size(); i++) {
			String ciClassName = list.get(i).get(1);
			String rltNum = list.get(i).get(3);
			BigDecimal ciClassId = ccu.getCiClassId(ciClassName);
			assertEquals("关系数量对比 : ", data.getInt(ciClassId.toString()), Integer.parseInt(rltNum));
		}
		
//		BigDecimal sourceCiCode = cu.getCiId(dt.raw().get(1).get(0));
//		ArrayList<BigDecimal> targetClassId = new ArrayList();
//		JSONObject num = new JSONObject();
//		for(int i = 1; i < dt.raw().size(); i++){
//			targetClassId.add(ccu.getCiClassId(dt.raw().get(i).get(1)));
//			num.put(ccu.getCiClassId(dt.raw().get(i).get(1)).toString(), dt.raw().get(i).get(2));
//		}
//		QueryCount qc = new QueryCount();
//		JSONObject result = qc.queryCount(sourceCiCode,targetClassId);
//		assertTrue(result.getBoolean("success"));
//		Iterator<String> it = num.keys();
//		while(it.hasNext()){
//			String key = it.next();
//			assertTrue(num.getString(key).equals(result.getJSONObject("data").getInt(key)+""));
//		}
	}
	
	
	@Then("^删除关系名为\"(.*)\"以及CICODE为\"(.*)\"和\"(.*)\"的CI关系$")
	public void deleteOneRlt(String rltName,String sourceCiCode,String targetCiCode){
		RemoveByCdt rbc = new RemoveByCdt();
		QueryRltClassCiInfoPage qrccip = new QueryRltClassCiInfoPage();
		JSONObject result = rbc.removeByCdt(sourceCiCode, targetCiCode);
		assertTrue(result.getBoolean("success"));
		JSONArray queryResult = qrccip.queryRltClassCiInfoPage(rltName, sourceCiCode, targetCiCode).getJSONObject("data").getJSONArray("data");
		boolean booResult = true;
		for(int i = 0; i < queryResult.length(); i++){
			if(queryResult.getJSONObject(i).getJSONObject("ci").getString("ciCode").equals(targetCiCode)){
				booResult = false;
				break;
			}
		}
		assertTrue(booResult);
	}
	
	@Then("^删除全部关系,删除源ciCode为\"(.*)\",目标ciClass为\"(.*)\",目标ciCode为\"(.*)\",关系名为\"(.*)\"的所有关系$")
	public void deleteAllRlt(String sourceCiCode,String targetClassName,String targetCiCode,String rltClassName){
		RemoveByCdt rbc = new RemoveByCdt();
		JSONObject result = rbc.removeByCdt(sourceCiCode, targetClassName, rltClassName);
		assertTrue(result.getBoolean("success"));
		QueryRltClassCiInfoPage qrccip = new QueryRltClassCiInfoPage();
		assertTrue(qrccip.queryRltClassCiInfoPage(rltClassName, sourceCiCode, targetCiCode).getJSONObject("data").getJSONArray("data").length()==0);
	}
	
	@Then("^验证RCA是否创建成功$")
	public void queryRca(DataTable dt){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryRcaByCiId qrbci = new QueryRcaByCiId();
		RltClassUtil rcu = new RltClassUtil();
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i++){
			String sourceCiCode = list.get(i).get(0);
			JSONObject result = qrbci.queryRcaByCiId(sourceCiCode,Integer.parseInt(list.get(i).get(3)));
			JSONArray tempResult = result.getJSONObject("data").getJSONArray("ciRltLines");
			BigDecimal rltId = rcu.getRltClassId(list.get(i).get(2));
			for(int n = 0; n < tempResult.length(); n++){
				assertTrue(list.get(i).get(0).equals(tempResult.getJSONObject(n).getString("sourceCiCode"))&&list.get(i).get(1).equals(tempResult.getJSONObject(n).getString("targetCiCode"))&&rltId.equals(tempResult.getJSONObject(n).getBigDecimal("classId")));
			}
		}
	}
	
	@Then("^验证depRCA是否创建成功$")
	public void queryDepRca(DataTable dt){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryRcaByCiId qrbci = new QueryRcaByCiId();
		RltClassUtil rcu = new RltClassUtil();
		List<List<String>> list = dt.raw();
		String sourceCiCode = list.get(1).get(0);
		JSONObject result = qrbci.queryRcaByCiId(sourceCiCode,Integer.parseInt(list.get(1).get(3)));
		for(int i = 1; i < list.size(); i++){
			JSONArray tempResult = result.getJSONObject("data").getJSONArray("ciRltLines");
			BigDecimal rltId = rcu.getRltClassId(list.get(i).get(2));
			boolean comp = false;
			for(int n = 0; n < tempResult.length(); n++){
				if(list.get(i).get(0).equals(tempResult.getJSONObject(n).getString("sourceCiCode"))&&list.get(i).get(1).equals(tempResult.getJSONObject(n).getString("targetCiCode"))&&rltId.equals(tempResult.getJSONObject(n).getBigDecimal("classId"))){
					comp = true;
					break;
				}
			}
			assertTrue(comp);
		}

	}
	
	@Then("^查询className为\"(.*)\",关键字为\"(.*)\"的ci$")
	public void queryPage(String ciClassName,String keyWord){
//		{"cdt":{"classId":100000000001098,"ciQ":["ATTR"],"like":"后台流程1"},"pageNum":1,"pageSize":30,"totalPages":2,"totalRows":31}
//		{"data":{"totalCiCount":0,"totalRows":1,"data":[{"attrs":{"CI CODE":"后台流程1","IMPORTANT LEVEL":"重要","NAME":"后台流程","SUPPORTER":"chenaohan","SERVICE TIME":"7*24"},"ci":{"modifier":"管理员[admin]","id":100000000007834,"createTime":20180312052248,"ciCode":"后台流程1","classId":100000000001098,"ownerId":1,"domainId":1,"dataStatus":1,"modifyTime":20180312052248,"sourceId":1,"creator":"管理员[admin]"}}],"pageSize":30,"totalPages":1,"pageNum":1},"code":-1,"success":true}
		QueryPageByIndex qpbi = new QueryPageByIndex();
		JSONObject result = qpbi.queryPageByIndex(ciClassName, keyWord);
		assertTrue(result.getJSONObject("data").getJSONArray("data").getJSONObject(0).getJSONObject("ci").getString("ciCode").equals(keyWord));
	}
	
	@When("^给CI推送多个性能:$")
	public void performanceImportDupli(DataTable dt){
		PerformanceImport performanceImport = new PerformanceImport();
		JSONObject result = performanceImport.performanceImport(dt);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"),-1);
	}
	
	@Then("^成功给CI推送多个性能:$")
	public void checkPerformanceImportDupli(DataTable dt){
		GetPerformanceList getPerformanceList = new GetPerformanceList();
		List<List<String>> list = dt.raw();
		JSONObject result = getPerformanceList.getPerformanceList(list.get(1).get(1));
		JSONArray data = result.getJSONArray("data");
		boolean ciNameFlag = false;
		boolean kpiNameFlag = false;
		boolean kpiValueFlag = false;
		for(int i = 1; i < list.size(); i++){
			String ciName = list.get(i).get(1);
			String kpiName = list.get(i).get(2);
			String kpiValue = list.get(i).get(4);
			for(int j = 0; j < data.length(); j ++){
				JSONObject obj = (JSONObject)data.get(j);
				JSONArray timeAndVals = obj.getJSONArray("timeAndVals");
				
				if(ciName.equals(obj.get("ciName"))){
					ciNameFlag = true;
				}
				if(kpiName.equals(obj.get("kpiName"))){
					kpiNameFlag = true;
				}
				if(kpiValue.substring(0, kpiValue.length()-1).equals(timeAndVals.getJSONObject(0).get("val"))){
					kpiValueFlag = true;
				}
			}
			if (ciNameFlag && kpiNameFlag && kpiValueFlag){
				assertTrue(true);
			}else{
				fail("有值不匹配");
			}
		}
	}
	
   @And ("^删除CICODE为\"(.*)\"的多条性能$")
   public void deleteCiCodePerformance(String ciCode){
	   String sql = "DELETE FROM performance where CI_NAME like '%" + ciCode + "%'";
	   JdbcUtil.executeUpdate(sql);
	   String sql1 = "DELETE FROM performance_current where CI_NAME like'%" + ciCode + "%'";
	   JdbcUtil.executeUpdate(sql1);
   }
   
	/*=============================Event_推送多条告警========================*/
	@When("^给CI推送多个告警:$")
	public void eventImportDupli(DataTable dt){
		EventImport eventImport = new EventImport();
		List <List<String>> list = dt.raw();
		List<JSONObject> pbs = new ArrayList<JSONObject>();
		for(int i = 1; i< list.size();i ++){
			JSONObject obj = new JSONObject();
			obj.put("SourceID", list.get(i).get(1));
			obj.put("SourceEventID", list.get(i).get(2));
			obj.put("SourceCIName", list.get(i).get(3));
			obj.put("SourceAlertKey", list.get(i).get(4));
			obj.put("Severity", list.get(i).get(5));
			obj.put("SourceSeverity", list.get(i).get(6));
			obj.put("Status", list.get(i).get(7));
			obj.put("Summary", list.get(i).get(8));
			obj.put("LastOccurrence", list.get(i).get(9));
			obj.put("tally", "5");
			obj.put("SourceIdentifier", "123");
			pbs.add(obj);
		}
		JSONObject result = eventImport.eventImport(pbs.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"),-1);
	}
	
	@Then("^成功给CI推送多个告警:$")
	public void checkEventImportDupli(DataTable dt){
		QueryEventDetails queryEventDetails = new QueryEventDetails();
		JSONArray ciCodes = new JSONArray();
		List<List<String>> list = dt.raw();
		String ciCode = list.get(1).get(3);
		ciCodes.put(ciCode);
		JSONObject result = queryEventDetails.queryEventDetails(ciCodes);
		JSONArray data = result.getJSONArray("data");
		assertEquals(list.size()-1,data.length());
		boolean ciCodeFlag = false;
		boolean severityFlag = false;
		boolean summaryFlag = false;
		boolean kpiNameFlag = false;
		for(int i = 1; i < list.size(); i ++){
			List eventList = list.get(i);
			String ciCodestr = (String)eventList.get(3);
			String severity = (String)eventList.get(5);
			String summary = (String)eventList.get(8);
			String kpiName = (String)eventList.get(4);
			for(int j = 0; j < data.length(); j ++){
			    JSONObject obj = (JSONObject)data.get(j);
			    if(ciCodestr.equals(obj.getString("ciCode"))){
			    	ciCodeFlag = true;
			    }
				if(Integer.parseInt(severity) == obj.getInt("severity")){
					severityFlag = true;
				}
				if(summary.equals(obj.getString("summary"))){
					summaryFlag = true;
				}
				if(kpiName.equals(obj.getString("kpiName"))){
					kpiNameFlag = true;
				}
			}
			if(ciCodeFlag && severityFlag && summaryFlag && kpiNameFlag){
				assertTrue(true);
			}else{
				fail("有值不匹配");
			}
		}
		
	}
	
	@And ("^删除CICODE为\"(.*)\"的多条告警$")
	public void removemoneapeventmemory(String identifier){
		String sql = "DELETE FROM mon_eap_event_memory WHERE IDENTIFIER like '%" + identifier + "%' and STATUS = 1";
		JdbcUtil.executeUpdate(sql);
	}
	
	
	@Then("^查询故障根因,用以下参数:$")
	public void queryRcaByCiIds(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryRcaByCiId qrbci = new QueryRcaByCiId();
		RltClassUtil rcu = new RltClassUtil();
		List<String> temp = list.get(1);
		List ciCodes = Arrays.asList(temp.get(0).split(":"));
		
//			|ciCodes|rltClassName|depth|
		JSONObject result = qrbci.queryRcaByCiIds(ciCodes, Integer.parseInt(temp.get(2)));
		//由于没有提交代码，先到这里。
		ciRltLinesResult = result.getJSONObject("data").getJSONArray("ciRltLines");
		

//		BigDecimal rltId = rcu.getRltClassId(temp.get(1));
//		boolean comp = false;
//		for(int n = 0; n < tempResult.length(); n++){
//			if(true){
//				comp = true;
//				break;
//			}
//		}
//		assertTrue(comp);
	}
	@Then("^验证故障跟因查询结果,用以下参数:$")
	public void compCiRltLinesResult(DataTable dt){
		List<List<String>> list = dt.raw();
		ClassRltUtil cru = new ClassRltUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltUtil ru = new RltUtil();
		if(ciRltLinesResult != null && ciRltLinesResult.length() > 0){
			if(ciRltLinesResult.length() != list.size()-1) assertTrue("结果关系数量与校验数量不同", false);
			for (int i = 0; i < ciRltLinesResult.length(); i++) {
				JSONObject temp = ciRltLinesResult.getJSONObject(i);
				String sourceCiCode = temp.getJSONObject("ciRlt").getString("sourceCiCode");
				String targetCiCode = temp.getJSONObject("ciRlt").getString("targetCiCode");
				BigDecimal rltClassId = temp.getJSONObject("ciRlt").getBigDecimal("id");
				boolean boo = false;
				for (int j = 1; j < list.size(); j++) {
					List<String> rltList = list.get(j);
					String sourceCiCodeList = rltList.get(0);
					String targetCiCodeList = rltList.get(1);
					BigDecimal rltId = ru.getciRltId(sourceCiCodeList, targetCiCodeList, rltList.get(2));
					if(sourceCiCode.equals(sourceCiCodeList) && targetCiCode.equals(targetCiCodeList) && rltClassId.equals(rltId)){
						boo = true;
					}
//					assertEquals("源ciCode不同:", sourceCiCode, sourceCiCodeList);
//					assertEquals("目标ciCode不同:", targetCiCode, targetCiCodeList);
//					assertEquals("rltId不同:", rltClassId, rltId);
				}
				assertTrue("对比值出现不同:", boo);
			}
		}
		
	}
	
	@Then("^用如下参数查询关系:$")
	public void queryRltInfoList(DataTable dt){
		List<List<String>> list = dt.raw();
		QuerySourceOrTargetClassList qsotcl = new QuerySourceOrTargetClassList();
		for (int i = 1; i < list.size(); i++) {
			List<String> temp = list.get(i);
//			|className|sourceOrtarget|order|queryTargetNames|
			String className = temp.get(0);
			String targetOrSource = temp.get(1);
			String orders = temp.get(2);
			String queryTargetNames = temp.get(3);
			JSONObject result = qsotcl.querySourceOrTargetClassList(className, targetOrSource, orders);
			JSONArray resultData = result.getJSONArray("data");
			String [] queryTargetNameArray = queryTargetNames.split(":");
			for (int j = 0; j < resultData.length(); j++) {
				boolean boo = false;
				JSONObject ciObj = resultData.getJSONObject(j);
				for (int k = 0; k < queryTargetNameArray.length; k++) {
					if(ciObj.getJSONObject("ciClass").getString("className").equals(queryTargetNameArray[k])){
						boo = true;
					}
				}
				assertEquals("查询关系与测试参数做比较:", boo, true);
			}
		}
	}
}
