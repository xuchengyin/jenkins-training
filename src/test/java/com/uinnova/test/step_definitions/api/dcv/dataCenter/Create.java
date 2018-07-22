package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.junit.Test;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


/**
 * @author wjx
 * @param id 数据中心ID
 * @param dataCenterid 数据中心编号
 * @param dataCentername 数据中心名称
 * @param desc 数据中心描述
 * @param picUrl 数据中心图片
 * @return 数据中心ID
 * 功能介绍： 创建、更新数据中心
 */

public class Create {
	/*
	 *创建数据中心
	 */
	public String saveOrUpdateDc(String dataCenterid, String dataCentername, String desc, String picUrl) {
		String url = ":1511/tarsier-vmdb/dcv/dataCenter/create";
		JSONObject param = getCiObj(null, dataCenterid, dataCentername, desc, picUrl);
		String ret = QaUtil.loadRest(url, param.toString(), "POST");
		return ret;
	}
	/*
	 *更新数据中心信息
	 */

	public String saveOrUpdateDc(BigDecimal id,String dataCenterid, String dataCentername, String desc, String picUrl) {
		String url = ":1511/tarsier-vmdb/dcv/dataCenter/create";
		JSONObject param = getCiObj(id, dataCenterid, dataCentername, desc, picUrl);
		String ret = QaUtil.loadRest(url, param.toString(), "POST");
		return ret;
	}

    private JSONObject getCiObj(BigDecimal id, String dataCenterid, String dataCentername, String desc, String picUrl) {
        JSONObject ci = new JSONObject();
        JSONObject param = new JSONObject();
        JSONObject attrs = new JSONObject();

        if (id == null) {
            ci.put("id", "");
        } else {
            ci.put("id", id);
        }
        ci.put("classId", 1);
        ci.put("ciCode", dataCenterid);
        attrs.put("编号", dataCenterid);
        attrs.put("名称", dataCentername);

        if (id == null) {
            attrs.put("DESC", "");
        } else {
            attrs.put("DESC", desc);
        }


        if (id == null) {
            attrs.put("PICURL", "");
        } else {
            attrs.put("PICURL", picUrl);
        }

        param.put("ci", ci);
        param.put("attrs", attrs);
        return param;
    }
	
}