package com.uinnova.test.step_definitions.utils.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author wsl
 * 操作压缩文件工具类
 *
 */
public class ZipUtil {

	/**
	 * @param zipFile
	 * @param fileName
	 * @return
	 * @throws Exception
	 * 读取压缩文件zipFile中文件fileName的内容
	 */
	public static String readFileContent(String zipFile, String fileName) throws Exception { 
		String fileContent = ""; 
		try{
			ZipFile zf=new ZipFile(zipFile);
			InputStream in=new BufferedInputStream(new FileInputStream(zipFile));
			ZipInputStream zin=new ZipInputStream(in);
			ZipEntry ze;
			while((ze=zin.getNextEntry())!=null){
				if(!ze.isDirectory()){
					if (ze.getName().compareToIgnoreCase(fileName)==0){
						BufferedReader reader;
						reader = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), "utf-8"));
						String line=null;
						while((line=reader.readLine())!=null){
							fileContent += line;  
						}
						reader.close();
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}  

}
