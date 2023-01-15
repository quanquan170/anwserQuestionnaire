package com.aim.questionnaire.common.utils;

import com.aim.questionnaire.common.constant.Constans;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Description: 工具类
 * @Author sunShun
 * @Date 2021-1-13 10:09:37
 * @Version V1.0
 */
public class BaseUtil {

	private static final Logger logger = LoggerFactory.getLogger(BaseUtil.class);

	/**
	 * BigDecimal类型做加法运算
	 * 
	 * @param b1 BigDecimal1
	 * @param b2 BigDecimal2
	 * @return BigDecimal值
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		if (b1 == null) {
			return b2;
		}
		if (b2 == null) {
			return b1;
		}
		return b1.add(b2);
	}

	/**
	 * Integer类型做加法运算
	 * 
	 * @param i1 int1
	 * @param i2 int2
	 * @return int值
	 */
	public static Integer add(Integer i1, Integer i2) {
		if (i1 == null) {
			return i2;
		}
		if (i2 == null) {
			return i1;
		}
		return Integer.valueOf(i1.intValue() + i2.intValue());
	}

	/**
	 * 获取多少天之后的日期
	 * 
	 * @param d 当前日期
	 * @param i 天数
	 * @return 日期
	 */
	public static Date addDay(Date d, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(5, c.get(5) + i);
		return c.getTime();
	}

	/**
	 * 获取日期加上几个星期后的日期
	 * 
	 * @param d 当前日期
	 * @param i 星期数
	 * @return 日期
	 */
	public static Date addMonth(Date d, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(2, c.get(2) + i);
		return c.getTime();
	}

	/**
	 * 字符串转日期格式
	 * 
	 * @param in 参数
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date asDate(String in) throws ParseException {
		return asDate(in, "yyyy-MM-dd");
	}

	/**
	 * 字符串按照格式转日期
	 * 
	 * @param in     字符串参数
	 * @param format 日期格式
	 * @return 日期
	 * @throws ParseException
	 */
	public static Date asDate(String in, String format) throws ParseException {
		if (isNull(in)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(in);
	}

	public static double asDouble(BigDecimal in) {
		if (in == null) {
			return 0.0D;
		}
		return in.doubleValue();
	}

	public static int asInt(BigDecimal b) {
		Double d = Double.valueOf(asDouble(b));
		return d.intValue();
	}

	public static int asInt(Integer i) {
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	public static int asInt(Long l) {
		if (l == null) {
			return 0;
		}
		return asInt(l.toString());
	}

	public static int asInt(String s) {
		if (isNull(s)) {
			return 0;
		}
		return asInt(new BigDecimal(s));
	}

	public static long asLong(Long l) {
		if (l == null) {
			return 0L;
		}
		return l.longValue();
	}

	public static List<String> asList(String[] ss) {
		List rtn = new ArrayList();
		if (ss != null && ss.length > 0) {
			for (String s : ss) {
				rtn.add(s);
			}
		}
		return rtn;
	}

	public static String asPercent(BigDecimal b) {
		if (b == null) {
			return "";
		}
		return round(multiply(b, 100), 2).toPlainString() + "%";
	}

	public static String asString(BigDecimal b, int scale) {
		return asString(b, scale, false);
	}

	public static String asString(BigDecimal b, int scale, boolean strpTrailingZeros) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (b == null) {
			return "0";
		}
		if (strpTrailingZeros) {
			if (asDouble(b) == Constans.DOUBLE_ZERO) {
				return "0";
			}
			return b.setScale(scale, 4).stripTrailingZeros().toPlainString();
		}
		return b.setScale(scale, 4).toPlainString();
	}

	public static String asString(Date in, String format) {
		if (in == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(in);
	}

	public static String asString(Integer i) {
		if (i == null) {
			return "";
		}
		return String.valueOf(i);
	}

	public static String asString(Long l) {
		if (l == null) {
			return "";
		}
		return String.valueOf(l);
	}

	public static String asStringDate(Date in) {
		return asString(in, "yyyy-MM-dd");
	}

	public static String asStringDatetime(Date in) {
		return asString(in, "yyyy-MM-dd HH:mm:ss");
	}

	public static String asStringNoZero(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		if ((b == null) || (b.doubleValue() == Constans.DOUBLE_ZERO)) {
			return "";
		}
		return b.setScale(scale, 4).toPlainString();
	}

	public static BigDecimal divide(BigDecimal b1, BigDecimal b2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if ((b1 == null) || (b1.doubleValue() == Constans.DOUBLE_ZERO)) {
			return null;
		}

		if ((b2 == null) || (b2.doubleValue() == Constans.DOUBLE_ZERO)) {
			return null;
		}
		return b1.divide(b2, scale, 4);
	}

	public static BigDecimal divide(BigDecimal b, int i, int scale) {
		return divide(b, new BigDecimal(i), scale);
	}

	public static BigDecimal divide(int i1, int i2, int scale) {
		BigDecimal b = new BigDecimal(i1);
		return divide(b, new BigDecimal(i2), scale);
	}

	public static int getByteLen(String in) {
		return in.getBytes().length;
	}

	public static String getExtensionName(String fileName) {
		if ((fileName.indexOf(Constans.IMAGE_SPLIT_SYMBOL) < 0) && (fileName.startsWith(Constans.IMAGE_PREF))) {
			return "jpg";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public static String getFromArray(String[] ary, int idx) {
		if ((ary == null) || (ary.length <= idx)) {
			return "";
		}
		return ary[idx];
	}

	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		cal.set(1, Integer.parseInt(year));
		cal.set(2, Integer.parseInt(month) - 1);
		cal.set(5, 1);
		cal.add(2, 1);
		cal.add(5, -1);
		return lPad(String.valueOf(cal.get(5)), 2, '0');
	}

	public static boolean isValidPassword(String password) {
		if ((isNull(password)) || (password.length() < Constans.PASSWORD_MIN_LENTH)) {
			return false;
		}
		if (password.matches(Constans.REGEX_PASSWORD_DATA)) {
			return false;
		}
		if (password.matches(Constans.REGEX_PASSWORD_ALPHABET)) {
			return false;
		}
		return (password.matches(
				"(?i)^((\\d+[\\da-z]*[a-z]+)|([a-z]+[\\da-z]*\\d+)|([a-z]+[\\da-z]*[a-z]*)|(\\d+[\\da-z]*\\d*))$"));
	}

	public static String getRandomString(int len) {
		String s = "";
		String[] ss = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		for (int i = 0; i < len; ++i) {
			int idx = (int) (Math.random() * ss.length);
			if (idx <= 0) {
				idx = 0;
			}
			if (idx >= ss.length - 1) {
				idx = ss.length - 1;
			}
			s = s + ss[idx];
		}
		return s;
	}

	public static String getRandomNumber(int len) {
		String s = "";
		String[] ss = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		for (int i = 0; i < len; ++i) {
			int idx = (int) (Math.random() * ss.length);
			if (idx <= 0) {
				idx = 0;
			}
			if (idx >= ss.length - 1) {
				idx = ss.length - 1;
			}
			s = s + ss[idx];
		}
		return s;
	}

	public static int getStringIndex(String s, String[] ss) {
		if ((isNull(s)) || (ss == null) || (ss.length == 0)) {
			return -1;
		}
		for (int i = 0; i < ss.length; ++i) {
			if (s.equalsIgnoreCase(ss[i])) {
				return i;
			}
		}
		return -1;
	}

	public static String getTemplatePath() {
		String rtn = getWebRootPath();
		rtn = rtn + "template";
		rtn = rtn + File.separator;
		return rtn;
	}

	public static String getWebRootPath() {
		String rtn = null;
		try {
			String cp = BaseUtil.class.getClassLoader().getResource("").toURI().getPath();
			File cpf = new File(cp);
			rtn = cpf.getParent();
			if (!(rtn.endsWith(File.separator))) {
				rtn = rtn + File.separator;
			}
			return rtn;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getFileSize(long size) {
		float f;
		long kb = 1024L;
		long mb = kb * 1024L;
		long gb = mb * 1024L;

		if (size >= gb) {
			return String.format("%.1f GB", new Object[] { Float.valueOf((float) size / (float) gb) });
		}
		if (size >= mb) {
			f = (float) size / (float) mb;
			return String.format((f > 100.0F) ? "%.0f MB" : "%.1f MB", new Object[] { Float.valueOf(f) });
		}
		if (size >= kb) {
			f = (float) size / (float) kb;
			return String.format((f > 100.0F) ? "%.0f KB" : "%.1f KB", new Object[] { Float.valueOf(f) });
		}
		return String.format("%d B", new Object[] { Long.valueOf(size) });
	}

	public static boolean inList(List<Integer> list, int i) {
		if ((list == null) || (list.size() == 0)) {
			return false;
		}
		for (Integer ii : list) {
			if (ii.intValue() != i) {
				break;
			}
			return true;
		}
		return false;
	}

	public static boolean inList(List<String> list, String s) {
		if ((list == null) || (list.size() == 0)) {
			return false;
		}
		if (isNull(s)) {
			return false;
		}
		for (String ss : list) {
			if (!(s.equals(ss))) {
				break;
			}
			return true;
		}
		return false;
	}

	public static boolean isAudio(String fileName) {
		String ext = getExtensionName(fileName);

		return (("MP3".equalsIgnoreCase(ext)) || ("WAV".equalsIgnoreCase(ext)));
	}

	public static boolean isDate(String in) {
		return isDate(in, "yyyy-MM-dd");
	}

	public static boolean isDate(String in, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.parse(in);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isDecimal(String num) {
		try {
			new BigDecimal(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isGreater(BigDecimal b1, BigDecimal b2) {
		double d1 = asDouble(b1);
		double d2 = asDouble(b2);
		return (d1 > d2);
	}

	public static boolean isGreaterOrEquals(BigDecimal b1, BigDecimal b2) {
		double d1 = asDouble(b1);
		double d2 = asDouble(b2);
		return (d1 >= d2);
	}

	public static boolean isHalf(String in) {
		if (isNull(in)) {
			return false;
		}
		return (in.length() == getByteLen(in));
	}

	public static boolean isImage(String fileName) {
		String ext = getExtensionName(fileName);
		return (("GIF".equalsIgnoreCase(ext)) || ("JPG".equalsIgnoreCase(ext)) || ("JPEG".equalsIgnoreCase(ext))
				|| ("PNG".equalsIgnoreCase(ext)) || ("BMP".equalsIgnoreCase(ext)));
	}

	public static boolean isInt(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isNull(String in) {
		return ((in == null) || ("".equals(in.trim())));
	}

	public static boolean isNotNull(String in) {
		return (!(isNull(in)));
	}

	public static boolean isEmail(String email) {
		boolean flag = false;
		try {
			String check = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMobileNumber(String mobileNumber) {
		try {
			String REGEX_MOBILE = "(134[0-8]\\d{7})|(((13([0-3]|[5-9]))|149|15([0-3]|[5-9])|166|17(3|[5-8])|18[0-9]|19[8-9])\\d{8})";
			return Pattern.matches(REGEX_MOBILE, mobileNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isBetween(Date in, Date from, Date to) {
		if (in == null) {
			return false;
		}
		if ((from != null) && (in.compareTo(from) < 0)) {
			return false;
		}
		Calendar cto = Calendar.getInstance();
		cto.setTime(to);
		cto.set(11, 23);
		cto.set(12, 59);
		cto.set(13, 59);
		return ((to == null) || (cto.getTime().compareTo(in) >= 0));
	}

	public static boolean isLowLetterOrDigit(String in) {
		if (in == null) {
			return false;
		}
		String ss = "0123456789abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < in.length(); ++i) {
			String s = in.substring(i, i + 1);
			if (ss.indexOf(s) < 0) {
				return false;
			}
		}
		return true;
	}

	public static String join(String joiner, Collection<String> c) {
		StringBuffer sb = new StringBuffer();
		if (c != null) {
			for (String s : c) {
				if (isNull(s)) {
					break;
				}
				sb.append(s);
				sb.append(joiner);
			}
			if (sb.length() > joiner.length()) {
				sb = sb.delete(sb.length() - joiner.length(), sb.length());
			}
		}
		return sb.toString();
	}

	public static String left(String s, int len) {
		if (isNull(s)) {
			return s;
		}
		if (len >= s.length()) {
			return s;
		}
		return s.substring(0, len);
	}

	public static String lPad(String in, int length, char c) {
		for (int i = 0; i < length; ++i) {
			in = c + in;
		}
		return in.substring(in.length() - length);
	}

	public static BigDecimal max(BigDecimal b1, BigDecimal b2) {
		if (isGreater(b1, b2)) {
			return b1;
		}
		return b2;
	}

	public static BigDecimal min(BigDecimal b1, BigDecimal b2) {
		if (isGreater(b1, b2)) {
			return b2;
		}
		return b1;
	}

	public static BigDecimal minus(BigDecimal b1, BigDecimal b2) {
		if (b2 == null) {
			return b1;
		}
		if (b1 == null) {
			return b2.negate();
		}
		return b1.add(b2.negate());
	}

	public static Integer minus(Integer i1, Integer i2) {
		if (i1 == null) {
			return i2;
		}
		if (i2 == null) {
			return i1;
		}
		return Integer.valueOf(i1.intValue() - i2.intValue());
	}

	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2, int scale) {
		if ((b1 == null) || (b2 == null)) {
			return null;
		}
		return b1.multiply(b2).setScale(scale, 4);
	}

	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
		if ((b1 == null) || (b2 == null)) {
			return null;
		}
		return b1.multiply(b2);
	}

	public static BigDecimal multiply(BigDecimal b, int i) {
		return multiply(b, new BigDecimal(i));
	}

	public static String repNull(String in) {
		if (isNull(in)) {
			return "";
		}
		return in;
	}

	public static List<String> removeDuplicate(List<String> inList) {
		if ((inList == null) || (inList.size() == 0)) {
			return inList;
		}
		List rtnList = new ArrayList();
		for (String s : inList) {
			if ((isNull(s)) || (rtnList.contains(s))) {
				break;
			}
			rtnList.add(s);
		}
		return rtnList;
	}

	public static String right(String s, int len) {
		if (isNull(s)) {
			return s;
		}
		if (len >= s.length()) {
			return s;
		}
		return s.substring(s.length() - len);
	}

	public static BigDecimal round(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (b == null) {
			return null;
		}
		return b.setScale(scale, 4);
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static int getAge(Date birthday) {
		Calendar c = Calendar.getInstance();
		c.setTime(birthday);
		int currentYear = Calendar.getInstance().get(1);
		int birthYear = c.get(1);
		return (currentYear - birthYear);
	}

	public static int getMin(List<Integer> list) {
		int rtn = 0;
		if (list != null) {
			for (Integer i : list) {
				rtn = Math.min(rtn, asInt(i));
			}
		}
		return rtn;
	}

	public static int getMax(List<Integer> list) {
		int rtn = 0;
		if (list != null) {
			for (Integer i : list) {
				rtn = Math.max(rtn, asInt(i));
			}
		}
		return rtn;
	}

	public static int getAverage(List<Integer> list) {
		int rtn = 0;
		if (list != null) {
			int total = 0;
			for (Integer i : list) {
				total += asInt(i);
			}
			rtn = total / list.size();
		}
		return rtn;
	}

	public static String getDefaultTargetDateFrom() {
		Date sysDate = new Date();
		return asString(sysDate, "yyyy-MM") + "-01";
	}

	public static String getDefaultTargetDateTo() throws ParseException {
		Date date = addDay(addMonth(asDate(getDefaultTargetDateFrom()), 1), -1);
		return asString(date, "yyyy-MM-dd");
	}

	public static long sub(Date d1, Date d2) {
		long time = d1.getTime() - d2.getTime();
		long day = time / 86400000L;
		return ((day > 0L) ? day : 0L - day);
	}

	public static long subDays(Date d1, Date d2) {
		long time = d1.getTime() - d2.getTime();
		long day = time / 86400000L;
		return day;
	}

	public static int getChineseWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return ((c.get(7) + 5) % 7);
	}

	public static void mkdirs(String filename) {
		File file = new File(filename);
		if (!(file.exists())) {
			if (file.isFile()) {
				if (!(file.getParentFile().exists())) {
					file.getParentFile().mkdirs();
				}
			} else if (!(file.exists())) {
				file.mkdirs();
			}
		}
	}

	public static String lmask(String in, int reserve) {
		if ((isNull(in)) || (in.length() == 1)) {
			return "*";
		}
		if (in.length() <= reserve) {
			return left(in, 1) + lPad("", in.length() - 1, '*');
		}
		return left(in, reserve) + lPad("", in.length() - reserve, '*');
	}

	public static String captureName(String ss) {
		ss = ss.substring(0, 1).toUpperCase() + ss.substring(1);
		return ss;
	}

	/**
	 * 
	 * @author sunShun
	 * @Description 判断字符串是否含有特殊字符
	 * @param str
	 * @return
	 */
	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 获取日期年份
	 * 
	 * @param date 日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return Integer.parseInt(String.format("%tY", date));
	}

	/**
	 * 获取日期月份
	 * 
	 * @param date 日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return Integer.parseInt(String.format("%tm", date));
	}

	/**
	 * 获取日期日
	 * 
	 * @param date 日期
	 * @return 日份
	 */
	public static int getDay(Date date) {
		return Integer.parseInt(String.format("%td", date));
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	/**
	 * 计算2个日期时间之间相差多少月/天/年
	 * 
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 * @param dateType  1表示天，2表示月，3表示年
	 * @return
	 */
	public static long diffDays(Date startDate, Date endDate, int dateType) {

		LocalDate beforeDate = LocalDate.of(getYear(startDate), getMonth(startDate), getDay(startDate));

		LocalDate afterDate = LocalDate.of(getYear(endDate), getMonth(endDate), getDay(endDate));
		if (dateType == 1) {
			// 天数
			return ChronoUnit.DAYS.between(beforeDate, afterDate);
		} else if (dateType == 2) {
			// 月数
			return ChronoUnit.MONTHS.between(beforeDate, afterDate);
		} else if (dateType == 3) {
			// 年数
			return ChronoUnit.YEARS.between(beforeDate, afterDate);
		} else {
			return 0L;
		}
	}

	/**
	 * 获取指定日期所在月份开始日期
	 * 
	 * @param date 指定日期
	 * @return 开始日期
	 */
	public static Date getDateBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		// 获取本月第一天的时间戳
		return c.getTime();
	}

	/**
	 * 获取指定日期所在月份结束日期
	 * 
	 * @param date 指定日期
	 * @return 结束日期
	 */
	public static Date getDateEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		// 获取本月最后一天的时间戳
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate 起始日期
	 * @param bdate  结束日期
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取单位 通过“风险主题”字段截取后两个字
	 * 
	 * @param riskTitle 风险主题
	 * @return 单位
	 */
	public static String getTsIndexUnit(String riskTitle) {
		if (!StringUtils.isEmpty(riskTitle) && riskTitle.length() >= 2) {
			return riskTitle.substring(riskTitle.length() - 2);
		}
		return riskTitle;
	}

	public static double divdouble(double v1, double v2, int scale) {
		if (scale > 0) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

	}

	public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			logger.info("开始往共享盘推送csv文件");
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} catch (Exception e) {
			logger.error("csv文件推送共享盘失败，异常信息是" + e.getMessage(), e);
		} finally {
			inputChannel.close();
			logger.info(inputChannel + "inputChannel关闭成功");
			outputChannel.close();
			logger.info(outputChannel + "outputChannel关闭成功");
		}

	}

	/**
	 * double转百分比
	 * 
	 * @param d              待转换的值
	 * @param FractionDigits 小数点后保留几位
	 * @return 百分值
	 */
	public static String getPercentFormat(double d, int FractionDigits) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
		String str = nf.format(d);
		return str;
	}

	/**
	 * double 精确的乘法运算
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static Double mul(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.multiply(b2).doubleValue();
	}

}
