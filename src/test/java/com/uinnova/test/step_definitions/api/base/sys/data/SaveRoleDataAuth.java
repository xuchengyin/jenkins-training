package com.uinnova.test.step_definitions.api.base.sys.data;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.AuthUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

public class SaveRoleDataAuth extends RestApi{


	/**
	 * 
	 * @author lidw
	 *
	 * @请求方式  post
	 * 
	 * 
	 * 2018/5/31做修改，适应新方式
	 * */
	public JSONObject saveRoleDataAuth(String roleName, List<String> ciClassName, List<String> dataType, List<String> see, List<String> edit, List<String> delete, List<String> add){
		String url = ":1511/tarsier-vmdb/cmv/sys/data/saveRoleDataAuth";
		JSONObject param = new JSONObject();
		JSONArray dataAuths = new JSONArray();
		AuthUtil  authUtil = new AuthUtil();
		CiClassUtil ccu = new CiClassUtil();
		String roleId = authUtil.getRoleId(roleName);
		if(ciClassName.size() != see.size() && see.size() != edit.size() && edit.size() != delete.size() && delete.size() != add.size()) return null;
		for (int i = 0; i < ciClassName.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("id", ccu.getCiClassId(ciClassName.get(i)));
			temp.put("dataType", Integer.parseInt(dataType.get(i)));
			temp.put("see", Boolean.valueOf(see.get(i)));
			temp.put("edit", Boolean.valueOf(edit.get(i)));
			temp.put("delete", Boolean.valueOf(delete.get(i)));
			temp.put("add", Boolean.valueOf(add.get(i)));
			dataAuths.put(temp);
			
		}
		param.put("roleId", roleId);
		param.put("dataAuths", dataAuths);
		return doRest(url,param.toString(),"POST");
	}
	
	//直接传对象赋权
	public JSONObject saveRoleDataAuth(JSONObject obj){
		String url = ":1511/tarsier-vmdb/cmv/sys/data/saveRoleDataAuth";
		return doRest(url,obj.toString(),"POST");
		
	}
}
