package com.uinnova.test.step_definitions.api.dmv.group;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

/**
 * 编写时间:2017-11-21 编写人:sunsl 功能介绍:DMV小组的新建，更新类
 */
public class SaveOrUpdateGroupInfo extends RestApi {
	public JSONObject saveOrUpdateGroupInfo(String groupName, String updateGroupName, String groupDesc, String data,
			Integer operator) {
		String url = ":1511/tarsier-vmdb/dmv/group/saveOrUpdateGroupInfo";
		
		JSONObject param = new JSONObject();
		JSONObject group = new JSONObject();
		GroupUtil groupUtil = new GroupUtil();
		if (operator == 0) {
			group.put("groupName", groupName);
			group.put("groupImage", data);
		} else {
			group.put("groupName", updateGroupName);
			group.put("id", groupUtil.getGroupId(groupName));
		}
		group.put("groupDesc", groupDesc);
		JSONArray groupUsers = new JSONArray();
		JSONObject userObj = new JSONObject();
		userObj.put("userId", QaUtil.user_id);
		userObj.put("authRegion", 1);
		groupUsers.put(userObj);
		param.put("group", group);
		param.put("groupUsers", groupUsers);
		return doRest(url, param.toString(), "POST");
	}

	public JSONObject saveOrUpdateGroupInfo(String groupName, String groupDesc) {
		String url = ":1511/tarsier-vmdb/dmv/group/saveOrUpdateGroupInfo";
		JSONObject param = new JSONObject();
		JSONObject group = new JSONObject();
		GroupUtil groupUtil = new GroupUtil();
		group.put("groupName", groupName);
		group.put("groupDesc", groupDesc);
		JSONArray groupUsers = new JSONArray();
		JSONObject userObj = new JSONObject();
		userObj.put("userId", QaUtil.user_id);
		userObj.put("authRegion", 1);
		groupUsers.put(userObj);
		param.put("group", group);
		param.put("groupUsers", groupUsers);
		return doRest(url, param.toString(), "POST");
	}

}
