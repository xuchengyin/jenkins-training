package com.uinnova.test.step_definitions.utils.common;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期时间处理类
 *
 */
public class DateUtil {

	public static SimpleDateFormat dnhour = new SimpleDateFormat("yyyyMMddHHmmss");


	/**
	 * 明天
	 * @return
	 */
	public static String getNextDay()  {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);
		date = calendar.getTime();
		return dnhour.format(date);
	}

	/**
	 * 昨天
	 * @return
	 */
	public static String getPreviousDay()  {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);
		date = calendar.getTime();
		return dnhour.format(date);
	}

	public static String getToday(){
		return dnhour.format(new Date());
	}

	public static void main(String[] args) {
		System.out.println(getToday());
		System.out.println(getPreviousDay());
		System.out.println(getNextDay());
		Long l = Long.valueOf(getPreviousDay());
		System.out.println(l);
	}


}
