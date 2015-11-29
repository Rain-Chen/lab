package com.qianyan.lab.util.exception;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.properties.PropertiesUtil;

import java.text.MessageFormat;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午5:35.
 */
public class ExceptionConfig {

    public static String mm(String moduleName, String errCode, String... args) {
        return mm(moduleName, true, errCode, args);
    }

    /**
     * 格式化异常信息
     *
     * @param moduleName   异常模块名称
     * @param cacheMessage 缓存异常信息
     * @param errCode      异常编码
     * @param args         异常信息参数
     * @return 格式化替换后的异常信息描述
     */
    public static String mm(String moduleName, boolean cacheMessage, String errCode, String... args) {
        String message;
        String ERROR_MESSAGE = "message";
        if (moduleName == null) {
            message = PropertiesUtil.readString(ERROR_MESSAGE, errCode, cacheMessage);
        } else {
            message = PropertiesUtil.readString(moduleName + "-" + ERROR_MESSAGE, errCode, cacheMessage);
        }
        if (message == null) {
            return MessageFormat.format(LabUtilErrorMessageConst.NOT_FOUND_MESSAGE, moduleName);
        }
        if (args != null) {
            message = MessageFormat.format(message, args);
        }
        return message;
    }

    public static String formatMessage(String message, Object... args) {
        return MessageFormat.format(message, args);
    }
}
