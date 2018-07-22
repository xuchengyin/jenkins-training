package com.uinnova.test.step_definitions.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 操作txt文件工具类
 * @author uinnova
 *
 */
public class TxtUtil {

	/**
	 * @param filePath
	 * @return
	 */
	public String readTxt(String filePath){
		String fileContent = ""; 
		try   
		{       
			File f = new File(filePath);      
			if(f.isFile()&&f.exists())  
			{       
				InputStreamReader read = new InputStreamReader(new FileInputStream(f),"utf-8");       
				BufferedReader reader=new BufferedReader(read);       
				String line;       
				while ((line = reader.readLine()) != null)   
				{        
					fileContent += line;       
				}         
				QaUtil.report("====read txt===="+fileContent);
				read.close();      
			}     
		} catch (Exception e)   
		{         
			e.printStackTrace();     
		}     
		return fileContent;
	}

}
