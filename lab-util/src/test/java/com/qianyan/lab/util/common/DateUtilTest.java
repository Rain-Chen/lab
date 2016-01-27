package com.qianyan.lab.util.common;

import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void testConvertToTimestamp() throws Exception {
        System.out.println(DateUtil.convertToTimestamp(new Date()));
        System.out.println(DateUtil.convertToTimestamp(new Date()).getClass());
    }

    @Test
    public void testToStringYmdHmsWithM() throws Exception {
        System.out.println(DateUtil.toStringYmdHmsWithM(new Date()));
    }

    @Test
    public void testToStringYmdHmsWithMS() throws Exception {
        System.out.println(DateUtil.toStringYmdHmsWithMS(new Date()));
    }

    @Test
    public void testToStringYmdWithM() throws Exception {
        System.out.println(DateUtil.toStringYmdWithM(new Date()));
    }

    @Test
    public void testToStringYmdWithS() throws Exception {
        System.out.println(DateUtil.toStringYmdWithS(new Date()));
    }

    @Test
    public void testToStringYmdHms() throws Exception {
        System.out.println(DateUtil.toStringYmdHms(new Date()));
        System.out.println(DateUtil.toStringYmdHmsS(new Date()));
    }

    @Test
    public void testToStringYmd() throws Exception {
        System.out.println(DateUtil.toStringYmd(new Date()));
    }

    @Test
    public void testToStringYYmd() throws Exception {
        System.out.println(DateUtil.toStringYYmd(new Date()));
    }

    @Test
    public void testToStringYm() throws Exception {
        System.out.println(DateUtil.toStringYm(new Date()));
    }

    @Test
    public void testToStringY() throws Exception {
        System.out.println(DateUtil.toStringY(new Date()));
    }

    @Test
    public void testToStringYmdwithChinese() throws Exception {
        System.out.println(DateUtil.toStringYmdwithChinese(new Date()));
    }

    @Test
    public void testToStringYmdwithsfm() throws Exception {
        System.out.println(DateUtil.toStringYmdwithsfm(new Date()));
    }

    @Test
    public void testToDateYmdHmsWithM() throws Exception {
        System.out.println(DateUtil.toDateYmdHmsWithM("2016-01-27 15:43:50"));
        //测试报错
        //System.out.println(DateUtil.toDateYmdHmsWithM("2016-01-27 abc"));
    }

    @Test
    public void testToDateYmdWithM() throws Exception {
        System.out.println(DateUtil.toDateYmdWithM("2016-01-27"));
    }

    @Test
    public void testToDateYmdHms() throws Exception {
        System.out.println(DateUtil.toDateYmdHms("20160127174756"));
    }

    @Test
    public void testToDateYmd() throws Exception {
        System.out.println(DateUtil.toDateYmd("20160127"));
    }

    @Test
    public void testToDateYm() throws Exception {
        System.out.println(DateUtil.toDateYm("201601"));
    }

    @Test
    public void testToDateHHmm() throws Exception {
        System.out.println(DateUtil.toDateHHmm("1751"));
    }

    @Test
    public void testToDateY() throws Exception {
        System.out.println(DateUtil.toDateY("2016"));
    }

    @Test
    public void testDateCompare() throws Exception {
        System.out.println(DateUtil.dateCompare("2016-01-25")); //true
        System.out.println(DateUtil.dateCompare(DateUtil.toDateYmd("20160125")));   //true
        System.out.println(DateUtil.dateCompare("2099-01-01")); //false
        System.out.println(DateUtil.dateCompare(DateUtil.toDateYmd("20990101")));   //false
    }

    @Test
    public void testDateCompare1() throws Exception {
        Date date1 = DateUtil.toDateYmdHms("20160127175900");
        Date date2 = DateUtil.toDateYmdHms("20160127180000");
        Date date3 = DateUtil.toDateYmdHms("20160127180000");
        System.out.println(DateUtil.dateCompare(date1, date2)); //当第一个时间早于第二个时间则返回-1
        System.out.println(DateUtil.dateCompare(date2, date1)); //当第一个时间晚于第二个时间则返回1
        System.out.println(DateUtil.dateCompare(date2, date3)); //当两个时间相等时返回0
    }

    @Test
    public void testDateCompare2() throws Exception {
        Date date1 = DateUtil.toDateYmdHms("20160127175900");
        Date date2 = DateUtil.toDateYmdHms("20160127180000");
        //第三个参数（unit）为比较的精度 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
        System.out.println(DateUtil.dateCompare(date1, date2, 1));
        System.out.println(DateUtil.dateCompare(date1, date2, 13));
    }

    @Test
    public void testChange() throws Exception {
        Date date = DateUtil.toDateYmdHms("20170127190225");
        //调整时间，第一个参数时间对象
        //第二个为调整的位置（年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13）
        //第三个参数为调整的大小，0为不调整
        System.out.println(DateUtil.change(date, 1, 0));    //Fri Jan 27 19:02:25 CST 2017
        System.out.println(DateUtil.change(date, 1, 1));    //Sat Jan 27 19:02:25 CST 2018
        System.out.println(DateUtil.change(date, 2, 2));    //Mon Mar 27 19:02:25 CST 2017
        System.out.println(DateUtil.change(date, 3, 4));    //Fri Feb 24 19:02:25 CST 2017
        System.out.println(DateUtil.change(date, 13, 5));   //Fri Jan 27 19:02:30 CST 2017
    }

    @Test
    public void testTruncate() throws Exception {
        //精度向上取舍时间（年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13）
        Date date = DateUtil.toDateYmdHms("20170127190225");
        System.out.println(DateUtil.truncate(date, 1));     //Sun Jan 01 00:00:00 CST 2017
        System.out.println(DateUtil.truncate(date, 5));     //Fri Jan 27 00:00:00 CST 2017
        System.out.println(DateUtil.truncate(date, 12));    //Fri Jan 27 19:02:00 CST 2017
    }

    @Test
    public void testLast() throws Exception {
        //精度向下取舍时间（年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13）
        Date date = DateUtil.toDateYmdHms("20170127190225");
        System.out.println(DateUtil.last(date, 1));     //Sun Dec 31 23:59:59 CST 2017
        System.out.println(DateUtil.last(date, 5));     //Fri Jan 27 23:59:59 CST 2017
        System.out.println(DateUtil.last(date, 12));    //Fri Jan 27 19:02:59 CST 2017
    }

    @Test
    public void testTruncateCompare() throws Exception {
        Date date1 = DateUtil.toDateYmdHms("20170127190225");
        Date date2 = DateUtil.toDateYmdHms("20170127190325");
        Date date3 = DateUtil.toDateYmdHms("20170127190325");
        System.out.println(DateUtil.truncateCompare(date1, date2, 12)); //-1
        System.out.println(DateUtil.truncateCompare(date2, date3, 12)); //0
        System.out.println(DateUtil.truncateCompare(date2, date1, 12)); //1
    }

    @Test
    public void testFormat() throws Exception {
        Date date = DateUtil.toDateYmdHms("20170127190225");
        System.out.println(DateUtil.format(date, "yyyy|MM|dd HH|mm|ss"));   //2017|01|27 19|02|25
    }

    @Test
    public void testParse() throws Exception {
        String dateStr = "2017|01|27 19|02|25";
        System.out.println(DateUtil.parse(dateStr, "yyyy|MM|dd HH|mm|ss"));  //Fri Jan 27 19:02:25 CST 2017
    }

    @Test
    public void testGetSysDate() throws Exception {
        System.out.println(DateUtil.getSysDate());  //Wed Jan 27 19:21:47 CST 2016 系统当前时间
    }

    @Test
    public void testDatePoorYMD() throws Exception {
        System.out.println(DateUtil.datePoorYMD("20170127", "20170127"));    //0
        System.out.println(DateUtil.datePoorYMD("20170127", "20170128"));    //1
        System.out.println(DateUtil.datePoorYMD("20170227", "20170328"));    //29
    }

    @Test
    public void testDatePoorYM() throws Exception {
        System.out.println(DateUtil.datePoorYM("201701", "201701"));    //0
        System.out.println(DateUtil.datePoorYM("201701", "201702"));    //1
    }

    @Test
    public void testRefreshSysDate() throws Exception {
        DateUtil.refreshSysDate(new Date());
    }
}