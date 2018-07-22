package com.uinnova.test.step_definitions.api.cmv.image;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-01-11
 * 编写人:sunsl
 * 功能介绍:CMV更改图标的映射图标
 */
public class UpdateImageRlt extends RestApi{
    public JSONObject updateImageRlt(BigDecimal rltImgId,BigDecimal id,String dirName){
    	String url = ":1511/tarsier-vmdb/cmv/image/updateImageRlt";
    	JSONObject param = new JSONObject();
    	if(dirName.equals("Tarsier DCV-3D")){
    	   param.put("rltImgId2", rltImgId);
    	}else{
    	   param.put("rltImgId1", rltImgId);
    	}
    	param.put("id", id);
    	return doRest(url, param.toString(), "POST");
    }
}
