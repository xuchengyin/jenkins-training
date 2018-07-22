package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportRltTargetCi extends RestApi{

	
	/**
	 * 查询CI关系接口
	 * @author lidw
	 *
	 * @请求方式  post
	 * */
	
	public String exportRltTargetCi(String sourceCiId, String targetCiId, String sourceClassId , String targetClassId, String classId){	//classId为关系分类ID


//		接口描述：根据关系条件导出相关目标CI数据,classId:所属关系分类ID,sourceCiId:源CI-ID,targetClassId:目标分类ID,targetCiId:目标CI-ID,sourceClassId:源分类ID
//		增加参数：targetCiId:目标CI-ID,sourceClassId:源分类ID
//		参数说明：
//
//		组合1：我是源,我要导出我的目标分类下的CI
//		classId:所属关系分类ID
//		sourceCiId:源CI-ID,
//		targetClassId:目标分类ID
//
//		组合2：我是目标,我要导出我的源分类下的CI
//		classId:所属关系分类ID
//		targetCiId:目标CI-ID,
//		sourceClassId:源分类ID
		String url = null;
		if(sourceCiId!=null && targetClassId != null && classId != null){
			url = ":1511/tarsier-vmdb/cmv/ciRlt/exportRltTargetCi?sourceCiId="+sourceCiId+"&targetClassId="+targetClassId+"&classId="+classId;
		}else if(targetCiId != null && sourceClassId != null && classId != null){
			url = ":1511/tarsier-vmdb/cmv/ciRlt/exportRltTargetCi?targetCiId="+targetCiId+"&sourceClassId="+sourceClassId+"&classId="+classId;
		}else{
			return null;
		}
		
		
		QaUtil qu = new QaUtil();
		Date now = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"testData/download/"+"CMV-ExportRltTargetCi-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
	
	//增加反向查询
	public String exportRltTargetCiReverse(String targetCiId, String sourceClassId, String classId){	//classId为关系分类ID
//		GET http://192.168.1.82:1511/tarsier-vmdb/cmv/ciRlt/exportRltTargetCi?sourceCiId=100000000010992&
//			targetClassId=100000000000596&classId=100000000000729 HTTP/1.1  //导出当前
		QaUtil qu = new QaUtil();
		Date now = new Date();
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/exportRltTargetCi?sourceCiId="+targetCiId+"&targetClassId="+sourceClassId+"&classId="+classId;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"testData/download/"+"CMV-ExportRltTargetCi-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
