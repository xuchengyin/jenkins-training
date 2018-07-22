package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 分类之间的关系工具类
 * @author wsl
 *
 */
public class ClassRltUtil {
	
	public BigDecimal getClassRltId(String sourceClassName, String targetClassName, String rltClassName){
		BigDecimal classRltID =new BigDecimal(0);
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal sourceClassId = ciClassUtil.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ciClassUtil.getCiClassId(targetClassName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		String sql = "select ID, CLASS_ID, SOURCE_CLASS_ID, TARGET_CLASS_ID from cc_ci_class_rlt where CLASS_ID=" + rltClassId + " and SOURCE_CLASS_ID =" + sourceClassId
				+ " and TARGET_CLASS_ID=" + targetClassId +" and DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		if (list!=null && list.size()>0){
			Map map = (Map) list.get(0);
			classRltID = (BigDecimal) map.get("ID");
		}
		return classRltID;
	}
	
	

}
