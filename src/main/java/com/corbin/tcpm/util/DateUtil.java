package com.corbin.tcpm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间的工具类
 * 
 * @author chong
 */
public class DateUtil {

	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_MONTH = "yyyy-MM";
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 英文全称 如：2010/12/01 23:15:06
	 */
	public static String FORMAT_LONG_OVERLINE = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 根据预设格式返回当前日期
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDaysAbs(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) Math.abs(t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	public static String timeStrComplete(String timeStr) {
		if (null == timeStr || "".equals(timeStr)) {
			return null;
		}
		String str = timeStr;
		str = str.replaceAll("[0-9]", "0");

		String tempStr = "0000-00-00 00:00:00";
		String tempSplitStr = "1970-01-01 00:00:00";
		int index = tempStr.indexOf(str);
		if (0 == index) {
			/**
			 * 说明符合日期格式 截取tempStr后面的部分与timeStr拼接构成时间
			 */
			String lastStr = tempSplitStr.substring(timeStr.length());
			return timeStr + lastStr;
		}
		return null;
	}

	public static Timestamp str2Time(String timeStr) {
		if (null == timeStr || "".equals(timeStr)) {
			return null;
		}
		String str = timeStr;
		str = str.replaceAll("[0-9]", "0");

		String tempStr = "0000-00-00 00:00:00";
		String tempSplitStr = "1970-01-01 00:00:00";
		int index = tempStr.indexOf(str);
		if (0 == index) {
			/**
			 * 说明符合日期格式 截取tempStr后面的部分与timeStr拼接构成时间
			 */
			String lastStr = tempSplitStr.substring(timeStr.length());
			return Timestamp.valueOf(timeStr + lastStr);
		}

		return null;
	}

	public static Timestamp str2TimeMax(String timeStr) {
		if (null == timeStr || "".equals(timeStr)) {
			return null;
		}
		String str = timeStr;
		str = str.replaceAll("[0-9]", "0");

		String tempStr = "0000-00-00 00:00:00";
		String tempSplitStr = "1970-01-01 23:59:59";
		int index = tempStr.indexOf(str);
		if (0 == index) {
			/**
			 * 说明符合日期格式 截取tempStr后面的部分与timeStr拼接构成时间
			 */
			String lastStr = tempSplitStr.substring(timeStr.length());
			return Timestamp.valueOf(timeStr + lastStr);
		}

		return null;
	}

	public static Date str2DateTime(String timeStr) {
		if (null == timeStr || "".equals(timeStr)) {
			return null;
		}
		String str = timeStr;
		str = str.replaceAll("[0-9]", "0");

		String tempStr = "0000-00-00 00:00:00";
		String tempSplitStr = "1970-01-01 00:00:00";
		int index = tempStr.indexOf(str);
		if (0 == index) {
			/**
			 * 说明符合日期格式 截取tempStr后面的部分与timeStr拼接构成时间
			 */
			String lastStr = tempSplitStr.substring(timeStr.length());
			return parse(timeStr + lastStr);
		}

		return null;
	}

	public static Date str2DateTimeMax(String timeStr) {
		if (null == timeStr || "".equals(timeStr)) {
			return null;
		}
		String str = timeStr;
		str = str.replaceAll("[0-9]", "0");

		String tempStr = "0000-00-00 00:00:00";
		String tempSplitStr = "1970-01-01 23:59:59";
		int index = tempStr.indexOf(str);
		if (0 == index) {
			/**
			 * 说明符合日期格式 截取tempStr后面的部分与timeStr拼接构成时间
			 */
			String lastStr = tempSplitStr.substring(timeStr.length());
			return parse(timeStr + lastStr);
		}

		return null;
	}

	/**
	 * 字符串数字转化为double
	 * 
	 * @param string
	 * @return
	 */
	public static double str2double(String string) {
		if (null == string || "".equals(string)) {
			return 0;
		}
		try {
			return Double.valueOf(string);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static String formatInitTime(Date date) {
		String time = format(date, getDatePattern());
		String timeStr = " 00:00:00";
		time = time.split(" ")[0] + timeStr;
		return time;
	}

	/**
	 * 得到本月第一天的日期
	 * 
	 * @Methods Name getFirstDayOfMonth
	 * @return Date
	 */
	public static String getCurrFirstDayOfMonth() {
		Calendar cDay = Calendar.getInstance();
		cDay.set(Calendar.DAY_OF_MONTH, 1);
		return formatInitTime(cDay.getTime());
	}

	/**
	 * 得到本月最后一天的日期
	 * 
	 * @Methods Name getLastDayOfMonth
	 * @return Date
	 */
	public static String getCurrLastDayOfMonth() {
		Calendar cDay = Calendar.getInstance();
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatInitTime(cDay.getTime());
	}

	/**
	 * 获取当前季度第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrFirstDayOfQuarter() {
		Calendar cDay = Calendar.getInstance();
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.JANUARY);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.APRIL);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
			cDay.set(Calendar.MONTH, Calendar.JULY);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.OCTOBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return formatInitTime(cDay.getTime());
	}

	/**
	 * 得到本季度最后一天的日期
	 * 
	 * @Methods Name getLastDayOfQuarter
	 * @return Date
	 */
	public static String getCurrLastDayOfQuarter() {
		Calendar cDay = Calendar.getInstance();
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.MARCH);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.JUNE);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
			cDay.set(Calendar.MONTH, Calendar.AUGUST);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatInitTime(cDay.getTime());
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static String getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static String getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return String
	 */
	public static String getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return format(currYearFirst);
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static String getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return format(currYearLast);
	}

}
