package com.uinnova.test.step_definitions.utils.base;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciClass.GetClassTree;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


/**
 * CI分类目录工具类
 * @author uinnova
 *
 */
public class CiClassDirUtil {

	/**
	 * 根据CI分类目录名称获取ID
	 * @param dirName
	 * @return
	 */
	public String getDirId(String dirName) {
		QaUtil.report("====dirName===" + dirName);
		GetClassTree gt = new GetClassTree();
		JSONObject obj = gt.getClassTree("");
		JSONArray data = obj.getJSONArray("data");
		String dirId = null;
		for (int i = 0; i < data.length(); i++) {
			JSONObject tmp = (JSONObject) data.get(i);
			String name = tmp.getJSONObject("dir").getString("dirName");
			if (name.equalsIgnoreCase(dirName)) {
				dirId = String.valueOf(tmp.getJSONObject("dir").getBigDecimal("id"));
				break;
			}
		}
		return dirId;
	}

}
