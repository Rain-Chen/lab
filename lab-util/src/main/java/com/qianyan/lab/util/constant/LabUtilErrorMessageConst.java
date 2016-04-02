package com.qianyan.lab.util.constant;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午5:50.
 */
public interface LabUtilErrorMessageConst {
    /**
     * 模块名称
     */
    String MODULE_NAME = "labutil";
    /**
     * {0}对象调用错误
     */
    String SYS_CALL_ERROR = "110000";

    String NOT_FOUND_MESSAGE = "未找到此错误编码对应信息,请到【{0}-message.properties】中进行配置";

    /**
     * 200001=XMLBean初始化，XmlNode不能为null
     */
    String ERROR_MESSAGE_200001 = "200001";

    /**
     * 200002=XMLBean初始化，XmlNode长度必须为1
     */
    String ERROR_MESSAGE_200002 = "200002";

    /**
     * 200003=字符不是有效的[{0}]日期格式
     */
    String ERROR_MESSAGE_200003 = "200003";

    /**
     * 200004=[{0}]不是有效的[{1}]格式
     */
    String ERROR_MESSAGE_200004 = "200004";

    /**
     * 200005=字符数组[{0}]编码失败
     */
    String ERROR_MESSAGE_200005 = "200005";

    /**
     * 200006=文件加载失败，路径为[{0}]
     */
    String ERROR_MESSAGE_200006 = "200006";

    /**
     * 200007=文件[{0}]创建错误
     */
    String ERROR_MESSAGE_200007 = "200007";

    /**
     * 200008=[{0}]报文解析错误
     */
    String ERROR_MESSAGE_200008 = "200008";

    /**
     * 200009=[{0}]报文转换错误
     */
    String ERROR_MESSAGE_200009 = "200009";

    /**
     * 200010=[{0}]不是有效的[{1}]编码
     */
    String ERROR_MESSAGE_200010 = "200010";

    /**
     * 200011=缓存配置文件[{0}]加载失败!
     */
    String ERROR_MESSAGE_200011 = "200011";

    /**
     * 200012=邮件发送失败
     */
    String ERROR_MESSAGE_200012 = "200012";



    ////////////////缓存相关错误，编号从200100开始/////////////////////
    /**
     * 200100=启动缓存引擎失败，引擎名称[{0}],实现类为[{1}]
     */
    String ERROR_MESSAGE_200100 = "200100";

    /**
     * 200101=启动缓存引擎失败，引擎名称[{0}],实现类[{1}]无法加载或实现类不存在
     */
    String ERROR_MESSAGE_200101 = "200101";

    /**
     * 200102=缓存项目{0}对应的缓存容器{1}未配置!
     */
    String ERROR_MESSAGE_200102 = "200102";

    /**
     * 200103=缓存项目{0}未在[cache-cfg.xml]配置!
     */
    String ERROR_MESSAGE_200103 = "200103";

    /**
     * 200104=从{0}缓存引擎中读取缓存项目{1}失败!
     */
    String ERROR_MESSAGE_200104 = "200104";


}
