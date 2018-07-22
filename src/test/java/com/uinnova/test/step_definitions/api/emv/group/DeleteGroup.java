package com.uinnova.test.step_definitions.api.emv.group;
/*
 * 编写时间:2018-05-25
 * 编写人:yll
 * 功能介绍:删除团队
 */

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvGroupUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;

public class DeleteGroup extends RestApi {
	public JSONObject deleteGroup(String newGroupName){
		   EmvGroupUtil groupUtil = new EmvGroupUtil();
		   String url =":1516/monitor-web/group/deleteGroup";
		   JSONObject param = new JSONObject();
		   param.put("groupId",groupUtil.getGroupId(newGroupName));
		   return doRest(url, param.toString(), "POST");
	   }

}
