package com.qianyan.lab.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with Intellij IDEA.
 * Created by rainchen
 * User: rainchen
 * Date: 15-11-28
 * Time: 下午5:00.
 */
public class SystemException extends RuntimeException implements LabException {

    private static final long serialVersionUID = 5227756489533465212L;

    /**
     * 错误编码
     */
    private String errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 错误模块
     */
    private String moduleName;

    /**
     * 系统异常构造
     *
     * @param moduleName  模块编码
     * @param t           异常源信息
     * @param errCode     异常编码
     * @param errMsgParam 异常消息参数
     */
    public SystemException(String moduleName, Throwable t, String errCode, String... errMsgParam) {
        this(moduleName, t, true, errCode, errMsgParam);
    }

    /**
     * 系统异常构造
     *
     * @param moduleName   模块编码
     * @param t            异常源信息
     * @param cacheMessage 缓存错误信息标志
     * @param errCode      异常编码
     * @param errMsgParam  异常消息参数
     */
    public SystemException(String moduleName, Throwable t, boolean cacheMessage, String errCode, String... errMsgParam) {
        super(t);
        String errorMessage = ExceptionConfig.mm(moduleName, cacheMessage, errCode, errMsgParam);
        this.errCode = errCode;
        this.errMsg = errorMessage;
        this.moduleName = moduleName;
    }

    /**
     * 系统异常构造
     *
     * @param moduleName 模块编码
     * @param errCode    异常编码
     */
    public SystemException(String moduleName, String errCode) {
        this(moduleName, null, errCode, null);
    }

    /**
     * 系统异常构造
     *
     * @param moduleName 模块编码
     * @param t          异常源信息
     * @param errCode    异常编码
     */
    public SystemException(String moduleName, Throwable t, String errCode) {
        this(moduleName, t, errCode, null);
    }

    /**
     * 系统异常构造
     *
     * @param moduleName  模块编码
     * @param errCode     异常编码
     * @param errMsgParam 异常消息参数
     */
    public SystemException(String moduleName, String errCode, String... errMsgParam) {
        this(moduleName, null, errCode, errMsgParam);
    }

    @Override
    public String getMessage() {
        return moduleName + "-" + errCode + ":" + errMsg;
    }

    /**
     * 获取堆栈信息
     *
     * @return 堆栈信息
     */
    public final String getBackStacks() {
        if (this.getCause() == null) {
            return "";
        } else {
            return getStackTrace(this.getCause());
        }
    }

    /**
     * 输出异常堆栈信息
     *
     * @param t 异常
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        t.printStackTrace(out);
        return sw.toString();
    }

    @Override
    public String getErrCode() {
        return null;
    }

    @Override
    public String getErrDtlMsg() {
        return getMessage();
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }
}
