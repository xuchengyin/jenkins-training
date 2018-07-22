package com.uinnova.test.step_definitions.utils.common;

/**
 * @author wsl
 * 常用工具类
 *
 */
public class CommonUtil {
	/**
	 * @param str
	 * @return
	 * 字符串是否为数字
	 */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
