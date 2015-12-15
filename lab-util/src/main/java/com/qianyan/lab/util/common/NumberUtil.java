package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-12-12
 * Time: 下午3:42.
 */
public class NumberUtil {

    public static Long longValueOf(String s) {
        Long l;
        try {
            l = Long.valueOf(s);
        } catch (NumberFormatException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200004, s, "数字");
        }
        return l;
    }

    public static Double doubleValueOf(String s) {
        Double d;
        try {
            d = Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200004, s, "数字");
        }
        return d;
    }

    public static Long parseLong(String s) {
        Long l;
        try {
            l = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200004, s, "数字");
        }
        return l;
    }

    public static int intValueOf(String s) {
        int i;
        try {
            i = Integer.valueOf(s);
        } catch (NumberFormatException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200004, s, "数字");
        }
        return i;
    }

    public static int intParseInt(String s) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200004, s, "数字");
        }
        return i;
    }

    public static Double getDoubleFormObj(Object obj) {
        if ("".equals(obj) || obj == null) {
            return 0.00;
        } else {
            return NumberUtil.doubleValueOf(obj.toString());
        }
    }

    public static int getIntFromObj(Object obj) {
        if ("".equals(obj) || obj == null) {
            return 0;
        } else {
            return NumberUtil.intParseInt(obj.toString());
        }
    }

    public static Integer getIntegerFromObj(Object obj) {
        if ("".equals(obj) || obj == null) {
            return null;
        } else {
            return NumberUtil.intParseInt(obj.toString());
        }
    }

    public static long getLongFromObj(Object obj) {
        if ("".equals(obj) || obj == null) {
            return 0;
        } else {
            return NumberUtil.longValueOf(obj.toString());
        }
    }

    public static String formatDouble(Double money) {
        if (money == null) {
            return "0.00";
        } else {
            Format format = new DecimalFormat("#0.00");
            return format.format(money);
        }
    }

    public static long getAbs(long obj) {
        if (obj < 0) {
            return (0 - obj);
        } else {
            return obj;
        }
    }
}
