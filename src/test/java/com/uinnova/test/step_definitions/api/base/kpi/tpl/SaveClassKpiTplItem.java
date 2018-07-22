package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TplUtil;

/**
 * 给分类挂载指标模板
 * @author wsl
 *
 */
public class SaveClassKpiTplItem extends RestApi{

	/**
	 * 给CI分类挂载指标模板
	 * @param ciClassName
	 * @param tplName
	 * @return
	 */
	public JSONObject saveClassKpiTplItem(String ciClassName, String tplName) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/saveClassKpiTplItem";
		JSONObject param = new JSONObject();
		CiClassUtil ciClassUtil= new CiClassUtil();
		TplUtil tplUtil = new TplUtil();
		param.put("rltObjId", String.valueOf(ciClassUtil.getCiClassId(ciClassName)));
		param.put("tplId", tplUtil.getKpiTplId(tplName));
		return doRest(url, param.toString(), "POST");
	}

}
