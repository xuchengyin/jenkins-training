package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.ImportCi;
import com.uinnova.test.step_definitions.api.base.ci.RemoveByClassId;
import com.uinnova.test.step_definitions.api.base.ciClass.GetClassTree;
import com.uinnova.test.step_definitions.api.base.ciClass.ImportClassAttr;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.api.base.ciClass.RemoveById;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;

/**
 * CI分类工具类
 * @author uinnova
 *
 */
public class CiClassUtil {

	/**
	 * @param ciClassName
	 * @param filePath
	 * @return
	 */
	public JSONObject importCiClassAttr(String ciClassName,String filePath) {
		ImportClassAttr im = new ImportClassAttr();
		JSONObject result = im.importAttr(ciClassName,filePath);
		return result;
	}

	/**
	 * @param dirName
	 * @param ciClassName
	 * @param imageName
	 * @param filePath
	 */
	public void createCiClassAndImportCi(String dirName, String ciClassName, String imageName,String filePath) {
		// 创建分类
		SaveOrUpdate su = new SaveOrUpdate();
		su.createCiClassFirstTime(ciClassName, dirName, imageName);
		String firstHeadTitle = ExcelUtil.getFirstHeadTitle(filePath, ciClassName);
		
		//如果导入的文件不存在CI Code， 那么第一列就是ciCode
		if (! ExcelUtil.getFirstHeadTitle(filePath, ciClassName, "CI Code")){
			su.updatePrimaryKeyName(ciClassName,firstHeadTitle);
		}
				
		// 导入分类属性
		importCiClassAttr(ciClassName,filePath);
		// 导入分类数据
		ImportCi ic = new ImportCi();
		ic.importCi(ciClassName, filePath);
	}

	/**
	 * @param ciClassName
	 * @return
	 */
	public Boolean deleteCiClassAndCi(String ciClassName) {
		// 获取ci分类id
		CiClassUtil cu = new CiClassUtil();
		Boolean flag = false;
		String clsId = String.valueOf(cu.getCiClassId(ciClassName));
		if(!clsId.isEmpty()){
			// 清除ci数据
			RemoveByClassId rbc = new RemoveByClassId();
			rbc.removeByClassId(clsId);
			// 删除ci分类
			RemoveById rb = new RemoveById();
			rb.removeById(clsId);
			flag = true;
		}
		return flag;
	}


	/**
	 * 根据分类名称获取分类ID
	 * @param ciClassName
	 * @return
	 */
	public BigDecimal getCiClassId(String ciClassName) {
		GetClassTree gc = new GetClassTree();
		JSONObject resultObj = gc.getClassTree(ciClassName);
		JSONArray data = resultObj.getJSONArray("data");
		BigDecimal classId = new BigDecimal(0);
		for (int i = 0; i < data.length(); i++) {
			boolean flag = false;
			JSONObject index = (JSONObject) data.get(i);
			if (index.toString().startsWith("{\"cls\"")) {
				JSONArray cls = index.getJSONArray("cls");
				if (!cls.equals(null)) {
					for (int j = 0; j < cls.length(); j++) {
						String name = ((JSONObject) cls.get(j)).getString("className");
						if (name.equalsIgnoreCase(ciClassName)) {
							classId = ((JSONObject) cls.get(j)).getBigDecimal("id");
							flag = true;
							break;
						}
					}
				}
			}
			if (flag)
				break;
		}
		return classId;
	}

	/**
	 * 获取单个ciClass的基本信息， 不包括属性定义
	 * @param ciClassName
	 * @return
	 */
	public JSONObject getCiClassInfoWithoutAttrInfo(String ciClassName) {
		GetClassTree gc = new GetClassTree();
		JSONObject resultObj = gc.getClassTree(ciClassName);
		JSONArray data = resultObj.getJSONArray("data");
		JSONObject classInfo = null;
		for (int i = 0; i < data.length(); i++) {
			boolean hasFlag = false;
			JSONObject index = (JSONObject) data.get(i);
			if (index.toString().startsWith("{\"cls\"")) {
				JSONArray cls = index.getJSONArray("cls");
				if (!cls.equals(null)) {
					for (int j = 0; j < cls.length(); j++) {
						String name = ((JSONObject) cls.get(j)).getString("className");
						if (name.equalsIgnoreCase(ciClassName)) {
							classInfo = (JSONObject) cls.get(j);
							hasFlag = true;
							break;
						}
					}
				}
			}
			if (hasFlag)
				break;
		}
		return classInfo;
	}

	/**
	 * 给分类添加属性定义
	 * @param className
	 * @param table
	 * @return
	 */
	public JSONObject addAttrUtil(String className,DataTable table){
		JSONObject result = (new QueryById()).queryById(className);
		JSONArray attrDefs = result.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject classInfo = (new QueryById()).queryById(className);
		JSONObject attrDef1 = (JSONObject) classInfo.getJSONObject("data").getJSONArray("attrDefs").get(0);
		JSONObject ciClass = (JSONObject) classInfo.getJSONObject("data").getJSONObject("ciClass");
		JSONObject fixMapping = (JSONObject) classInfo.getJSONObject("data").getJSONObject("fixMapping");
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			JSONObject attrDefsObj = new JSONObject();
			List<String> row = table.raw().get(i);
			//获取属性名称
			String proName = null;
			if(row.get(0).indexOf(".txt")>0){
				String filePath = CiClassUtil.class.getResource("/").getPath()+"/testData/ci/"+row.get(0);
				proName = (new TxtUtil()).readTxt(filePath);
			}else{
				proName = row.get(0);
			}

			if(i==1){
				attrDefsObj = attrDef1;
				attrDef1.put("proName", proName);
				attrDef1.put("isMajor", 0);
				attrDef1.remove("proStdName");
				attrDefsObj.put("enumValues", row.get(4));
				attrDefsObj.put("defVal", row.get(6));
				attrDefsObj.put("isCiDisp", row.get(3).toString());
			}
			if(i!=1){
				attrDefsObj.put("defVal", row.get(6));
				attrDefsObj.put("enumValues", row.get(4));
				attrDefsObj.put("isCiDisp", row.get(3).toString());
				attrDefsObj.put("isRequired", row.get(2).toString());
				attrDefsObj.put("orderNo", i);
				attrDefsObj.put("proDesc", "");
				attrDefsObj.put("proName", proName);
			}
			//添加数据字典
			String[] dictList;
			BigDecimal id = new BigDecimal(0);
			if(row.get(1).indexOf("数据字典")>=0){
				dictList = row.get(1).split("_");
				if(dictList[1].equals("type")){
					id = (new CiClassUtil()).getCiClassId(dictList[2]);
					attrDefsObj.put("proDropSourceType", 2);
				}
				if(dictList[1].equals("tag")){
					id = (new TagRuleUtil()).getTagId(dictList[2]);
					attrDefsObj.put("proDropSourceType", 1);
				}
				attrDefsObj.put("proDropSourceId", String.valueOf(id));
			}
			attrDefsObj.put("proType", getDataType(row,1));
			if(row.get(5).equals("1")){
				attrDefsObj.put("isMajor", 1);
				fixMapping.put("nmCiCode", proName);
			}
			attrDefs.put(attrDefsObj);
		}
		attrDefs.remove(0);
		SaveOrUpdate su = new SaveOrUpdate();
		JSONObject res = su.saveOrUpdate(attrDefs, ciClass, fixMapping);
		return res;
	}

	/**
	 * 修改分类的属性
	 * @param className
	 * @param table
	 * @return
	 */
	public JSONObject updateAttrUtil(String className,DataTable table){
		JSONObject result = (new QueryById()).queryById(className);
		JSONArray attrDefs = result.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject classInfo = (new QueryById()).queryById(className);
		JSONObject attrDef1 = (JSONObject) classInfo.getJSONObject("data").getJSONArray("attrDefs").get(0);
		JSONObject ciClass = (JSONObject) classInfo.getJSONObject("data").getJSONObject("ciClass");
		JSONObject fixMapping = (JSONObject) classInfo.getJSONObject("data").getJSONObject("fixMapping");
		int rows = table.raw().size();
		for(int i=1;i<rows;i++){
			JSONObject attrDefsObj = new JSONObject();
			List<String> row = table.raw().get(i);
			//获取属性名称
			String proName = null;
			if(row.get(0).indexOf(".txt")>0){
				String filePath = CiClassUtil.class.getResource("/").getPath()+"/testData/ci/"+row.get(0);
				proName = (new TxtUtil()).readTxt(filePath);
			}else{
				proName = row.get(0);
			}

			if(i==1){
				attrDefsObj = attrDef1;
				attrDef1.put("proName", proName);
				attrDef1.put("isMajor", 0);
				attrDef1.remove("proStdName");
				attrDefsObj.put("enumValues", row.get(4));
				attrDefsObj.put("defVal", row.get(6));
				attrDefsObj.put("isCiDisp", row.get(3).toString());
			}
			if(i!=1){
				attrDefsObj.put("defVal", row.get(6));
				attrDefsObj.put("enumValues", row.get(4));
				attrDefsObj.put("isCiDisp", row.get(3).toString());
				attrDefsObj.put("isRequired", row.get(2).toString());
				attrDefsObj.put("orderNo", i);
				attrDefsObj.put("proDesc", "");
				attrDefsObj.put("proName", proName);
			}
			//添加数据字典
			String[] dictList;
			BigDecimal id = new BigDecimal(0);
			if(row.get(1).indexOf("数据字典")>=0){
				dictList = row.get(1).split("_");

				if(dictList[1].equals("type")){
					id = (new CiClassUtil()).getCiClassId(dictList[2]);
					attrDefsObj.put("proDropSourceType", 2);
				}
				if(dictList[1].equals("tag")){
					id = (new TagRuleUtil()).getTagId(dictList[2]);
					attrDefsObj.put("proDropSourceType", 1);
				}
				attrDefsObj.put("proDropSourceId", String.valueOf(id));
			}
			attrDefsObj.put("proType", getDataType(row,1));
			if(row.get(5).equals("1")){
				attrDefsObj.put("isMajor", 1);
				fixMapping.put("nmCiCode", proName);
			}
			attrDefs.put(attrDefsObj);
		}
		attrDefs.remove(0);
		SaveOrUpdate su = new SaveOrUpdate();
		JSONObject res = su.saveOrUpdate(attrDefs, ciClass, fixMapping);
		return res;
	}


	/**
	 * @param row
	 * @param colNum
	 * @return
	 */
	public String getDataType(List<String> row ,int colNum){
		String type = "";
		if(row.get(colNum).indexOf("数据字典")>=0){
			type = String.valueOf(8);
		}else{
			switch(row.get(colNum)){
			case "整数":
				type = String.valueOf(1);
				//				attrDefsObj.put("proType", String.valueOf(1));
				break;
			case "小数":
				type = String.valueOf(2);
				//				attrDefsObj.put("proType", String.valueOf(2));
				break;
			case "字符串":
				type = String.valueOf(3);
				//				attrDefsObj.put("proType", String.valueOf(3));
				break;
			case "文本":
				type = String.valueOf(4);
				//				attrDefsObj.put("proType", String.valueOf(4));
				break;
			case "文章":
				type = String.valueOf(5);
				//				attrDefsObj.put("proType", String.valueOf(5));
				break;
			case "枚举":
				type = String.valueOf(6);
				//				attrDefsObj.put("proType", String.valueOf(6));
				break;
			case "日期":
				type = String.valueOf(7);
				//				attrDefsObj.put("proType", String.valueOf(7));
				break;
			}
		}
		return type;
	}


	/**
	 * @return
	 */
	public JSONObject getAllCiClass(){
		JSONObject allClass = new JSONObject();
		int classNum=0;
		JSONObject classTree = (new GetClassTree().getClassTree(""));
		int dirNum = classTree.getJSONArray("data").length();
		for (int i=0;i<dirNum;i++){
			JSONObject dataObj = (JSONObject) classTree.getJSONArray("data").get(i);
			JSONArray cls = dataObj.getJSONArray("cls");
			int  clsNum = dataObj.getJSONArray("cls").length();
			for (int j=0;j<clsNum;j++){
				JSONObject cl = dataObj.getJSONArray("cls").getJSONObject(j);
				allClass.put(String.valueOf(classNum),cl.getString("className"));
				classNum++;
			}
		}
		return allClass;
	}


	/**
	 * @param clsName
	 * @param attrName
	 * @param attrType
	 * @return
	 */
	public String getCiClsAttrSort(String clsName,String attrName,int attrType){
		JSONObject qi = (new QueryById()).queryById(clsName);
		//组装中间结果attrSort JSONObject
		String[] typeStr = {"int","double","short","long","big"};
		int IV = 0;
		int DV = 0;
		int SV = 0;
		int LV = 0;
		int BV = 0;
		//初始化attrSort
		JSONObject attrSort = new JSONObject();
		for(int i=0;i<typeStr.length;i++){
			JSONObject attrSortObj = new JSONObject();
			attrSort.put(typeStr[i], attrSortObj);
		}
		//将属性类型组装到attrSort
		JSONArray attrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
		for(int as=0;as<attrDefs.length();as++){
			String name = ((JSONObject)attrDefs.get(as)).getString("proName");
			int type = ((JSONObject)attrDefs.get(as)).getInt("proType");
			if(type==1){
				JSONObject intObj = attrSort.getJSONObject(typeStr[0]);
				intObj.put(name, "IV_"+IV);
				IV++;
			}
			if(type==2){
				JSONObject doubObj = attrSort.getJSONObject(typeStr[1]);
				doubObj.put(name, "DV_"+DV);
				DV++;
			}
			if(type==3 || type==6 ||type==7||type==8 ){
				JSONObject shortObj = attrSort.getJSONObject(typeStr[2]);
				shortObj.put(name, "SV_"+SV);
				SV++;
			}
			if(type==4){
				JSONObject longObj = attrSort.getJSONObject(typeStr[3]);
				longObj.put(name, "LV_"+LV);
				LV++;
			}
			if(type==5){
				JSONObject bigObj = attrSort.getJSONObject(typeStr[4]);
				bigObj.put(name, "BV_"+BV);
				BV++;
			}
		}
		JSONObject realObj = null;
		if(attrType==1){
			realObj = attrSort.getJSONObject(typeStr[0]);
		}
		if(attrType==2){
			realObj = attrSort.getJSONObject(typeStr[1]);
		}
		if(attrType==3 || attrType==6 ||attrType==7||attrType==8 ){
			realObj = attrSort.getJSONObject(typeStr[2]);
		}
		if(attrType==4){
			realObj = attrSort.getJSONObject(typeStr[3]);
		}
		if(attrType==5){
			realObj = attrSort.getJSONObject(typeStr[4]);
		}
		return realObj.getString(attrName);	 
	}

	/**
	 * @param ciCode
	 * @return
	 * 根据CICode获取到class名称
	 */
	public String getCiClassNameByCiCode(String ciCode) {
		String sql ="select CLASS_NAME from cc_ci_class where id = (select CLASS_ID from cc_ci  where CI_CODE='"
				+ciCode+"' and DATA_STATUS=1 and DOMAIN_ID="+ QaUtil.domain_id+")  and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List ciClsList = JdbcUtil.executeQuery(sql);
		if (ciClsList.size() >0 ){
			HashMap ciHashMap = (HashMap)ciClsList.get(0);
			return (String) ciHashMap.get("CLASS_NAME");
		}else{
			return "" ;
		}


	}

	/**
	 * 根据classId获得className
	 * @param clsId
	 * @return String
	 */
	public String getClsNameByClsId(BigDecimal clsId){
		String name="";
		String sql = "SELECT CLASS_NAME FROM cc_ci_class  WHERE ID = " + clsId + "  AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List ciList = JdbcUtil.executeQuery(sql);
		if (ciList.size() >0 ){
			HashMap ciHashMap = (HashMap)ciList.get(0);
			name = (String) ciHashMap.get("CLASS_NAME");
			return name;
		}else{
			return name ;
		}
	}

	/**
	 * 根据分类的属性名称获得属性ID
	 * @param className
	 * @param attrName
	 */
	public BigDecimal getAttrIdByAttrName(String className,String attrName){
		BigDecimal classId = getCiClassId(className);
		String sql = "SELECT ID FROM cc_ci_attr_def where CLASS_ID = " + classId + " AND PRO_NAME = '" + attrName + "'  AND CI_TYPE = 1 AND DATA_STATUS = 1";
		List list = JdbcUtil.executeQuery(sql);
		BigDecimal attrId = new BigDecimal(0);
		if (list.size() > 0){
			HashMap  map = (HashMap)list.get(0);
			attrId = (BigDecimal)map.get("ID");
		}
		return attrId;
	}

	public BigDecimal getClassIdByClassName(String className,BigDecimal ciType){
		String sql = "SELECT ID FROM cc_ci_class WHERE CLASS_NAME ='" + className + "' AND CI_TYPE = " + ciType + " AND DATA_STATUS = 1 AND DOMAIN_ID=" + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		BigDecimal classId = new BigDecimal(0);
		if(list != null && list.size() >0){
			HashMap map = (HashMap)list.get(0);
			classId = (BigDecimal)map.get("ID");
		}
		return classId;
	}
}
