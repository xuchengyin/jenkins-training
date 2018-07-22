package com.uinnova.test.step_definitions.api.base.ciClass;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassDirUtil;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class SaveOrUpdate extends RestApi{

	/**
	 * 创建ci分类
	 * @param attrDefs
	 * @param ciClass
	 * @param fixMapping
	 * @return
	 */
	public JSONObject saveOrUpdate(JSONArray attrDefs, JSONObject ciClass, JSONObject fixMapping) {
		JSONObject jsonObj = new JSONObject();
		String url = ":1511/tarsier-vmdb/cmv/ciClass/saveOrUpdate";
		jsonObj.put("attrDefs", attrDefs);
		jsonObj.put("ciClass", ciClass);
		jsonObj.put("fixMapping", fixMapping);
		return doRest(url, jsonObj.toString(), "POST");
	}

	/**
	 * @param className
	 * @param dirName
	 * @param imageName
	 * @return
	 */
	public JSONObject createCiClassFirstTime(String className, String dirName, String imageName) {
		String dirId = (new CiClassDirUtil()).getDirId(dirName);
		JSONArray attrDefs = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("ciType", 1);
		obj.put("isMajor", 1);
		obj.put("isRequired", 1);
		obj.put("orderNo", 1);
		obj.put("proName", "CI Code");
		obj.put("proType", 3);
		attrDefs.put(obj);
		JSONObject ciClass = new JSONObject();
		ciClass.put("DISP_FIELD_IDS", "");
		ciClass.put("DISP_FIELD_NAMES", "");
		ciClass.put("ciType", "1");
		QaUtil.report("====className====" + className);
		ciClass.put("classCode", className);
		ciClass.put("classColor", "rgb(85,168,253)");
		ciClass.put("classDesc", "");
		ciClass.put("className", className);
		QaUtil.report("====dirId====" + dirId);
		ciClass.put("dirId", dirId);
		String imageUrl = (new ImageUtil()).getImageUrl(imageName);
		QaUtil.report("====icon====" + imageUrl);
		ciClass.put("icon", imageUrl);
		ciClass.put("parentId", 0);
		JSONObject fixMapping = new JSONObject();
		fixMapping.put("nmCiCode", "CI Code");
		JSONObject result = saveOrUpdate(attrDefs, ciClass, fixMapping);
		return result;
	}

	/**
	 * @param sourceName
	 * @param distName
	 * @return
	 * 重构于 2018-4-12
	 */
	public JSONObject updateCiClassName(String sourceName, String distName) {
		QueryById queryById = new QueryById();
		JSONObject ciClsResult = queryById.queryById(sourceName);
		JSONArray attrDefs = ciClsResult.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject ciClass = ciClsResult.getJSONObject("data").getJSONObject("ciClass");
		JSONObject fixMapping = ciClsResult.getJSONObject("data").getJSONObject("fixMapping");
		ciClass.put("classCode", distName);
		ciClass.put("className", distName);
		return saveOrUpdate(attrDefs, ciClass, fixMapping);
	}


	/**
	 * @param className
	 * @param newName
	 * @return
	 */
	public JSONObject updatePrimaryKeyName(String className,String newName){
		JSONObject classInfo = (new QueryById()).queryById(className);
		JSONObject  ciClass  = classInfo.getJSONObject("data").getJSONObject("ciClass");
		JSONArray   attrDefs =  classInfo.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject att_obj = (JSONObject) attrDefs.get(0);
		att_obj.put("proName", newName);
		att_obj.put("isMajor", 1);
		JSONObject fixMapping = new JSONObject();
		fixMapping.put("nmCiDesc", "");
		fixMapping.put("nmCiCode", newName);
		JSONObject result = saveOrUpdate(attrDefs, ciClass, fixMapping);
		return result;
	}
	
	
	/**
	 * 设置class的label
	 * @param className
	 * @param labelList
	 * @return
	 */
	public JSONObject updateClassLabel(String className,List<String> labelList){
		JSONObject classInfo = (new QueryById()).queryById(className);
		JSONObject  ciClass  = classInfo.getJSONObject("data").getJSONObject("ciClass");
		JSONArray   attrDefs =  classInfo.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject fixMapping = classInfo.getJSONObject("data").getJSONObject("fixMapping");
		for (int i =0; i<attrDefs.length(); i++){
			JSONObject att_obj = (JSONObject) attrDefs.get(i);
			if (labelList.contains(att_obj.getString("proName"))){
				att_obj.put("isCiDisp", 1);
			}
		}		
		JSONObject result = saveOrUpdate(attrDefs, ciClass, fixMapping);
		return result;
	}
}
