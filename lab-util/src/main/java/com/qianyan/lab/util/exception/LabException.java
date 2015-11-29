package com.qianyan.lab.util.exception;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午4:54.
 */
public interface LabException {

    /**
     * 获取错误编码
     *
     * @return 错误编码
     */
    String getErrCode();

    /**
     * 获取错误详细信息
     *
     * @return 错误详细信息
     */
    String getErrDtlMsg();

    /**
     * 获取错误描述信息
     *
     * @return 错误描述信息
     */
    String getErrMsg();
}
