package com.uinnova.test.step_definitions.api.cmv.ci;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportSearchInfo extends RestApi{

	/**
	 * 配置查询，搜索下载功能。
	 * @author lidw
	 * @param  tagIds 标签ID
	 * @param  classIds 分类ID
	 * @param  words 查询关键字
	 * @请求方式  get
	 * */

	//	如果没有参数，下载的是全部CI。
	//  有一个url对象可以处理url拼接，当时没想到，后续有需要再改。
	public String exportSearchInfo(ArrayList<String> tagIds, ArrayList<String> classIds, ArrayList<String> words){
		String url = null;
		StringBuffer tagid = new StringBuffer();
		StringBuffer classid = new StringBuffer();
		StringBuffer word = new StringBuffer();
		if(!tagIds.isEmpty() || !classIds.isEmpty() || !words.isEmpty()){
			url = ":1511/tarsier-vmdb/cmv/search/ci/exportSearchInfo?";
			if(!tagIds.isEmpty() && !"".equals(tagIds.get(0))){
				tagid.append("tagIds=");
				for(int i = 0; i < tagIds.size(); i++){
					if(i == tagIds.size() - 1){
						if((classIds.isEmpty() && !"".equals(classIds.get(0))) || (words.isEmpty() && !"".equals(tagIds.get(0)))){
							tagid.append(tagIds.get(i));
						}else{
							tagid.append(tagIds.get(i)).append("&");
						}
					}else{
						tagid.append(tagIds.get(i)).append(",");
					}
				}
			}
			if(!classIds.isEmpty() && !"".equals(classIds.get(0))){
				classid.append("classIds=");
				for(int i = 0; i < classIds.size(); i++){
					if(i == classIds.size() - 1){
						String a = words.get(0);
						if(!words.isEmpty() || !"".equals(words.get(0))){
							classid.append(classIds.get(i));
						}else{
							classid.append(classIds.get(i)).append("&");
						}
					}else{
						classid.append(classIds.get(i)).append(",");
					}
				}
			}
			if(!words.isEmpty() && !"".equals(words.get(0))){
				word.append("words=");
				for(int i = 0; i < words.size(); i++){
					if(i == words.size() - 1){
						word.append(words.get(i));
					}else{
						word.append(words.get(i)).append(",");
					}
				}
			}	
		}else{
			url = ":1511/tarsier-vmdb/cmv/search/ci/exportSearchInfo";
		}

		url = url + tagid.toString() + classid.toString() + word.toString();
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"testData/download/"+"CMV-ExportSearchInfo-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
