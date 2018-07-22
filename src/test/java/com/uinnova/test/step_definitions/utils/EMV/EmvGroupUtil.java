package com.uinnova.test.step_definitions.utils.EMV;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.group.QueryOpList;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 * 编写时间:2018-05-25
 * 编写人:yll
 * 功能介绍:EMV团队管理中，删除团队的接口中，传的参数是团队的ID，但是feature里面写的是团队名称，所以要通过团队名称取到团队的ID
 */
public class EmvGroupUtil {
	public BigDecimal getGroupId(String groupName) {
		String sql = "SELECT ID  FROM mon_group WHERE GROUP_NAME = '" + groupName + "' AND DATA_STATUS = 1 ";
		List list = JdbcUtil.executeQuery(sql);
		BigDecimal groupId = new BigDecimal(0);
		if (list != null && list.size() > 0) {
			HashMap groupMap = (HashMap) list.get(0);
			groupId = (BigDecimal) groupMap.get("ID");
		}
		return groupId;
	}

//	public BigDecimal getUserId(String groupName) {
//		QueryOpList qo = new QueryOpList();
//		JSONObject result = qo.queryOpList();
//		JSONArray data = result.getJSONArray("data");
//		BigDecimal id = new BigDecimal(0);
//		for (int i = 0; i < data.length(); i++) {
//			JSONObject userObj = (JSONObject) data.get(i);
//			id = userObj.getBigDecimal("id");
//			if (id.compareTo(new BigDecimal(1)) != 0) {
//				break;
//			}
//		}
//		return id;
//	}

}
