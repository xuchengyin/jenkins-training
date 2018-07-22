package com.uinnova.test.step_definitions.api.cmv.image;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class OnekeyRafreshImageSize extends RestApi{
/**
 * @author ldw
 * 应DMV需求，需要批量更新系统中的图标尺寸信息
 * 一键刷新系统中所有图标尺寸(支持刷新的格式：jpg|png|gif|jpeg|bmp|svg)
 * */
	public JSONObject onekeyRafreshImageSize(){
		BigDecimal domain_id = QaUtil.domain_id;
		String url = ":1511/tarsier-vmdb/cmv/image/onekeyRafreshImageSize?domainId="+domain_id.toString();
		return doRest(url, "", "GET");
	}
}
