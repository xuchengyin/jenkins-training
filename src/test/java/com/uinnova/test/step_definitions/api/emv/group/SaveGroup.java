package com.uinnova.test.step_definitions.api.emv.group;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.EMV.EmvGroupUtil;
import com.uinnova.test.step_definitions.utils.EMV.EmvUtil;
import com.uinnova.test.step_definitions.utils.dmv.GroupUtil;
/*
 * 编写时间:2018-05-22
 * 编写人:yll
 * 功能介绍:新增、更新团队，新增时 ，groupId不传参，编辑时才传参数
 */
public class SaveGroup extends RestApi{
	public JSONObject saveGroup(JSONArray userId,JSONArray sourceId,String groupName,String img,String newGroupName,Integer operator){
		   String url =":1516/monitor-web/group/saveGroup";
		   JSONObject param = new JSONObject();
		   EmvGroupUtil groupUtil = new EmvGroupUtil();
		   //operator 为0时是新建
			if (operator == 0) {
				param.put("groupName", groupName);
				param.put("groupImage", img);
			} else {
				param.put("groupName", newGroupName);
				param.put("groupId", groupUtil.getGroupId(groupName));
			}
		   param.put("userId", userId);//userId是团队里面选择的成员
		   param.put("sourceId", sourceId);//sourceId是团队里面选择的事件源的id
		   return doRest(url, param.toString(), "POST");
	   }

}
