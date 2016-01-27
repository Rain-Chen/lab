package com.qianyan.lab.util.common;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/27.
 */
public final class StringUtil {

    /**
     * 验证对象是否为空串""或空对象null
     *
     * @param str 验证对象
     * @return 对象的toString为空串""或者对象为null则返回true，否则返回false
     */
    public static boolean isEmptyOrNull(Object str) {
        return "".equals(str) || null == str;
    }

    /**
     * 验证对象不为null字符串不为空串""
     *
     * @param str 待验证对象
     * @return 对象不为null且对象的toString不为""则返回true 否则返回false
     */
    public static boolean isNotEmptyOrNull(Object str) {
        return !"".equals(str) && null != str;
    }


}
