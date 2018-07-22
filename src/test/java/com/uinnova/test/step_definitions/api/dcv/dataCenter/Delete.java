package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import java.math.BigDecimal;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class Delete {
	/*
	 * 删除数据中心方法
	 * */
	public String delete(String dataCenterid) {
		String url = ":1511/tarsier-vmdb/dcv/dataCenter/delete";
		
		BigDecimal data1 = (new CiUtil()).getCiId(dataCenterid);
		
		//JSONObject param = getCiobj(data1, dataCenterid);
		String ret = QaUtil.loadRest(url, data1.toString(), "POST");
		return ret;
		
	}
}
