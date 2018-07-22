package com.uinnova.test.step_definitions.api.ITV.config;

import org.json.JSONObject;

/**
 * @author wjx
 * @param viewID 视图ID     
 * @请求方式 post
 * 功能介绍：获取CI节点信息
 */
public class SetFileText {
	public JSONObject saveView(String[] viewID){
		String url = ":1511/tarsier-vmdb/dcv/config/setFileText";
		
		JSONObject param = new JSONObject();
		JSONObject test = new JSONObject();
				
		for(int i = 0; i < viewID.length; i++){
		param.put("url", "projects/snapshot/logic_universe_combined/"+viewID[i]+".json");
		test.put("text", "\"backgroundImage\":\"style1.png\",\"approachEffectTime\":2,"
				+ "\"approachCamInfoTime\":2,\"layerVerticalSpacing\":5,"
				+ "\"layerHorizontalSpacing\":2,\"differentLayerLine\":{\"spacingRow\":1,"
				+ "\"spacingCol\":1,\"direction\":\"out\"},\"roasting\":{\"stayTime\":3,"
				+ "\"layerEffectTime\":10,\"disappearOffset\":0.5,\"disappearRoll\":20},"
				+ "\"layers\":{\"normal@0@0@100000000000216\":{\"materialType\":\"directory\","
				+ "\"materialPath\":\"uinv_cosmos_otherRes\\\\models\\\\layerType\\\\model_1\",\"color\":null,"
				+ "\"scale\":[1,1,1],\"height\":6,\"caption\":{\"fontSize\":1,\"color\":[1,1,1,1],"
				+ "\"offset\":[-0.5,0,0],\"layoutPosition\":[]},\"nodes\":{\"2\":"
				+ "{\"color\":null,\"scale\":[1,1,1],\"caption\":{\"fontSize\":0.4,\"color\""
				+ ":[1,1,1,1],\"offset\":[0,0,0.3]}},\"3\":{\"color\":null,\"scale\":[1,1,1],"
				+ "\"caption\":{\"fontSize\":0.4,\"color\":[1,1,1,1],\"offset\":[0,0,0.3]}},"
				+ "\"4\":{\"color\":null,\"scale\":[1,1,1],\"caption\":{\"fontSize\":0.4,"
				+ "\"color\":[1,1,1,1],\"offset\":[0,0,0.3]}},\"5\":{\"color\":null,"
				+ "\"scale\":[1,1,1],\"caption\":{\"fontSize\":0.4,\"color\":[1,1,1,1],"
				+ "\"offset\":[0,0,0.3]}}},\"containers\":{},\"layout\":{\"horizontal\":\"left\","
				+ "\"vertical\":\"center\"}}}}\"");
		param.put("dcid", 0);
		param.put("text", test);
		System.out.println("******************************" + param);
		}
		
		
		
		return null;
	}
	public static void main(String args[]){
		String[] viewID = {"1000000","adasdasdas10000","1231312ert34"};
		SetFileText test = new SetFileText();
		test.saveView(viewID);
	}
}
