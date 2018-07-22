package com.uinnova.test.step_definitions.api.dmv.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-05
 * 编写人:sunsl
 * 功能介绍:设置自定义形状类
 */
public class SaveCustomImage extends RestApi{
    public JSONObject saveCustomImage(String imageName,String imageXml){
    	String url = ":1511/tarsier-vmdb/dmv/image/saveCustomImage";
    	JSONObject param = new JSONObject();
    	param.put("imageName", imageName);
    	param.put("imageXml", imageXml);
    	return doRest(url, param.toString(), "POST");
    }
}
