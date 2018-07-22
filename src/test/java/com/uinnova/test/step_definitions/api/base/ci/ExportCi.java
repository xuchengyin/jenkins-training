package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportCi {

	/**
	 * 导出classID的所有分类
	 * @param classId
	 * @return
	 */
	public String exportSingleCiClass(BigDecimal classId){
		String url = ":1511/tarsier-vmdb/cmv/ci/exportCi?ciClassIds="+classId;
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"/testData/download/"+"CI-singleClass"+classId+"-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}

	/**
	 * @return
	 */
	public String exportAllCiClass(){
		String url = ":1511/tarsier-vmdb/cmv/ci/exportCi";

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"/testData/download/"+"CI-allCiCls-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}

	/**
	 * @param classId
	 * @return
	 */
	public String exportSingleCi(BigDecimal classId){
		String url = ":1511/tarsier-vmdb/cmv/ci/exportCi?ciClassIds="+classId+"&hasData=1";
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"/testData/download/"+"CI-singleCiData"+classId+"-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}

	/**
	 * @return
	 */
	public String exportAllCi(){
		String url = ":1511/tarsier-vmdb/cmv/ci/exportCi?hasData=1";
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"/testData/download/"+"CI-allCiData"+format.format(now)+".xls";
		
		if(QaUtil.downloadFile_urlWithoutFileName2(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
	
	
}
