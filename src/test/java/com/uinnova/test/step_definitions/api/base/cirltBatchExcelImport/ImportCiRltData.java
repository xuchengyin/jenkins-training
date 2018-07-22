package com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;

public class ImportCiRltData extends RestApi{

	/**
	 * @param table
	 * @param fileNames
	 * @return
	 */
	public JSONObject importCiRltData(DataTable table,JSONObject fileNames) {
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/importCiRltData";
		/*组装param：
		 * 1.组装各paramObj----按照sheet数
		 * 2.组装attrTitles----按照table行数
		 * **/
		//按照cucumber定义，获取各excel下的sheet所覆盖的行数
		JSONObject excelSheetMap = QaUtil.getExcelSheetNum(table);
		int rowFlag = 0;
		List<Map<String,String>> tableList = table.asMaps(String.class, String.class);
		JSONArray param = new JSONArray();
		for(int i=0;i<tableList.size();i++) {
			String excelName = tableList.get(rowFlag).get("excelName");
			int sheetNum = excelSheetMap.getJSONObject(excelName).length();
			String fileNameServer  =  fileNames.getString(excelName);
			for(int sn=0;sn<sheetNum;sn++) {
				String sheetName = tableList.get(rowFlag).get("sheetName");
				JSONObject paramObj = new JSONObject();
				int rowNum = excelSheetMap.getJSONObject(excelName).getInt(sheetName);
				String sourceFieldIndex = tableList.get(rowFlag).get("excelSourceCICodeIndex");
				String targetFieldIndex = tableList.get(rowFlag).get("excelDistCICodeIndex");
				//				String sourceFieldName = tableList.get(rowFlag).get("excel_源CICode");
				//				String targetFieldName = tableList.get(rowFlag).get("excel_目标CICode");
				String rltClassName = tableList.get(rowFlag).get("rltName");
				BigDecimal rltClsId = (new RltClassUtil()).getRltClassId(rltClassName);
				//				JSONObject excelFieldIndexs = getTitleIndex(fileNames, excelName, sheetName);
				JSONArray attrTitles = new JSONArray();
				for(int rn=0; rn<rowNum;rn++) {
					String rltAttrName = tableList.get(rowFlag).get("rltAttrName");
					JSONObject rltAttrIndex = (new RltClassUtil()).getRltAttrId(rltClassName);
					String excelAttrName = tableList.get(rowFlag).get("excelAttr");
					String AttrTitleIndex = tableList.get(rowFlag).get("excelAttrIndex");
					JSONObject attrTitleObj =  null;
					if (!rltAttrName.isEmpty()) {
						attrTitleObj =  new JSONObject();
						String attrId = new String();
						if (rltAttrIndex.has(rltAttrName)) {
							//已有关系属性
							attrTitleObj.put("attrId", String.valueOf(rltAttrIndex.getInt(rltAttrName)));
						} else {
							//新增关系属性
							attrTitleObj.put("attrId", attrId);
						}
						attrTitleObj.put("attrName", rltAttrName);
						attrTitleObj.put("titleIndex", AttrTitleIndex);
						attrTitleObj.put("titleName", excelAttrName);
						attrTitles.put(attrTitleObj);
					} else {
						//						attrTitles.put(attrTitleObj);
						QaUtil.report("========不添加关系属性==========");
					}
					rowFlag++;
				}
				paramObj.put("attrTitles", attrTitles);
				paramObj.put("fileName", fileNameServer);
				paramObj.put("rltId",String.valueOf(rltClsId));
				paramObj.put("sheetName", sheetName);
				paramObj.put("sourceFieldIndex", sourceFieldIndex);
				paramObj.put("targetFieldIndex", targetFieldIndex);

				param.put(paramObj);
			}
			i = rowFlag;
		}
		return doRest(url, param.toString(), "POST");
	}


	//获取sheet中各字段的index
	//	public JSONObject getTitleIndex(JSONObject fileNames,String fileName,String sheetName) {
	//		String fileNameServer = fileNames.getString(fileName);
	//		JSONArray fieldNames = (new GetSheetDataBySheetName()).getSheetDataBySheetName(fileNameServer, sheetName).getJSONObject("data").getJSONArray("fieldNames");
	//		
	//		JSONObject titleIndex = new JSONObject();
	//		for(int i=0;i<fieldNames.length();i++) {
	//			String titleNamesTmp = fieldNames.toString();
	//			String[] titleNames = titleNamesTmp.substring(1, titleNamesTmp.length()-1).split(",");
	//			titleIndex.put(titleNames[i].substring(1, titleNames[i].length()-1), i);
	//		}
	//		return titleIndex;
	//	}

}
