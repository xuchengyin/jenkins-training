package com.uinnova.test.step_definitions.testcase.base.rlt.ciRlt;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRlt.ExportCiRlt;
import com.uinnova.test.step_definitions.api.base.ciRlt.ExportImportMsg;
import com.uinnova.test.step_definitions.api.base.ciRlt.ImportCiRlt;
import com.uinnova.test.step_definitions.api.base.ciRlt.QueryPage;
import com.uinnova.test.step_definitions.api.base.ciRlt.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.base.ciRlt.RemoveByClassId;
import com.uinnova.test.step_definitions.api.base.ciRlt.RemoveById;
import com.uinnova.test.step_definitions.api.base.ciRltClass.QueryAll;
import com.uinnova.test.step_definitions.api.base.search.ci.QuerySeeSearchKindList;
import com.uinnova.test.step_definitions.api.base.search.ci.SearchInfoList;
import com.uinnova.test.step_definitions.api.base.sys.data.SaveRoleDataAuth;
import com.uinnova.test.step_definitions.api.cmv.ciClass.QueryRltClassAndMarkByClassId;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.AutosaveOrUpdateBatchByRules;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.AutosaveOrUpdateBatchRlt;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryCiRltRuleList;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryRltCiInfoPageByCdt;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.RemoveByRemoveCdts;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.RemoveCurRltAllByCdt;
import com.uinnova.test.step_definitions.api.cmv.progress.GetProgressInfoByKey;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;
import com.uinnova.test.step_definitions.utils.base.SyncToNoahUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_ciRlt {
	JSONArray result = null;
	BigDecimal ciRltId = new BigDecimal(0);
	String filePath = null;
	String exportImportMsgFileName = null;
	JSONObject autoResult = null;
	public List<BigDecimal> ciRltIdList = new ArrayList<BigDecimal>();


	@After("@cleanRlt")
	public void cleanRlt(){
		for (int i=0; i<ciRltIdList.size(); i++){
			BigDecimal id = ciRltIdList.get(i);
			JSONObject res = (new RemoveById()).removeById(ciRltId);
			assertEquals(1, res.getInt("data"));
			ciRltIdList.remove(i);
			i--;
		}
	}

	/*==================关系数据_搜索=================*/
	@Given("^系统中已存在如下ci分类:\"(.*)\"$")
	public void getPreparedCi(@Delimiter(",") List<String> cis){
		for(int i=0;i<cis.size();i++){
			String ciClassName = cis.get(i);
			if((new CiClassUtil()).getCiClassId(ciClassName).compareTo(new BigDecimal(0))==0){
				fail("ci分类:"+ciClassName+" 不存在");
			}
		}
	}


	@And("系统中已存在如下tag分类:\"(.*)\"$")
	public void getPreparedTag(@Delimiter(",") List<String> tags){
		for(int i=0;i<tags.size();i++){
			String tagName = tags.get(i);
			if((new TagRuleUtil()).getTagId(tagName).compareTo(new BigDecimal(0))==0){
				fail();
			}
		}
	}

	@When("^按照如下条件进行筛选:$")
	public void searchCi(List<List<String>> params){
		result = new JSONArray();
		int flag = 0;
		for(int i=1;i<params.size();i++){
			ArrayList<List<String>> paramObj = getParamLine(params,i);
			List<String> clsArr = paramObj.get(0);
			List<String> tagArr = paramObj.get(1);
			List<String> keyArr = paramObj.get(2);

			JSONObject resultObj = (new SearchInfoList()).searchInfoList(clsArr,tagArr,keyArr);
			if(resultObj.getBoolean("success")){
				result.put(resultObj);
				flag++;
			}
		}
		assertTrue(flag==params.size()-1);  
	}

	@Then("^按照如下条件筛选结果正确:$")
	public void checkSearchCi(List<List<String>> params){
		int flag = 0;
		for(int i=1;i<params.size();i++){
			ArrayList<List<String>> paramObj = getParamLine(params,i);
			List<String> clsArr = paramObj.get(0);
			List<String> tagArr = paramObj.get(1);
			List<String> keyArr = paramObj.get(2);
			//获取classId
			String cds =  "";
			if(clsArr!=null && clsArr.size()!=0){
				for(int j=0;j<clsArr.size();j++){
					BigDecimal clsId = (new CiClassUtil()).getCiClassId(clsArr.get(j));
					cds += clsId+",";
				}
			}else{
				QaUtil.report("=====ciClass组为空===");
			}

			//获取tagId
			String tgs =  "";
			if(tagArr!=null && tagArr.size()!=0){
				for(int k=0;k<tagArr.size();k++){
					BigDecimal tagId = (new TagRuleUtil()).getTagId(tagArr.get(k));
					tgs += tagId+",";
				}
			}
			//构建sql
			String sql = "select count(ID) NUM from CC_CI where DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
			if(clsArr!=null && clsArr.size()!=0){
				String cls_sql = " and CLASS_ID in ("+cds.substring(0, cds.length()-1)+") ";
				sql += cls_sql;
			}
			if(tagArr!=null && tagArr.size()!=0){
				String tag_sql = " and ID in (select CI_ID from CC_CI_TAG where TAG_ID in ("+ tgs.substring(0, tgs.length()-1) +")  and DOMAIN_ID = "+ QaUtil.domain_id +" group by CI_ID ";
				sql += tag_sql;
				if(tagArr.size()>1) {
					sql +=  "having count(1)>="+tagArr.size();
				}
				sql += " )";
			}
			if(keyArr!=null && keyArr.size()!=0){
				for(String key:keyArr){
					String key_sql = " and ID in (select ID from CC_CI_PRO_INDEX where CI_TYPE=1 and IDX_0 like '%"+key+"%'  and DOMAIN_ID = "+ QaUtil.domain_id+" )";
					sql += key_sql;
				}
			}

			int count_api = result.getJSONObject(i-1).getJSONObject("data").getInt("totalCount");
			ArrayList sqlResult = JdbcUtil.executeQuery(sql);
			HashMap map = (HashMap) sqlResult.get(0);

			int count_sql = Integer.parseInt(map.get("NUM").toString());
			QaUtil.report("====sql条数====="+count_sql+"===接口条数==="+count_api);
			QaUtil.report("====第几次循环====="+i);
			assertEquals(count_api, count_sql);
			if(count_api==count_sql){
				flag++;
			}else {
				fail();
			}
		}
		assertEquals(flag, params.size()-1);
	}

	/*==================关系数据_获取分类和tag列表=================*/
	@When("^获取系统所有ciClass和tag列表$")
	public void getTypeAndTagList(){
		SaveRoleDataAuth srda = new SaveRoleDataAuth();
		result =  new JSONArray();
		JSONObject autoObj = new JSONObject("{\"dataAuths\":[{\"id\":1,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":1,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"数据中心\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"数据中心\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"数据中心\",\"classPath\":\"#1#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":2,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":2,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"园区\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"园区\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"园区\",\"classPath\":\"#2#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":3,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":3,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"建筑\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"建筑\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"建筑\",\"classPath\":\"#3#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":4,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":4,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"楼层\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"楼层\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"楼层\",\"classPath\":\"#4#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":5,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":5,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"房间\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"房间\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"房间\",\"classPath\":\"#5#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":6,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":6,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"组\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"组\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"组\",\"classPath\":\"#6#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":7,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":7,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"机柜\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"机柜\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"机柜\",\"classPath\":\"#7#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":8,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":8,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"架式设备\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"架式设备\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"架式设备\",\"classPath\":\"#8#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":9,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":9,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"虚拟机\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"虚拟机\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"虚拟机\",\"classPath\":\"#9#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":10,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":10,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"独立设备\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"独立设备\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"独立设备\",\"classPath\":\"#10#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":11,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":11,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"小品\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"小品\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"小品\",\"classPath\":\"#11#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":12,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":12,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"放样物体\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"放样物体\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"放样物体\",\"classPath\":\"#12#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":13,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":13,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"放样物体集合\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"放样物体集合\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"放样物体集合\",\"classPath\":\"#13#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":14,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":14,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"门\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"门\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"门\",\"classPath\":\"#14#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":15,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":15,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"窗户\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"窗户\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"窗户\",\"classPath\":\"#15#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":16,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":16,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"漏水线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"漏水线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"漏水线\",\"classPath\":\"#16#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":17,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":17,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"管线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"管线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"管线\",\"classPath\":\"#17#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":18,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":18,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"管路\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"管路\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"管路\",\"classPath\":\"#18#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":19,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":19,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"刀片\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"刀片\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"刀片\",\"classPath\":\"#19#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":20,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":20,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"板卡\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"板卡\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"板卡\",\"classPath\":\"#20#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":21,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":21,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"库存\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"库存\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"库存\",\"classPath\":\"#21#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":23,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":9,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":23,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"配线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"配线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"配线\",\"classPath\":\"#23#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":24,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":24,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"MANUALOBJECT\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"ManualObject\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"ManualObject\",\"classPath\":\"#24#\",\"modifyTime\":20170522161616},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":25,\"dataType\":2,\"ciClass\":{\"createTime\":20171009103121,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin\",\"id\":25,\"modifier\":\"admin\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"GEOPOINT\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"GeoPoint\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"GeoPoint\",\"classPath\":\"#25#\",\"modifyTime\":20171009103121},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":26,\"dataType\":2,\"ciClass\":{\"createTime\":20171009103130,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin\",\"id\":26,\"modifier\":\"admin\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"GEOLINE\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"GeoLine\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"GeoLine\",\"classPath\":\"#26#\",\"modifyTime\":20171009103130},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":100000000000247,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000247,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"APPLICATION\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"Application\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"Application\",\"classPath\":\"#100000000000247#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":100000000000248,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000248,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"CLUSTER\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"Cluster\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"Cluster\",\"classPath\":\"#100000000000248#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":100000000000249,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000249,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"S@&_-\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"s@&_-\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"s@&_-\",\"classPath\":\"#100000000000249#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":true,\"edit\":true,\"delete\":true},{\"id\":100000000000064,\"dataType\":1,\"see\":true,\"edit\":true,\"delete\":true,\"tagDef\":{\"modifier\":\"管理员\",\"id\":100000000000064,\"createTime\":20180426213802,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"APP\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000065,\"dataType\":1,\"see\":true,\"edit\":true,\"delete\":true,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000065,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"Clu\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000066,\"dataType\":1,\"see\":true,\"edit\":true,\"delete\":true,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000066,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"@&_-~！@#￥%……&*（））——，\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000067,\"dataType\":1,\"see\":true,\"edit\":true,\"delete\":true,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000067,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"【、；‘。、《》？’】234567是￥\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000028,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000029,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000030,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000031,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000032,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000033,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000034,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true},{\"id\":100000000000035,\"dataType\":3,\"see\":false,\"edit\":true,\"delete\":true}],\"roleId\":1}");
		JSONObject autoResult = srda.saveRoleDataAuth(autoObj);
		assertTrue(autoResult.getBoolean("success"));
		JSONObject res = (new QuerySeeSearchKindList()).querySeeSearchKindList();
		result.put(res);
		assertTrue(((JSONObject)result.get(0)).getJSONArray("data").length()>0);

		autoObj = new JSONObject("{\"dataAuths\":[{\"id\":1,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":1,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"数据中心\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"数据中心\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"数据中心\",\"classPath\":\"#1#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":2,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":2,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"园区\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"园区\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"园区\",\"classPath\":\"#2#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":3,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":3,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"建筑\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"建筑\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"建筑\",\"classPath\":\"#3#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":4,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":4,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"楼层\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"楼层\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"楼层\",\"classPath\":\"#4#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":5,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":5,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"房间\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"房间\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"房间\",\"classPath\":\"#5#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":6,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":6,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"组\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"组\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"组\",\"classPath\":\"#6#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":7,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":7,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"机柜\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"机柜\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"机柜\",\"classPath\":\"#7#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":8,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":8,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"架式设备\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"架式设备\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"架式设备\",\"classPath\":\"#8#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":9,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":9,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"虚拟机\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"虚拟机\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"虚拟机\",\"classPath\":\"#9#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":10,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":10,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"独立设备\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"独立设备\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"独立设备\",\"classPath\":\"#10#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":11,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":11,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"小品\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"小品\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"小品\",\"classPath\":\"#11#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":12,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":12,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"放样物体\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"放样物体\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"放样物体\",\"classPath\":\"#12#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":13,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":13,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"放样物体集合\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"放样物体集合\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"放样物体集合\",\"classPath\":\"#13#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":14,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":14,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"门\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"门\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"门\",\"classPath\":\"#14#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":15,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":15,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"窗户\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"窗户\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"窗户\",\"classPath\":\"#15#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":16,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":16,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"漏水线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"漏水线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"漏水线\",\"classPath\":\"#16#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":17,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":17,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"管线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"管线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"管线\",\"classPath\":\"#17#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":18,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":18,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"管路\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"管路\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"管路\",\"classPath\":\"#18#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":19,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":19,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"刀片\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"刀片\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"刀片\",\"classPath\":\"#19#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":20,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":20,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"板卡\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"板卡\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"板卡\",\"classPath\":\"#20#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":21,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":21,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"库存\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"库存\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"库存\",\"classPath\":\"#21#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":23,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":9,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":23,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"配线\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"配线\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"配线\",\"classPath\":\"#23#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":24,\"dataType\":2,\"ciClass\":{\"createTime\":20170522161616,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin[init]\",\"id\":24,\"modifier\":\"admin[init]\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"MANUALOBJECT\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"ManualObject\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"ManualObject\",\"classPath\":\"#24#\",\"modifyTime\":20170522161616},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":25,\"dataType\":2,\"ciClass\":{\"createTime\":20171009103121,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin\",\"id\":25,\"modifier\":\"admin\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"GEOPOINT\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"GeoPoint\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"GeoPoint\",\"classPath\":\"#25#\",\"modifyTime\":20171009103121},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":26,\"dataType\":2,\"ciClass\":{\"createTime\":20171009103130,\"icon\":\"/122/52515.png\",\"classLvl\":1,\"dirId\":1,\"creator\":\"admin\",\"id\":26,\"modifier\":\"admin\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"GEOLINE\",\"isLeaf\":1,\"domainId\":1,\"dataStatus\":1,\"classCode\":\"GeoLine\",\"classColor\":\"rgb(85, 168, 253)\",\"className\":\"GeoLine\",\"classPath\":\"#26#\",\"modifyTime\":20171009103130},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000247,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000247,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"APPLICATION\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"Application\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"Application\",\"classPath\":\"#100000000000247#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000248,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000248,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"CLUSTER\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"Cluster\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"Cluster\",\"classPath\":\"#100000000000248#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000249,\"dataType\":2,\"ciClass\":{\"createTime\":20180426213802,\"icon\":\"/122/52430.png\",\"classLvl\":1,\"dirId\":101,\"creator\":\"管理员\",\"id\":100000000000249,\"modifier\":\"管理员\",\"ciType\":1,\"parentId\":0,\"classStdCode\":\"S@&_-\",\"domainId\":1,\"isLeaf\":1,\"dataStatus\":1,\"classCode\":\"s@&_-\",\"classColor\":\"rgb(85,168,253)\",\"className\":\"s@&_-\",\"classPath\":\"#100000000000249#\",\"modifyTime\":20180426213802,\"classDesc\":\"\"},\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000064,\"dataType\":1,\"see\":false,\"edit\":false,\"delete\":false,\"tagDef\":{\"modifier\":\"管理员\",\"id\":100000000000064,\"createTime\":20180426213802,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"APP\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000065,\"dataType\":1,\"see\":false,\"edit\":false,\"delete\":false,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000065,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"Clu\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000066,\"dataType\":1,\"see\":false,\"edit\":false,\"delete\":false,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000066,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"@&_-~！@#￥%……&*（））——，\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000067,\"dataType\":1,\"see\":false,\"edit\":false,\"delete\":false,\"tagDef\":{\"modifier\":\"system[system]\",\"id\":100000000000067,\"createTime\":20180426213803,\"tagType\":1,\"buildStatus\":1,\"tagName\":\"【、；‘。、《》？’】234567是￥\",\"domainId\":1,\"dataStatus\":1,\"isValid\":1,\"modifyTime\":20180426213803,\"dirId\":100000000000033,\"creator\":\"管理员\"}},{\"id\":100000000000028,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000029,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000030,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000031,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000032,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000033,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000034,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false},{\"id\":100000000000035,\"dataType\":3,\"see\":false,\"edit\":false,\"delete\":false}],\"roleId\":1}");
		autoResult = srda.saveRoleDataAuth(autoObj);
		assertTrue(autoResult.getBoolean("success"));
	}

	@Then("^返回结果中包含所有ciClass和tag$")
	public void checkGetTypeAndTagList(){
		//获取ciclass 和tag id列表
		String sql_ciClass = "SELECT ID FROM cc_ci_class c where DATA_STATUS=1 and CI_TYPE=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		String sql_tag = "SELECT ID FROM cc_ci_tag_def c where DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList ciClsList = JdbcUtil.executeQuery(sql_ciClass);
		ArrayList tagList = JdbcUtil.executeQuery(sql_tag);

		ArrayList<String> ids = new ArrayList();
		for(int i=0;i<ciClsList.size();i++ ){
			HashMap map = (HashMap)ciClsList.get(i);
			ids.add(map.get("ID").toString());
		}
		QaUtil.report("====ciClsList===="+ciClsList);
		for(int j=0;j<tagList.size();j++ ){
			HashMap map = (HashMap)tagList.get(j);
			ids.add(map.get("ID").toString());
		}
		QaUtil.report("====tagList===="+tagList);
		JSONArray data = (new QuerySeeSearchKindList()).querySeeSearchKindList().getJSONArray("data");
		String id = null;
		int flag = 0 ;
		for(int k=0;k<data.length();k++){
			id = String.valueOf(data.getJSONObject(k).getBigDecimal("id"));
			if(ids.contains(id)){
				flag++;
			}
		}
		assertEquals(flag, data.length());
	}


	public ArrayList<List<String>> getParamLine(List<List<String>> params,int lineNum){
		ArrayList lineObj =  new ArrayList<String>();
		ArrayList<String> clsArr = new ArrayList<String>();
		ArrayList<String> tagArr = new ArrayList<String>();
		ArrayList<String> keyArr = new ArrayList<String>();
		String cls = params.get(lineNum).get(1);
		String tags = params.get(lineNum).get(2);
		String keys = params.get(lineNum).get(3);
		if(!cls.isEmpty()){
			String[] cl = cls.split("、");
			for(int j=0;j<cl.length;j++){
				clsArr.add(cl[j]);
			}
		}else{
			if(!keys.isEmpty()){
				clsArr.add(null);
			}
			clsArr = null;
		}
		if(!tags.isEmpty()){
			String[] tag = tags.split("、");
			for(int j=0;j<tag.length;j++){
				tagArr.add(tag[j]);
			}
		}else{
			tagArr = null;
		}
		if(!keys.isEmpty()){
			String[] key = keys.split("、");
			for(int j=0;j<key.length;j++){
				keyArr.add(key[j]);
			}
		}else{
			keyArr = null;
		}
		lineObj.add(clsArr);
		lineObj.add(tagArr);
		lineObj.add(keyArr);
		return lineObj;
	}


	@And("^系统中已存在如下关系分类:\"(.*)\"$")
	public void CheckPreparedRltClass(@Delimiter(",") List<String> rltClsNames){
		int flag=0;
		for(String rltClsName :rltClsNames){
			BigDecimal rltId = (new RltClassUtil()).getRltClassId(rltClsName);
			if(rltId.compareTo(new BigDecimal(0))!=0){
				flag++;
			}
		}
		assertEquals(flag, rltClsNames.size());
	}
	/*==================关系数据_增查删=================*/
	@Given("^在关系分类\"(.*)\"下,存在源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"的关系数据$")
	public void existsRltData(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
		RltUtil rltUtil = new RltUtil();
		if(!rltUtil.isExistsRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode)){
			fail("关系分类:"+rltClsName+"下"+sourceCiCode+"到"+targetCiCode+"的关系数据不存在");
		}
	}
	
	@When("^在关系分类\"(.*)\"下,创建源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"关系数据$")
	public void createRltData(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
		JSONObject result = (new com.uinnova.test.step_definitions.api.base.ciRlt.SaveOrUpdate()).saveOrUpdate(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode);
		assertTrue(result.getBigDecimal("data").compareTo(new BigDecimal(0))>0);
		ciRltId = result.getBigDecimal("data");
		ciRltIdList.add(ciRltId);
		//以下代码确认关系数据同步到noah
		assertTrue((new SyncToNoahUtil()).syncCiRltToNoah(rltClsName, sourceCiCode, targetCiCode));
	}

	@Then("^\"(.*)\"下存在只存在1条\"(.*)\"与\"(.*)\"的关系数据$")
	public void checkCreateCiRlt(String rltName,String sourceCiCode,String targetCiCode){
		JSONObject qk = (new QueryPageByIndex()).queryPageByIndex_rltByKeyword(rltName, sourceCiCode);
		int totalRows = qk.getJSONObject("data").getInt("totalRows");
		if(totalRows==0){
			int i = 0;
			while(totalRows==0){
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				qk = (new QueryPageByIndex()).queryPageByIndex_rltByKeyword(rltName, sourceCiCode);
				totalRows = qk.getJSONObject("data").getInt("totalRows");
				i++;
			}
		}
		int flag = 0;

		if(totalRows>0){
			for(int i=0;i<totalRows;i++){
				JSONObject data  =   qk.getJSONObject("data").getJSONArray("data").getJSONObject(i);
				String sourceCi =  data.getJSONObject("sourceCiInfo").getJSONObject("ci").getString("ciCode");
				String targetCi =  data.getJSONObject("targetCiInfo").getJSONObject("ci").getString("ciCode");
				if(sourceCi.equals(sourceCiCode) && targetCi.equals(targetCiCode)){
					QaUtil.report("==source=="+sourceCi+" "+sourceCiCode+"或者=target=="+targetCi+" "+targetCiCode+"===相等");
					ciRltId = data.getJSONObject("ciRlt").getBigDecimal("id");
					flag++;

				}else{
					QaUtil.report("==source=="+sourceCi+" "+sourceCiCode+"或者=target=="+targetCi+" "+targetCiCode+"===不相等");
					continue;
				}
			}
		}
		QaUtil.report("==="+sourceCiCode+"与"+targetCiCode+"的"+rltName+"关系创建成功===");
		assertEquals(1, flag);
	}

	@When("^在关系分类\"(.*)\"下,创建源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"关系数据,属性为:$")
	public void createRltDataAndAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		JSONObject result = (new com.uinnova.test.step_definitions.api.base.ciRlt.SaveOrUpdate()).saveOrUpdateHaveAttr(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode, table);
		assertTrue(result.getBigDecimal("data").compareTo(new BigDecimal(0))>0);
		ciRltId = result.getBigDecimal("data");
		ciRltIdList.add(ciRltId);
		//以下代码确认关系数据同步到noah
		assertTrue((new SyncToNoahUtil()).syncCiRltToNoah(rltClsName, sourceCiCode, targetCiCode));
	}

	@Then("^成功在关系分类\"(.*)\"下,创建源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"关系数据,属性为:$")
	public void checkCreateRltDataAndAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		RltUtil ru = new RltUtil();
		JSONObject rltData = ru.getRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode, table);
		assertNotNull(rltData);
	}

	@When("^在关系分类\"(.*)\"下,源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"关系数据,修改属性为:$")
	public void updateRltDataAndAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		JSONObject result = (new com.uinnova.test.step_definitions.api.base.ciRlt.SaveOrUpdate()).updateHaveAttr(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode, table);
		assertTrue(result.getBigDecimal("data").compareTo(new BigDecimal(0))>0);
	}

	@Then("^在关系分类\"(.*)\"下,源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"关系数据,修改如下属性成功$")
	public void checkUpdateRltDataAndAttr(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		RltUtil ru = new RltUtil();
		JSONObject rltData = ru.getRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode, table);
		assertNotNull(rltData);
	}

	/**
	 * 通过ID删除
	 * @param rltClsName
	 * @param keyword
	 */
	@When("^删除\"(.*)\"关系下,属性值前匹配\"(.*)\"的关系数据$")
	public void delCiRlt(String rltClsName,String keyword){
		JSONObject res = (new RemoveById()).removeById(ciRltId);
		assertEquals(1, res.getInt("data"));
		ciRltIdList.remove(ciRltId);
	}

	@Then("^\"(.*)\"关系下,不存在属性值前匹配\"(.*)\"的关系数据$")
	public void checkDelCiRlt(String rltClsName,String keyword){
		// 获取关系下所有数据的id
		JSONObject qp = (new QueryPage()).queryPage(rltClsName);
		JSONArray data = qp.getJSONObject("data").getJSONArray("data");
		int dataNum = qp.getJSONObject("data").getJSONArray("data").length();
		//判断不包含此id
		for(int i=0;i<dataNum;i++){
			BigDecimal ciId = ((JSONObject)data.get(i)).getJSONObject("ciRlt").getBigDecimal("id");
			if(ciRltId.compareTo(ciId)==0){
				fail();
			}
		}
		QaUtil.report("======关系数据"+ciRltId+"删除成功====");
		assertTrue(true);
	}

	@When("^删除\"(.*)\"关系下,源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"的关系数据$")
	public void delCiRltByDetail(String rltClsName,String sourceCiClass, String sourceCiCode, String targetCiClass,String targetCiCode){
		RltUtil rltUtil = new RltUtil();
		JSONObject result = rltUtil.getRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode, null);
		BigDecimal delCiRltId = result.getJSONObject("ciRlt").getBigDecimal("id");
		JSONObject res = (new RemoveById()).removeById(delCiRltId);
		assertEquals(1, res.getInt("data"));
		ciRltIdList.remove(delCiRltId);
	}

	@Then("^\"(.*)\"关系下,不存在源分类为\"(.*)\",源对象为\"(.*)\",目标分类为\"(.*)\",目标对象为\"(.*)\"的关系数据$")
	public void checkDelCiRltByDetail(String rltClsName,String sourceCiClass, String sourceCiCode, String targetCiClass,String targetCiCode){
		RltUtil rltUtil = new RltUtil();
		assertFalse(rltUtil.isExistsRltData(rltClsName, sourceCiClass, sourceCiCode, targetCiClass, targetCiCode));
	}



	/*==================关系数据_下载模板=================*/
	@When("^下载关系分类\"(.*)\"的关系数据模板$")
	public void downlodRltMould(String rltClsName){
		filePath = (new ExportCiRlt()).exportCiRltMould(rltClsName);
		assertTrue((new File(filePath)).exists());
	}

	@Then("^\"(.*)\"的关系数据模板下载成功$")
	public void checkDownloadRltMould(String rltClsName){
		JSONArray excelData = (new ExcelUtil()).readFromExcel(filePath, rltClsName);
		if(excelData.length()==1){
			QaUtil.report("===excel表数据校验成功===");
			assertTrue(true);
		}
	}

	/*==================关系数据_下载当前分类数据=================*/

	@When("^下载当前关系分类\"(.*)\"的关系数据$")
	public void downloadCurrentciRltData(String rltClsName){
		filePath = (new ExportCiRlt()).exportCiRlt_currentCiRltCls(rltClsName);
		assertTrue((new File(filePath)).exists());
	}
	@Then("^当前关系分类\"(.*)\"的数据下载成功$")
	public void checkDownloadCurrentciRltData(String rltClsName){
		JSONArray excelData = (new ExcelUtil()).readFromExcel(filePath, rltClsName);
		JSONObject apiData = (new QueryPage()).queryPage(rltClsName);
		int flag = 0;
		int totalRows =  ((JSONObject)apiData.getJSONObject("data")).getInt("totalRows");
		System.out.println(apiData.getJSONObject("data").getJSONArray("data").toString());
		for(int i=0;i<totalRows;i++){
			JSONObject apiDatObj = (JSONObject)apiData.getJSONObject("data").getJSONArray("data").get(i);
			String  apiSourceObj = apiDatObj.getJSONObject("sourceCiInfo").getJSONObject("ci").getString("ciCode");
			String  apiTargetObj = apiDatObj.getJSONObject("targetCiInfo").getJSONObject("ci").getString("ciCode");

			String excelSourceObj = ((JSONObject)excelData.get(i+1)).getString(String.valueOf(2));
			String excelTargetObj= ((JSONObject)excelData.get(i+1)).getString(String.valueOf(5));

			if(excelSourceObj.equals(apiSourceObj) && excelTargetObj.equals(apiTargetObj)){
				QaUtil.report("===="+rltClsName+"分类====第"+(flag+1)+"行数据相同======");
				flag++;
			}else{
				QaUtil.report("====excel:"+excelSourceObj+" "+excelTargetObj+"与api结果==="+apiSourceObj+" "+apiTargetObj+"不相等===");
			}
		}
		assertTrue(flag==totalRows);
	}

	/*==================关系数据_下载全部关系数据=================*/

	@When("^下载全部关系数据$")
	public void downloadAllRltData(){
		filePath =(new ExportCiRlt()).exportCiRlt_allCiRltCls();
		assertTrue((new File(filePath)).exists());
	}

	@Then("^全部关系数据下载成功$")
	public void checkDownloadRltData(){
		JSONArray datas = (new QueryAll()).queryAll().getJSONArray("data");
		for(Object data :datas){
			JSONObject dataObj = (JSONObject) data;
			String rltClsName = dataObj.getJSONObject("ciClass").getString("classCode");
			checkDownloadCurrentciRltData(rltClsName);
		}
	}

	@When("^清除关系分类\"(.*)\"下的所有数据$")
	public void delAllRltData(String rltClsName){
		JSONObject result = (new RemoveByClassId()).removeByClassId(rltClsName);
		assertTrue(result.getInt("data")>0);
	}

	@And("^给关系分类\"(.*)\"下导入关系数据$")
	public void importRltData(String rltClsName){
		String result = (new ImportCiRlt()).importCiRlt(rltClsName, filePath);
		JSONObject obj = new JSONObject(result);
		exportImportMsgFileName = obj.getString("data");
	}
	@Then("^用以下数据验证导入关系报告:$")
	public void exportAndImportMsg(DataTable dt){
		ExportImportMsg eim = new ExportImportMsg();
		TxtUtil tu = new TxtUtil();
		String filePath = eim.exportImportMsg(exportImportMsgFileName);
		String result =  tu.readTxt(filePath);
		QaUtil.report(result+"------"+dt.raw().get(1).get(0));
		assertTrue(result.indexOf(dt.raw().get(1).get(0)) > 0);
	}

	@Then("^\"(.*)\"分类的关系数据导入成功$")
	public void checkImportRltData(String rltClsName){
		JSONArray excelData = (new ExcelUtil()).readFromExcel(filePath, rltClsName);
		JSONObject apiData = (new QueryPage()).queryPage(rltClsName);
		int flag = 0;
		int totalRows =  ((JSONObject)apiData.getJSONObject("data")).getInt("totalRows");
		QaUtil.report("==excelRows=="+(excelData.length()-1)+"====apiRows"+totalRows+"======");
		assertTrue((excelData.length()-1)==totalRows);
	}
	
	
	@Then("^自动构建关系,用如下数据:$")
	public void autoCreatRlt(DataTable dt){
		List<List<String>> list = dt.raw();
//		|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
//		|自动构建CI关系|sourceClass|targetClass|文本|文本|
		
		AutosaveOrUpdateBatchByRules aoubbr = new AutosaveOrUpdateBatchByRules();
		for (int i = 1; i < list.size(); i++) {
			String rltClassName = list.get(i).get(0);
			String sourceClassName = list.get(i).get(1);
			String targetClassName = list.get(i).get(2);
			String sourceAttrName = list.get(i).get(3);
			String targetAttrName = list.get(i).get(4);
			List sourceAttrNames =  Arrays.asList(sourceAttrName.split(":"));
			List targetAttrNames =  Arrays.asList(targetAttrName.split(":"));
			autoResult = aoubbr.autosaveOrUpdateBatchByRules(rltClassName, sourceClassName, targetClassName, sourceAttrNames, targetAttrNames);
			assertTrue(autoResult.getBoolean("success"));
		}
		//{"code":-1,"data":[13,0],"success":true}
	}
	@Then("^成功自动构建关系\"(.*)\",更新关系\"(.*)\"$")
	public void autoCreateRltSuccess(String createNum, String updateNum){
		int create = Integer.parseInt(createNum);
		int update = Integer.parseInt(updateNum);
		JSONArray data = autoResult.getJSONArray("data");
		if(data.length() != 2)assertTrue("自动化构建返回值不是2个! : ", false);
		assertEquals("自动创建数量与检测值不同(创建): ", data.getInt(0), create);
		assertEquals("自动创建数量与检测值不同(更新): ", data.getInt(1), update);
	}
	
	@Then("^不分页查询自动构建关系数据的规则信息,用如下参数:$")
	public void queryAutoCreateRltRule(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryCiRltRuleList qcrrl = new QueryCiRltRuleList();
		ClassRltUtil cru = new ClassRltUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltClassUtil rcu = new RltClassUtil();

		JSONObject result = null;
		for (int i = 1; i < list.size(); i++) {
			String rltClassName = list.get(i).get(0);
			String sourceCiClassNames = list.get(i).get(1);
			String targetCiClassNames = list.get(i).get(2);
			int type = Integer.parseInt(list.get(i).get(5));
			List<String> sourceCiClassName = Arrays.asList(sourceCiClassNames.split(":"));//这里注意，虽然做了分割，但是这个只支持一个
			List<String> targetCiClassName = Arrays.asList(targetCiClassNames.split(":"));
			result = qcrrl.queryCiRltRuleList(rltClassName, sourceCiClassName, targetCiClassName, type);
		}
		assertTrue(result.getBoolean("success"));

		JSONArray data = result.getJSONArray("data");
		assertEquals("返回值data中数据长度有变化 :", data.length(),1);
		for (int i = 0; i < data.length(); i++) {
			JSONObject temp = data.getJSONObject(i);
			for (int j = 1; j < list.size(); j++) {
				String rltClassName = list.get(j).get(0);
				String sourceCiClassNames = list.get(j).get(1);
				String targetCiClassNames = list.get(j).get(2);
				String sourceAttrName = list.get(j).get(3);
				String targetAttrName = list.get(j).get(4);
				BigDecimal classRltId = cru.getClassRltId(sourceCiClassNames, targetCiClassNames, rltClassName);
				BigDecimal rltClassId = rcu.getRltClassId(rltClassName);
				BigDecimal sourceCiClassId = ccu.getCiClassId(sourceCiClassNames);//这里需要注意，可能有问题，因为传入的是list，但是返回的结果中不是list
				BigDecimal targetCiClassId = ccu.getCiClassId(targetCiClassNames);//这里需要注意，可能有问题
				BigDecimal sourceDefId = ccu.getAttrIdByAttrName(sourceCiClassNames, sourceAttrName);//这里需要注意，可能有问题
				BigDecimal targetDefId = ccu.getAttrIdByAttrName(targetCiClassNames, targetAttrName);//这里需要注意，可能有问题
				assertEquals("对比classRltId :", classRltId, temp.getBigDecimal("classRltId"));
				assertEquals("对比classId :", rltClassId, temp.getBigDecimal("classId"));
				assertEquals("对比targetDefId :", targetDefId, temp.getBigDecimal("targetDefId"));
				assertEquals("对比sourceDefId :", sourceDefId, temp.getBigDecimal("sourceDefId"));
				assertEquals("对比targetClassId :", targetCiClassId, temp.getBigDecimal("targetClassId"));
				assertEquals("对比sourceClassId :", sourceCiClassId, temp.getBigDecimal("sourceClassId"));
			}
		}
	}
	
	
	@Then("^不分方向查询分类关系:$")
	public void queryRltClassEachother(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryRltClassAndMarkByClassId qrcambci = new QueryRltClassAndMarkByClassId();
		
		for (int i = 1; i < list.size(); i++) {
			String sourceClassName = list.get(i).get(1);
			String targetClassName = list.get(i).get(2);
			String orders = list.get(i).get(3);
			String rltClassName = list.get(i).get(4);
			String classMark = list.get(i).get(5);
			String kw = list.get(i).get(6);
			if(list.get(i).get(0).equals("正常")){
				JSONObject result = qrcambci.queryRltClassAndMarkByClassId(sourceClassName, targetClassName, orders);
				JSONArray data = result.getJSONArray("data");
				for (int j = 0; j < data.length(); j++) {
					JSONObject temp = data.getJSONObject(j);
					assertEquals("对比分类名字:", temp.getJSONObject("rltClass").getString("className"), rltClassName);
					assertEquals("对比classMark信息:", temp.getString("classMark"), classMark);
				}
			}else if(list.get(i).get(0).equals("错误的源分类")){
				JSONObject result = qrcambci.queryRltClassAndMarkByClassId(sourceClassName, targetClassName, orders);
				assertEquals("对比返回值data长度 :", result.getJSONArray("data").length(), 0);

			}else if(list.get(i).get(0).equals("错误的目标分类")){
				JSONObject result = qrcambci.queryRltClassAndMarkByClassId(sourceClassName, targetClassName, orders);
				assertEquals("对比返回值data长度 :", result.getJSONArray("data").length(), 0);

			}else if(list.get(i).get(0).equals("错误的排序")){
				qrcambci.queryRltClassAndMarkByClassIdFailed(sourceClassName, targetClassName, orders, kw);

			}
		}
	}
	
	
	@Then("^根据关系条件不分关系方向分页查询关系中目标或者源的CI信息:$")
	public void queryRltClassByCdt(DataTable dt){
		List<List<String>> list = dt.raw();
		QueryRltCiInfoPageByCdt qrcipbc = new QueryRltCiInfoPageByCdt();
		for (int i = 1; i < list.size(); i++) {
			String rltClassName = list.get(i).get(0);
			String ciCode = list.get(i).get(1);
			String className = list.get(i).get(2);
			String orders = list.get(i).get(3);
			String targetCiCode = list.get(i).get(4);
			String resultLength = list.get(i).get(5);
			JSONObject result = qrcipbc.queryRltCiInfoPageByCdt(rltClassName, ciCode, className, orders);
			JSONArray data = result.getJSONObject("data").getJSONArray("data");
			assertEquals("结果长度对比", Integer.parseInt(resultLength), data.length());
			boolean boo = false;
			for (int j = 0; j < data.length(); j++) {
				JSONObject temp = data.getJSONObject(j);
				if(temp.getJSONObject("ci").getString("ciCode").equals(targetCiCode)) boo = true;
			}
			assertTrue("对比结果中ciCode :", boo);
		}
	}
	
	
	@Then("^配置查询,删除单条关系,用如下数据：$")
	public void configureQueryDeleteRltbyCdt(DataTable dt){
		List<List<String>> list = dt.raw();
		RemoveByRemoveCdts rbrc = new RemoveByRemoveCdts();
		for (int i = 1; i < list.size(); i++) {
			String commont = list.get(i).get(0);
			String rltClassName = list.get(i).get(1);
			String sourceCiCode = list.get(i).get(2);
			String targetCiCode = list.get(i).get(3);
			String deleteNum = list.get(i).get(4);
			String kw = list.get(i).get(5);
			List rltClassNames = Arrays.asList(rltClassName.split(":"));
			List sourceCiCodes = Arrays.asList(sourceCiCode.split(":"));
			List targetCiCodes = Arrays.asList(targetCiCode.split(":"));
			if(commont.equals("正常")){
				JSONObject result = rbrc.removeByRemoveCdts(rltClassNames, sourceCiCodes, targetCiCodes);
//				System.out.println(result);
//				assertEquals("判断删除条数: ", result.getInt("data"), Integer.parseInt(deleteNum));
				/**
				 * @author ldw
				 * 修改接口返回值为1<--1删除成功，0删除失败
				 * */
				assertEquals("判断删除成功: ", result.getInt("data"), 1);
				
			}else if(commont.equals("错误rltClassName")){
				JSONObject result = rbrc.removeByRemoveCdts(rltClassNames, sourceCiCodes, targetCiCodes);
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}else if(commont.equals("错误sourceCiCode")){
				JSONObject result = rbrc.removeByRemoveCdts(rltClassNames, sourceCiCodes, targetCiCodes);
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}else if(commont.equals("错误targetCiCode")){
				JSONObject result = rbrc.removeByRemoveCdts(rltClassNames, sourceCiCodes, targetCiCodes);
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}
		}
	}
	
	
	@Then("^配置查询,删除当前关系,用如下数据：$")
	public void configureQueryDeleteRltbyCdtByCiClass(DataTable dt){
		List<List<String>> list = dt.raw();
		RemoveCurRltAllByCdt rcrabc = new RemoveCurRltAllByCdt();
		for (int i = 1; i < list.size(); i++) {
			String commont = list.get(i).get(0);
			String rltClassName = list.get(i).get(1);
			String sourceCiCode = list.get(i).get(2);
			String targetCiClass = list.get(i).get(3);
			String deleteNum = list.get(i).get(4);
			String kw = list.get(i).get(5);
			if(commont.equals("正常")){
				JSONObject result = rcrabc.removeCurRltAllByCdt(rltClassName, sourceCiCode, targetCiClass);
//				System.out.println(result);
//				assertEquals("判断删除条数: ", result.getInt("data"), Integer.parseInt(deleteNum));
				/**
				 * @author ldw
				 * 修改接口返回值为1<--1删除成功，0删除失败
				 * */
				assertEquals("判断删除成功: ", result.getInt("data"), 1);
				
			}else if(commont.equals("错误rltClassName")){
				JSONObject result = rcrabc.removeCurRltAllByCdt(rltClassName, sourceCiCode, targetCiClass);
				
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}else if(commont.equals("错误sourceCiCode")){
				JSONObject result = rcrabc.removeCurRltAllByCdt(rltClassName, sourceCiCode, targetCiClass);
				
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}else if(commont.equals("错误targetCiCode")){
				JSONObject result = rcrabc.removeCurRltAllByCdt(rltClassName, sourceCiCode, targetCiClass);
				
				assertEquals("判断删除数为0:", result.getInt("data"), 0);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	@Then("^异步自动构建关系,用如下数据:$")
	public void autoCreatBatchRlt(DataTable dt){
		List<List<String>> list = dt.raw();
//		|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
//		|自动构建CI关系|sourceClass|targetClass|文本|文本|
		
		AutosaveOrUpdateBatchRlt aoubr = new AutosaveOrUpdateBatchRlt();
		for (int i = 1; i < list.size(); i++) {
			String rltClassName = list.get(i).get(0);
			String sourceClassName = list.get(i).get(1);
			String targetClassName = list.get(i).get(2);
			String sourceAttrName = list.get(i).get(3);
			String targetAttrName = list.get(i).get(4);
			List sourceAttrNames =  Arrays.asList(sourceAttrName.split(":"));
			List targetAttrNames =  Arrays.asList(targetAttrName.split(":"));
			aoubr.autosaveOrUpdateBatchRlt(rltClassName, sourceClassName, targetClassName, sourceAttrNames, targetAttrNames);
//			assertTrue(autoResult.getBoolean("success"));
		}
	}
	

	@Then("^异步成功自动构建关系\"(.*)\",更新关系\"(.*)\",用key:\"(.*)\",更新状态为:\"(.*)\"$")
	public void autoCreateBatchRltSuccess(String createNum, String updateNum, String key, String status){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int create = Integer.parseInt(createNum);
		int update = Integer.parseInt(updateNum);
		GetProgressInfoByKey gpibk = new GetProgressInfoByKey();
		JSONObject result = gpibk.getProgressInfoByKey(key);
		JSONObject data = result.getJSONObject("data");
//		JSONArray data = autoResult.getJSONArray("data");
//		if(data.length() != 2)assertTrue("自动化构建返回值不是2个! : ", false);
		assertEquals("异步自动创建数量与检测值不同: ", data.getJSONArray("resultData").getInt(0), create);
		assertEquals("异步自动创建数量与检测值不同: ", data.getJSONArray("resultData").getInt(1), update);
		assertEquals("异步自动创建status", data.getInt("status"), status);
		assertEquals("异步自动创建key", data.getString("key"), key);
	}
}
