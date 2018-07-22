package com.uinnova.test.step_definitions.api.base.ci;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;

public class SaveOrUpdate extends RestApi{

	/**
	 * @param className
	 * @param table
	 * @param row
	 * @return
	 */
	public JSONObject saveOrUpdate(String className,DataTable table,int row){
		String url = ":1511/tarsier-vmdb/cmv/ci/saveOrUpdate";
		JSONObject param = new JSONObject();
		JSONObject attrs = new JSONObject();
		int cols = table.raw().get(0).size();
		for(int i=1;i<cols;i++){
			String typeData = "";
			String tagData = "";
			if(table.raw().get(row).get(i).indexOf(".txt")>0){
				StringBuffer sb = new StringBuffer();
				String filePath = SaveOrUpdate.class.getResource("/").getPath()+"testData/ci/"+table.raw().get(row).get(i);
				try {
					String fileContent = (new TxtUtil()).readTxt(filePath);
					attrs.put(table.raw().get(0).get(i), fileContent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				String titleField = table.raw().get(0).get(i);
				String name = table.raw().get(row).get(i);
				if(titleField.indexOf("字典")>=0 && (!name.isEmpty())){
					String[] dictList = titleField.split("_");
					if(dictList[1].equals("type")){
						typeData = (new CiUtil()).getAllCiData(name).getJSONObject(0).getJSONObject("ci").getString("ciCode");
					}
					attrs.put(table.raw().get(0).get(i), typeData);
					if(dictList[1].equals("tag")){
						JSONObject sc = (new SearchInfoListByTagIdOrClassId()).searchInfoListByTagIdOrClassId(name, 1);
						JSONArray records = sc.getJSONObject("data").getJSONArray("records");
						if(records.length()>0) {
							tagData = ((JSONObject)records.get(0)).getJSONObject("attrs").getString("CI CODE");
						}
						attrs.put(table.raw().get(0).get(i), tagData);
					}
				}else{
					attrs.put(table.raw().get(0).get(i), table.raw().get(row).get(i));
				}

			}
		}
		attrs.put("undefined","");
		JSONObject ci = new JSONObject();
		ci.put("classId", (new CiClassUtil()).getCiClassId(className));
		param.put("attrs", attrs);
		param.put("ci", ci);
		JSONObject  result= doRest(url, param.toString(), "POST");
		
		return result;
		
	}
}
