package com.uinnova.test.step_definitions.utils.itv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class CiAttr {
	private HashMap map = new HashMap(); // 存储查出CI的所有信息
	private BigDecimal ciId; // CIID
	private BigDecimal classId; // CI分类ID
	private String classImageUrl; // 存储CI所在分类使用的图片路径
	private String classImageName;// 存储CI分类使用的图标名称

	public CiAttr(String CiCode) {
		String data = "select * from cc_ci WHERE CI_CODE = '" + CiCode + "' AND " + "DATA_STATUS = 1 AND DOMAIN_ID = "
				+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(data);
		if (list.size() == 0) {
			System.out.println("--------ITV:未查找到CI信息--------");
		} else {
			for (int i = 0; i < list.size(); i++) {
				map = (HashMap) list.get(i);
			}
		}
		ciId = (BigDecimal) map.get("ID");
		classId = (BigDecimal) map.get("CLASS_ID");

		// 查询分类使用iconUrl
		String classIconUrlSql = "SELECT ICON from cc_ci_class WHERE ID = " + classId
				+ " AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		ArrayList classIconUrlList = JdbcUtil.executeQuery(classIconUrlSql);
		HashMap mapClass = (HashMap) classIconUrlList.get(0);
		classImageUrl = mapClass.get("ICON").toString();

		// 根据iconUrl查询使用imageName
		String iconNameSql = "SELECT IMG_NAME FROM cc_image WHERE IMG_PATH = '" + classImageUrl
				+ "' AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		ArrayList iconNameList = JdbcUtil.executeQuery(iconNameSql);
		HashMap classImg = (HashMap) iconNameList.get(0);
		classImageName = classImg.get("IMG_NAME").toString();
	}

	public BigDecimal getID() {

		return ciId;
	}

	public BigDecimal getClassID() {

		return classId;
	}

	public String getClassIcon() {

		return classImageUrl;
	}

	public String getClassIconImage() {

		return classImageName;
	}
}
