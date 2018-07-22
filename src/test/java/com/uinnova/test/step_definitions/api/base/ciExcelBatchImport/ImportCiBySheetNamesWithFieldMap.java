package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;

public class ImportCiBySheetNamesWithFieldMap extends RestApi{

	/**
	 * @param table
	 * @param fileNames
	 * @return
	 */
	public JSONObject importCiBySheetNamesWithFieldMap(DataTable table, JSONObject fileNames) {
		String url = ":1511/tarsier-vmdb/cmv/ciExcelBatchImport/importCiBySheetNamesWithFieldMap";
		JSONArray param = new JSONArray();

		/*
		 * 获取sheet中属性映射关系
		 * 1.根据cucumber table 对数据按照excelName和sheetName进行分组统计,确定循环遍历方式
		 * 2.遍历每个excel表，按照excelAttr组装默认fieldMap和attrDefs 3.根据cucumber
		 * table设置，更新更新fieldMap和attrDefs
		 */

		// 1.根据cucumber table 对数据按照excelName和sheetName进行分组统计
		List<Map<String, String>> tableList = table.asMaps(String.class, String.class);
		HashMap<String, Integer> excelMap = QaUtil.getNumMap(table, "excelName");
		JSONObject excelSheetObj = new JSONObject();
		for(int i=0;i<tableList.size();i++) {
			String excelName = tableList.get(i).get("excelName");
			//			String colName = tableList.get(i).get("colName");
			int excelRows = Integer.parseInt(excelMap.get(excelName).toString());
			JSONObject excelNameObj = QaUtil.getNumJSONObj(table,excelName,i,(i+excelRows),"sheetName");
			excelSheetObj.put(excelName, excelNameObj);
			i = i+(excelRows-1);
		}

		int rowFlag = 0;
		// 1.1 遍历每个excel表
		for (int i = 0; i < excelMap.size(); i++) {
			// 1.1.1 根据入参，获取上传文件在服务器上的存储名称
			String excelName = tableList.get(rowFlag).get("excelName").toString();
			String fileName = fileNames.get(excelName).toString();
			int sheetNums = excelSheetObj.getJSONObject(excelName).length();
			// 1.2 遍历各excel下的每个sheet
			for (int sheetNum = 0; sheetNum < sheetNums; sheetNum++) {
				JSONObject paramObj = new JSONObject();
				JSONObject cdts = new JSONObject();
				JSONObject ciClassInfo = new JSONObject();
				JSONObject fieldMap = new JSONObject();
				JSONArray attrDefs = new JSONArray();
				String sheetName = tableList.get(rowFlag).get("sheetName").toString();
				String className = tableList.get(rowFlag).get("clsName").toString();
				BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
				cdts.put("fileName", fileName);
				cdts.put("importType", Integer.parseInt(tableList.get(rowFlag).get("importType").toString()));
				cdts.put("sheetName", sheetName);
				// 将importExcel中得到的fieldNames,组装cdts及attrDefs(默认新增属性，之后根据属性情况进行已有属性替换)

				// 将getSheetDataBySheetName获取的data组装成fieldMap及attrDefs
				//				try {
				//					Thread.sleep(1000);
				//				} catch (Exception e) {
				//					// TODO: handle exception
				//				}

				JSONObject gs = (new GetSheetDataBySheetName()).getSheetDataBySheetName(fileName, sheetName);
				JSONArray fieldNames = gs.getJSONObject("data").getJSONArray("fieldNames");
				// JSONObject fieldMap = new JSONObject();
				JSONObject excelAttrIndex = new JSONObject();
				for (int ft = 0; ft < fieldNames.length(); ft++) {
					String field = fieldNames.getString(ft);
					// 组装默认fieldMap
					fieldMap.put(String.valueOf(ft), field);
					/*组装默认attrDefs，按照分类属性进行映射或是新增
					 * 1.若分类存在，则替换成属性定义
					 * 2.若分类不存在，则按照默认字段定义
					 * */ 
					JSONObject attrDef = new JSONObject();
					if(classId.compareTo(new BigDecimal(0))!=0) {
						JSONArray clsAttrDefs = (new QueryById()).queryById(className).getJSONObject("data").getJSONArray("attrDefs");
						JSONObject clsAttrObj = getClsAttrIndex(clsAttrDefs);
						if(clsAttrObj.has(field)) {
							//分类存在此属性，按照分类定义进行属性初始化
							int fieldIndex = Integer.parseInt(clsAttrObj.get(field).toString());
							String proName = clsAttrDefs.getJSONObject(fieldIndex).getString("proName");
							int  isMajor = clsAttrDefs.getJSONObject(fieldIndex).getInt("isMajor");
							int  isCiDisp = clsAttrDefs.getJSONObject(fieldIndex).getInt("isCiDisp");
							int  isRequired = clsAttrDefs.getJSONObject(fieldIndex).getInt("isRequired");
							int  proType = clsAttrDefs.getJSONObject(fieldIndex).getInt("proType");
							attrDef.put("proName", field);
							attrDef.put("isMajor", 0);
							attrDef.put("isCiDisp", isCiDisp);
							attrDef.put("isRequired", isRequired);
							attrDef.put("proType", proType);
						}else {
							//分类不存在此属性，按照默认定义进行属性初始化
							attrDef.put("proName", field);
							attrDef.put("isMajor", 0);
							attrDef.put("isCiDisp", 0);
							attrDef.put("isRequired", 0);
							attrDef.put("proType", 3);
						}
					}else {
						//分类不存在此属性，按照默认定义进行属性初始化
						attrDef.put("proName", field);
						attrDef.put("isMajor", 0);
						attrDef.put("isCiDisp", 0);
						attrDef.put("isRequired", 0);
						attrDef.put("proType", 3);
					}
					attrDefs.put(attrDef);
					// 获取各fieldMap中各字段对应的Index
					excelAttrIndex.put(field, ft);
				}
				/*
				 * 3.根据cucumber table设置，更新更新fieldMap和attrDefs 3.1 判断所映射分类是否存在 3.1.1 新建分类映射
				 * 3.1.1.1 按照cucumber table设置，更新attrDefs修改属性类型,由于是新增因此不需要修改fieldMap 1.1.1
				 * ignore==1 忽略该属性 1.1.2 按照table更新attrDefs 注意：主键通过cucumber table 设置获取
				 * 
				 * 3.1.2 已有分类映射 3.1.1.2 按照cucumber table对需要修改的属性进行调整fieldMap及attrDefs调整 1.1.1
				 * ignore==1 忽略该属性 1.1.2 按照clsAttrDefs更新attrDefs
				 */

				// 3.1 判断cls是否存在
				//				String className = tableList.get(rowFlag).get("clsName").toString();
				BigDecimal clsId = (new CiClassUtil()).getCiClassId(className);

				int sheetRows = excelSheetObj.getJSONObject(excelName).getInt(sheetName);
				//				int sheetRows = sheetMap.get(sheetName);
				// 3.1.1 cls不存在
				if (clsId.compareTo(new BigDecimal(0))==0) {
					//创建分类
					JSONObject sa = (new SaveOrUpdate()).createCiClassFirstTime(className, "业务领域", "Default");
					clsId = sa.getBigDecimal("data");
					JSONObject qi = (new QueryById()).queryById(className);
					ciClassInfo.put("ciClass", qi.getJSONObject("data").getJSONObject("ciClass"));
					ciClassInfo.put("fixMapping", qi.getJSONObject("data").getJSONObject("fixMapping"));
					String field = null;
					// 3.1.1.1 根据cucumber table进行各属性更新（包括主键）
					for (int ss = 0; ss < sheetRows; ss++) {
						// 3.1.1.1 ignore==1 忽略该属性
						String ignore = tableList.get(rowFlag).get("ignore");
						String excelAttrName = tableList.get(rowFlag).get("excelAttr");
						String clsAttrName = tableList.get(rowFlag).get("clsAttr");
						int proType = Integer.parseInt(tableList.get(rowFlag).get("proType"));
						int isMajor = Integer.parseInt(tableList.get(rowFlag).get("isMajor"));
						int isCiDisp = Integer.parseInt(tableList.get(rowFlag).get("isCiDisp"));
						int isRequired = Integer.parseInt(tableList.get(rowFlag).get("isRequired"));
						int importType = Integer.parseInt(tableList.get(rowFlag).get("importType"));
						// 获取excelAttr在fieldMap及attrDefs中的index，以便对fieldMap按照index进行替换
						String fieldMapIndex = excelAttrIndex.get(excelAttrName).toString();
						int attrDefIndex  = getAttrDefsIndex(attrDefs, excelAttrName);
						if (ignore.equals("1")) {
							// 获取属性所在index，从fieldMap和attrDefs中移除此属性
							fieldMap.remove(fieldMapIndex);
							attrDefs.remove(attrDefIndex);
						} else {
							// 3.1.1.2 读取table中的属性设置进行fieldMap及attrDefs更新，如果是主键，则修改fieldMap，否则只修改attrDefs
							// 若是主键，替换fieldMap及importType
							JSONObject replacedObj = attrDefs.getJSONObject(attrDefIndex);
							if (isMajor == 1) {
								fieldMap.put(fieldMapIndex, clsAttrName);
								replacedObj.put("proName", clsAttrName);
								replacedObj.put("isMajor", isMajor);
								//								cdts.put("importType", importType);
							} else {
								fieldMap.put(fieldMapIndex, clsAttrName);
								replacedObj.put("proName", clsAttrName);
								replacedObj.put("isMajor", isMajor);
								replacedObj.put("isCiDisp", isCiDisp);
								replacedObj.put("isRequired", isRequired);
								replacedObj.put("proType", proType);
							}
						}
						rowFlag++;
					}
				} else {
					/*
					 * 3.1.2 cls存在，根据cls替换attrDefs，将fieldMap及attrDefs替换 1. 如果cls有这个属性，则按cls替换 2.
					 * 若干没有这个属性， 2.1 若excelAttr==clsAttr,不管 2.2 若excelAttr！=clsAttr,则替换成此属性
					 */
					// 获取已有属性索引
					JSONObject qi = (new QueryById()).queryById(className);
					JSONArray oldAttrDefs = qi.getJSONObject("data").getJSONArray("attrDefs");
					ciClassInfo.put("ciClass", qi.getJSONObject("data").getJSONObject("ciClass"));
					ciClassInfo.put("fixMapping", qi.getJSONObject("data").getJSONObject("fixMapping"));
					ciClassInfo.put("oldAttrDefs", oldAttrDefs);
					//获取clsAttr对应的Index
					JSONObject clsAttrIndex = getClsAttrIndex(oldAttrDefs);
					for (int ss = 0; ss < sheetRows; ss++) {
						// 3.1.1.1 ignore==1 忽略该属性
						String ignore = tableList.get(rowFlag).get("ignore");
						String excelAttrName = tableList.get(rowFlag).get("excelAttr");
						String clsAttrName = tableList.get(rowFlag).get("clsAttr");
						int proType = Integer.parseInt(tableList.get(rowFlag).get("proType"));
						int isMajor = Integer.parseInt(tableList.get(rowFlag).get("isMajor"));
						int isCiDisp = Integer.parseInt(tableList.get(rowFlag).get("isCiDisp"));
						int isRequired = Integer.parseInt(tableList.get(rowFlag).get("isRequired"));
						int importType = Integer.parseInt(tableList.get(rowFlag).get("importType"));
						String fieldMapIndex = excelAttrIndex.get(excelAttrName).toString();
						int attrDefIndex = getAttrDefsIndex(attrDefs, excelAttrName);
						if (ignore.equals("1")) {
							fieldMap.remove(fieldMapIndex);
							attrDefs.remove(attrDefIndex);
						} else {
							if (clsAttrIndex.has(clsAttrName)) {
								// 映射已有属性
								replaceFieldMap(excelAttrName, clsAttrName, fieldMap,fieldMapIndex);
								replaceAttrInfo(attrDefs, oldAttrDefs, excelAttrName,clsAttrName);
							} else {
								// 映射新增属性
								replaceFieldMap(excelAttrName, clsAttrName, fieldMap,fieldMapIndex);
								JSONObject replacedObj = attrDefs.getJSONObject(attrDefIndex);
								replacedObj.put("proName", clsAttrName);
								//								replacedObj.put("isMajor", 0);
								//								replacedObj.put("isCiDisp", 0);
								//								replacedObj.put("isRequired", 0);
							}
						}

						rowFlag++;
					}
				}
				cdts.put("fieldMap", fieldMap);
				ciClassInfo.put("attrDefs", attrDefs);
				paramObj.put("cdts",cdts);
				paramObj.put("ciClassInfo",ciClassInfo);
				param.put(paramObj);
			}
		}
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 封装已有ci分类中属性Index到JSONObject中，以便获取index
	 * @param oldAttrDefs
	 * @return
	 */
	private JSONObject getClsAttrIndex(JSONArray oldAttrDefs) {
		JSONObject clsAttrIndex = new JSONObject();
		JSONObject attrDefRes = null;
		for (int as = 0; as < oldAttrDefs.length(); as++) {
			attrDefRes = (JSONObject) oldAttrDefs.get(as);
			clsAttrIndex.put(attrDefRes.getString("proName"), as);
		}
		return clsAttrIndex;
	}

	/**
	 * 获取在对ci分类属性，进行默认初始化后，各excelAttr对应的attrDefs中对应的index
	 * @param attrDefs
	 * @param excelAttrName
	 * @return
	 */
	private int getAttrDefsIndex(JSONArray attrDefs, String excelAttrName) {
		int attrDefIndex = 0;
		for (int as = 0; as < attrDefs.length(); as++) {
			String proName = attrDefs.getJSONObject(as).getString("proName");
			if (proName.equals(excelAttrName)) {
				attrDefIndex = as;
			}
		}
		return attrDefIndex;
	}


	/**
	 * 将分类属性定义替换到原属性定义中
	 * @param attrDefs
	 * @param oldAttrDefs
	 * @param excelAttrName
	 * @param clsAttrName
	 * @return
	 */
	public JSONArray replaceAttrInfo(JSONArray attrDefs, JSONArray oldAttrDefs,String excelAttrName,String clsAttrName) {
		JSONObject attrDef = new JSONObject();
		JSONObject oldAttrDef = null;
		for(int ns=0;ns<attrDefs.length();ns++) {
			if(attrDefs.getJSONObject(ns).getString("proName").equals(excelAttrName)) {
				attrDef = attrDefs.getJSONObject(ns);
			}
		}
		for(int cs=0;cs<oldAttrDefs.length();cs++) {
			if(oldAttrDefs.getJSONObject(cs).getString("proName").equals(clsAttrName)) {
				oldAttrDef = oldAttrDefs.getJSONObject(cs);
			}		
		}
		attrDef.put("proName", oldAttrDef.getString("proName"));
		attrDef.put("isCiDisp", oldAttrDef.getInt("isCiDisp"));
		attrDef.put("isMajor", oldAttrDef.getInt("isMajor"));
		attrDef.put("isRequired", oldAttrDef.getInt("isRequired"));
		attrDef.put("proType", oldAttrDef.getInt("proType"));
		return attrDefs;
	}


	/**
	 * 替换fieldMap,将fieldMap中的excelAttr替换成clsAttr
	 * @param excelAttrName
	 * @param clsAttrName
	 * @param fieldMap
	 * @param excelAttrIndex
	 * @return
	 */
	public JSONObject replaceFieldMap(String excelAttrName,String clsAttrName,JSONObject fieldMap,String excelAttrIndex) {
		String name = null;
		//		int excelAttrId = excelAttrObj.getInt(excelAttrIndex);
		//		for(int i=0;i<fieldMap.length();i++) {
		name = fieldMap.getString(excelAttrIndex);
		if(name.equals(excelAttrName)) {
			fieldMap.put(String.valueOf(excelAttrIndex), clsAttrName);
			//			}
		}
		return fieldMap;
	}


}
