package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.group.QueryOpList;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/*
 * 编写时间:2017-12-19
 * 编写人:sunsl
 * 功能介绍:DMV小组的功能类
 */
public class GroupUtil {
	public BigDecimal getGroupId(String groupName) {
		String sql = "SELECT ID  FROM vc_group WHERE GROUP_NAME = '" + groupName + "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		BigDecimal groupId = new BigDecimal(0);
		if (list != null && list.size() > 0) {
			HashMap groupMap = (HashMap) list.get(0);
			groupId = (BigDecimal) groupMap.get("ID");
		}
		return groupId;
	}

	public BigDecimal getUserId(String groupName) {
		QueryOpList qo = new QueryOpList();
		JSONObject result = qo.queryOpList();
		JSONArray data = result.getJSONArray("data");
		BigDecimal id = new BigDecimal(0);
		for (int i = 0; i < data.length(); i++) {
			JSONObject userObj = (JSONObject) data.get(i);
			id = userObj.getBigDecimal("id");
			if (id.compareTo(new BigDecimal(1)) != 0) {
				break;
			}
		}
		return id;
	}
}
