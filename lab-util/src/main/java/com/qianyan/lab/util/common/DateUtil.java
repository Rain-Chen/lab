package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/26.
 */
public final class DateUtil {

    //年
    public static int YEAR = 1;

    //月
    public static int MONTH = 2;

    //周
    public static int WEEK = 3;

    //天
    public static int DAY = 5;

    //小时
    public static int HOUR = 10;

    //分钟
    public static final int MINUTE = 12;

    //秒
    public static final int SECOND = 13;

    //毫秒
    public static final int MILLISECOND = 14;

    //主机时间与数据库差值
    private static Long hostDbTimeMinus = 0l;

    /**
     * 单例
     */
    private DateUtil() {
    }

    /**
     * 将Date型转为Timestamp类型
     *
     * @param date 时间对象
     * @return 时间戳
     */
    public static java.sql.Timestamp convertToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return new java.sql.Timestamp(date.getTime());
        }
    }

    /**
     * 将日期对象转换为yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String toStringYmdHmsWithM(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    /**
     * 将日期对象转换为yyyy-MM-dd HH:mm:ss SSS格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd HH:mm:ss SSS格式字符串
     */
    public static String toStringYmdHmsWithMS(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")).format(date);
    }

    /**
     * 将日期对象转换为yyyyMMddHHmmssSSS格式字符串
     *
     * @param date 时间对象
     * @return yyyyMMddHHmmssSSS格式字符串
     */
    public static String toStringYmdHmsS(Date date) {
        return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(date);
    }

    /**
     * 将日期对象转换为yyyyMMddHHmmss格式字符串
     *
     * @param date 时间对象
     * @return yyyyMMddHHmmss格式字符串
     */
    public static String toStringYmdHms(Date date) {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
    }

    /**
     * 将日期对象转换为yyyy-MM-dd格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd格式字符串
     */
    public static String toStringYmdWithM(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * 将日期对象转换为yyyy/MM/dd格式字符串
     *
     * @param date 时间对象
     * @return yyyy/MM/dd格式字符串
     */
    public static String toStringYmdWithS(Date date) {
        return (new SimpleDateFormat("yyyy/MM/dd")).format(date);
    }

    /**
     * 将日期对象转换为yyyyMMdd格式字符串
     *
     * @param date 日期对象
     * @return yyyyMMdd格式字符串
     */
    public static String toStringYmd(Date date) {
        return (new SimpleDateFormat("yyyyMMdd")).format(date);
    }

    /**
     * 将日期对象转换为yyMMdd格式字符串
     *
     * @param date 日期对象
     * @return yyMMdd格式字符串
     */
    public static String toStringYYmd(Date date) {
        return (new SimpleDateFormat("yyMMdd")).format(date);
    }

    /**
     * 将日期对象转换为yyyyMM格式字符串
     *
     * @param date 时间对象
     * @return yyyyMM格式字符串
     */
    public static String toStringYm(Date date) {
        return (new SimpleDateFormat("yyyyMM")).format(date);
    }

    /**
     * 将日期对象转换为yyyy格式字符串
     *
     * @param date 时间对象
     * @return yyyy格式字符串
     */
    public static String toStringY(Date date) {
        return (new SimpleDateFormat("yyyy")).format(date);
    }

    /**
     * 将日期对象转换为yyyy年MM月dd日格式字符串
     *
     * @param date 时间对象
     * @return yyyy年MM月dd日格式字符串
     */
    public static String toStringYmdwithChinese(Date date) {
        return (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
    }

    /**
     * 将日期对象转换为yyyy年MM月dd日HH24时MI分SS秒格式字符串
     *
     * @param date 时间对象
     * @return yyyy年MM月dd日 HH时mm分ss秒格式字符串
     */
    public static String toStringYmdwithsfm(Date date) {
        return (new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")).format(date);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss格式字符串
     * @return 日期对象
     */
    public static Date toDateYmdHmsWithM(String dateStr) {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 将yyyy-MM-dd格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd格式字符串
     * @return 日期对象
     */
    public static Date toDateYmdWithM(String dateStr) {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyy-MM-dd");
        }
    }

    /**
     * 将yyyyMMddHHmmss格式字符串转换为日期对象
     *
     * @param dateStr yyyyMMddHHmmss格式字符串
     * @return 日期对象
     */
    public static Date toDateYmdHms(String dateStr) {
        try {
            SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmmss");
            return dfm.parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyyMMddHHmmss");
        }
    }

    /**
     * 将yyyyMMdd格式字符串转换为日期对象
     *
     * @param dateStr yyyyMMdd格式字符串
     * @return 日期对象
     */
    public static Date toDateYmd(String dateStr) {
        try {
            return (new SimpleDateFormat("yyyyMMdd")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyyMMdd");
        }
    }

    /**
     * 将yyyyMM格式字符串转换为日期对象
     *
     * @param dateStr yyyyMM格式字符串
     * @return 日期对象
     */
    public static Date toDateYm(String dateStr) {
        try {
            return (new SimpleDateFormat("yyyyMM")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyyMM");
        }
    }

    /**
     * 将HHmm格式字符串转换为日期对象
     *
     * @param dateStr yyyyMM格式字符串
     * @return 日期对象
     */
    public static Date toDateHHmm(String dateStr) {
        try {
            return (new SimpleDateFormat("HHmm")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "HHmm");
        }
    }

    /**
     * 将yyyy格式字符串转换为日期对象
     *
     * @param dateStr yyyy格式字符串
     * @return 日期对象
     */
    public static Date toDateY(String dateStr) {
        try {
            return (new SimpleDateFormat("yyyy")).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr, "yyyy");
        }
    }

    /**
     * 比较当前日期(数据库日期)是否大于指定日期
     *
     * @param obj 待比较日期参数
     * @return boolean
     * @throws java.text.ParseException
     */
    public static boolean dateCompare(Object obj) {
        boolean bea = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String isDate = simpleDateFormat.format(getSysDate());

        Date date1 = null;
        Date date0;
        try {
            if (obj instanceof Date) {
                date1 = simpleDateFormat.parse(DateUtil.toStringYmdWithM((Date) obj));
            }
            if (obj instanceof String) {
                date1 = simpleDateFormat.parse((String) obj);
            }
            date0 = simpleDateFormat.parse(isDate);
            assert date1 != null;
            if (date0.after(date1)) {
                bea = true;
            }
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, obj.toString());
        }
        return bea;
    }

    /**
     * 比较两个日期的大小
     * 1、日期参数为空表示无穷小
     *
     * @param inDate1 第一个日期参数
     * @param inDate2 第二个日期参数
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     */
    public static int dateCompare(Date inDate1, Date inDate2) {
        return dateCompare(inDate1, inDate2, SECOND);
    }

    /**
     * 比较日期大小
     *
     * @param inDate1 日期1
     * @param inDate2 日期2
     * @param unit    比较精度 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     */
    public static int dateCompare(Date inDate1, Date inDate2, int unit) {
        // 字符空验证
        if (inDate1 == null && inDate2 == null) {
            // 两个日期都为空返回相等
            return 0;
        } else if (inDate1 == null) {
            // 日期1为空，日期2不为空返回-1
            return -1;
        } else if (inDate2 == null) {
            // 日期1不为空，日期为空返回2
            return 1;
        }
        return DateUtil.truncate(inDate1, unit).compareTo(DateUtil.truncate(inDate2, unit));
    }

    /**
     * 调整时间, 按照需要调整的单位调整时间
     * 例如:保留到日期的年change("20120511154440", DateUtil.YEAR, 2);返回：20140511154440
     *
     * @param inDate 传入日志
     * @param unit   调整单位 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @param amount 调整数量
     * @return 调整后的日期
     */
    public static Date change(Date inDate, int unit, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        calendar.add(unit, amount);
        return calendar.getTime();
    }

    /**
     * 按照精度要求对日期进行舍取
     * 例如:保留到日期的年truncate("20120511154440", DateUtil.YEAR);返回：20120101000000
     *
     * @param inDate 输入日期
     * @param unit   单位 年：年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @return 处理后的日期
     */
    public static Date truncate(Date inDate, int unit) {
        return DateUtils.truncate(inDate, unit);
    }

    /**
     * 按照指定的单位获取指定日志的最后值
     * 例如:保留到日期的年last("20120511154440", DateUtil.YEAR);返回：20121231235959
     *
     * @param inDate 输入日期
     * @param unit   单位 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @return 处理后的日期
     */
    public static Date last(Date inDate, int unit) {
        Date tempDate = truncate(inDate, unit);
        tempDate = change(tempDate, unit, 1);
        return change(tempDate, DateUtil.MILLISECOND, -1);

    }

    /**
     * 比较两个日期的大小
     * 1、日期参数为空表示无穷小
     *
     * @param inDate1 第一个日期参数
     * @param inDate2 第二个日期参数
     * @param unit    单位 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     */
    public static int truncateCompare(Date inDate1, Date inDate2, int unit) {
        if (inDate1 == null && inDate2 == null) {
            // 两个日期都为空返回相等
            return 0;
        } else if (inDate1 == null) {
            // 日期1为空，日期2不为空返回-1
            return -1;
        } else if (inDate2 == null) {
            // 日期1不为空，日期为空返回2
            return 1;
        } else {
            Date tempDate1 = truncate(inDate1, unit);
            Date tempDate2 = truncate(inDate2, unit);
            return tempDate1.compareTo(tempDate2);
        }

    }

    /**
     * 将日期格式化为指定的格式
     *
     * @param date    日期
     * @param pattern 格式化模式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 将日期格式化为指定的格式
     *
     * @param dateStr 日期
     * @param pattern 格式化模式
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            return (new SimpleDateFormat(pattern)).parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr);
        }
    }

    /**
     * 获取数据库当前时间
     * 根据主机时间与主机时间与系统时间的差值获取统一时间
     *
     * @return 数据库时间
     */
    public static Date getSysDate() {
        return new Date(new Date().getTime() + hostDbTimeMinus);
    }

    /**
     * 比较日期相差几天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static long datePoorYMD(String date1, String date2) {
        if (StringUtil.isNotEmptyOrNull(date1)) {
            date1 = toStringYmdHms(toDateYmd(date1));
        }
        if (StringUtil.isNotEmptyOrNull(date2)) {
            date2 = toStringYmdHms(toDateYmd(date2));
        }
        return datePoor(date1, date2, DateUtil.DAY);
    }

    /**
     * 比较日期相差几月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差月数
     */
    public static long datePoorYM(String date1, String date2) {
        Date rzDate = null;
        Date stDate = null;
        if (StringUtil.isNotEmptyOrNull(date1)) {
            date1 = toStringYmdHms(toDateYm(date1));
        } else {
            rzDate = getSysDate();
        }
        if (StringUtil.isNotEmptyOrNull(date2)) {
            date2 = toStringYmdHms(toDateYm(date2));
        } else {
            stDate = getSysDate();
        }
        if (rzDate == null) {
            rzDate = toDateYmdHms(date1);
        }
        if (stDate == null) {
            stDate = toDateYmdHms(date2);
        }
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(rzDate);
        c2.setTime(stDate);
        result = 12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return NumberUtil.getAbs(result);
    }

    private static long datePoor(String date1, String date2, int type) {
        long poor = datePoor(date1, date2);
        if (type == DAY) {
            return poor / (24 * 60 * 60 * 1000L);
        } else {
            return poor;
        }
    }

    private static long datePoor(String date1, String date2) {
        Date startDate;
        Date endDate;
        Date now = getSysDate();
        if (StringUtil.isEmptyOrNull(date1)) {
            startDate = now;
        } else {
            startDate = toDateYmdHms(date1);
        }
        if (StringUtil.isEmptyOrNull(date2)) {
            endDate = now;
        } else {
            endDate = toDateYmdHms(date2);
        }
        long start = startDate.getTime();
        long end = endDate.getTime();
        return NumberUtil.getAbs(end - start);
    }

    /**
     * 更新主机时间与数据库统一时间的差值
     *
     * @param minusTime
     */
    private static void updateHostDbTimeMinus(long minusTime) {
        synchronized (hostDbTimeMinus) {
            hostDbTimeMinus = minusTime;
        }
    }

    public static void refreshSysDate(Date nowDate) {
        Date hostDate = new Date();
        long minusTime = nowDate.getTime() - hostDate.getTime();
        updateHostDbTimeMinus(minusTime);
    }
}
