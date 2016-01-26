package com.qianyan.lab.util.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/26.
 */
public final class BigDecimalUtil {

    //默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 2;

    private BigDecimalUtil() {
    }

    /**
     * 精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return double类型的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确的加法运算
     *
     * @param values 所有求和的数
     * @return double类型的和
     */
    public static double add(double... values) {
        int length = values.length;
        if (length > 2) {
            Double result = add(values[0], values[1]);
            int count = 2;
            while (count < length) {
                result = add(result, values[count]);
                count++;
            }
            return result;
        }
        if (length == 2) {
            return add(values[0], values[1]);
        }
        if (length <= 0) {
            return -1;
        }
        return values[0];
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return double类型的两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return double类型的两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param values 所有求积的数
     * @return double类型的参数的积
     */
    public static double mul(double... values) {
        int length = values.length;
        if (length > 2) {
            Double result = mul(values[0], values[1]);
            int count = 2;
            while (count < length) {
                result = mul(result, values[count]);
                count++;
            }
            return result;
        }
        if (length == 2) {
            return mul(values[0], values[1]);
        }
        if (length <= 0) {
            return 0;
        }
        return values[0];
    }

    /**
     * 提供相对精准的除法运算，当除不尽时，精确到小数点后10位
     *
     * @param v1 被除数
     * @param v2 除数
     * @return double类型的两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供相对精准的除法运算，当除不尽时，由scale指定精确到小数点后scale位
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精度
     * @return double类型的两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入
     *
     * @param v     需要四舍五入的数字
     * @param scale 精度
     * @return double类型四舍五入后的值
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理，保留固定小数位数输出
     *
     * @param v     需要四舍五入的数字
     * @param scale 精度
     * @return String类型的四舍五入的字符串(千分位形式)
     */
    public static String formatMoney(double v, int scale) {
        String l = "##,##0.";
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            if (scale == 0) {
                l = "##,##0";
            }
            for (int i = 0; i < scale; i++) {
                l += "0";
            }
        }
        DecimalFormat format = new DecimalFormat(l);
        return format.format(round(v, scale));
    }

    /**
     * 提供精确的double转换为float
     *
     * @param v 需要转换的double类型的值
     * @return float类型的转换后的值
     */
    public static float toFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的double转换为int(不进行四舍五入)
     *
     * @param v 需要被转换的数字
     * @return int类型的转换后的值
     */
    public static int toInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的double转换为long
     *
     * @param v 需要被转换的数字
     * @return long类型的转换后的值
     */
    public static long toLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * 返回两个数中大的一个
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return double类型的两个数中较大的一个
     */
    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * 返回两个数中小的一个
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return double类型的两个数中较小的一个
     */
    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
