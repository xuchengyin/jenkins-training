package com.uinnova.test.step_definitions.api.dmv.diagramVersion;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-03-07
 * 编写人:sunsl
 * 功能介绍:新建视图删除版本类
 */
public class RemoveDiagramVersionById extends RestApi{
    public JSONObject removeDiagramVersionById(String diagramName,String versionDesc){
    	String url = ":1511/tarsier-vmdb/dmv/diagramVersion/removeDiagramVersionById";
    	DiagramUtil diagramUtil = new DiagramUtil();
    	BigDecimal versionId = diagramUtil.getVersionIdByVersionDesc(diagramName, versionDesc);
    	return doRest(url,String.valueOf(versionId),"POST");
    }
}
