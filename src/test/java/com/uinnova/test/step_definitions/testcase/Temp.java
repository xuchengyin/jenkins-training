package com.uinnova.test.step_definitions.testcase;

import org.json.JSONArray;
import org.json.JSONObject;

public class Temp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String str = "@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_:123";
//		String[] al = str.split(":");
//		for(int i = 0 ; i < al.length ; i++){
//			System.out.println(al[i]);
//		}
//		ArrayList al = new ArrayList();
//		al.add("第1-0次");
//		isNum("00005");
//		isNum("5.00000");
//		System.out.println(al.contains("第1-0次"));
//		System.out.println("第1-0次".equals("第1-0次"));
//		{
//			className: "test",
//			datas: [{
//					"名称": "d01Servertest",
//					"IP": "1.1.1.3",
//					"id": "P311-G2-01"
//				},{
//					"名称": "d02Servertest",
//					"IP": "1.1.1.4",
//					"id": "P311-G2-02"
//				}
//			]
//		}
		JSONObject temp = new JSONObject();
		JSONArray temp1 = new JSONArray();
		temp1.put("1");
		temp.put("a", 123);
		temp.put("b", temp1);
		System.out.print(temp.optJSONArray("c"));
//		temp.query("c");
		
	}

	
	public JSONObject createServerTest(int num){
		String name = "Servertest";
		JSONObject obj = new JSONObject();
		JSONArray arrObj = new JSONArray();
		obj.put("className", "test");
		for(int i = 0; i < num; i++){
			JSONObject temp = new JSONObject();
			temp.put("IP", "1.1.1.4");
			temp.put("id", "P311-G2-01");
			temp.put("名称", name+i);
			arrObj.put(temp);
		}
		obj.put("datas", arrObj);
		return obj;
	}
}
