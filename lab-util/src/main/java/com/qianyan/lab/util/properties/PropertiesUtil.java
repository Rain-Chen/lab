package com.qianyan.lab.util.properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午6:06.
 */
public final class PropertiesUtil {

    private static Log log = LogFactory.getLog(PropertiesUtil.class);

    private static final String PROPERTIES_FILE_CACHE = "CONFIG_CACHE";

    private static final String DEFAULT = "lab";

    /**
     * 私有化构造方法
     */
    private PropertiesUtil() {

    }

    /**
     * 从配置文件中读取配置信息
     *
     * @param fileName 配置文件前缀名
     * @param propName 属性名称
     * @return 属性值
     */
    public static String readString(String fileName, String propName) {
        return readString(fileName, propName, true);
    }

    /**
     * 从配置文件中读取配置信息
     *
     * @param fileName  配置文件前缀
     * @param propName  属性名称
     * @param cacheFlag 是否从缓存中读取配置文件
     * @return 属性值
     */
    public static String readString(String fileName, String propName, boolean cacheFlag) {
        //方法开始打印日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readString】调用开始，参数：［propertiesFileName]=" + fileName + ",[propertiesName]=" + propName);
        }
        //处理结果
        Properties properties = readPropertyFile(DEFAULT, cacheFlag);
        if (properties.getProperty(propName) == null) {
            properties = readPropertyFile(fileName, cacheFlag);
        }
        if (properties == null || (properties.getProperty(propName) == null || properties.getProperty(propName).equals(""))) {
            readPropertyFile(fileName, cacheFlag);
        }
        String result = readString(properties, propName);
        //开始日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readString】调用结束，result=" + result);
        }
        //返回处理结果
        return result;
    }

    /**
     * 从配置文件中读取配置信息
     *
     * @param properties 配置文件
     * @param propName   属性名称
     * @return 属性值
     */
    public static String readString(Properties properties, String propName) {
        String propValue = null;
        if (properties == null) {
            Properties defProperties = readPropertyFile(DEFAULT);
            propValue = defProperties.getProperty(propName);
            if (!propValue.equals("")) {
                return propValue.trim();
            }
        }
        assert properties != null;
        propValue = properties.getProperty(propName);
        if (!propValue.equals("")) {
            return propValue.trim();
        }
        return propValue;
    }

    /**
     * 从配置文件中读取配置信息
     *
     * @param properties 配置文件
     * @param propName   属性名称
     * @return 值为true返回true，否则返回false
     */
    public static boolean readBooblean(Properties properties, String propName) {
        boolean result = false;
        String propValue = readString(properties, propName);
        return result = "true".equals(propValue);
    }

    /**
     * 从配置文件中读取配置信息
     *
     * @param properties   配置文件
     * @param propertyName 属性名称
     * @return 返回指定属性的Int值
     */
    public static int readInt(Properties properties, String propertyName) {
        boolean result = false;
        String propValue = readString(properties, propertyName);
        return Integer.parseInt(propValue);
    }

    /**
     * 从缓存中读取配置文件
     *
     * @param fileName  配置文件名称
     * @param cacheFlag 配置文件缓存
     * @return 配置文件
     */
    public static Properties readPropertyFile(String fileName, boolean cacheFlag) {
        //开始日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readPropertyFile】调用开始，参数：［fileName]=" + fileName + ",[cacheFlag]=" + cacheFlag);
        }
        Properties properties = null;
        if (cacheFlag) {
            //TODO 缓存中获取
            //properties = CacheManager
            if (properties == null) {
                //properties = readPropertyFile(fileName);
                //CacheManager.getInstance().put();
            }
        } else {
            properties = readPropertyFile(fileName);
        }
        //开始日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readPropertyFile】调用结束");
        }
        return properties;
    }

    /**
     * 不缓存读取配置文件
     *
     * @param fileName 配置文件名称
     * @return 配置文件
     */
    public static Properties readPropertyFile(String fileName) {
        //开始日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readPropertyFile】调用开始，参数：［fileName]=" + fileName);
        }
        Properties properties = new Properties();
        //配置文件名称
        String realFilePath = fileName + ".properties";
        //配置文件名称要求全部小写
        InputStream inputStream = null;
        try {
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(realFilePath);
            if (inputStream == null) {
                log.error("读取配置文件【" + fileName + "】不存在");
                throw new RuntimeException("读取配置文件【" + fileName + "】不存在");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("读取配置文件【" + fileName + "】错误", e);
            throw new RuntimeException("读取配置文件【" + fileName + "】错误", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("关闭配置文件【" + fileName + "】错误", e);
                }
            }
        }
        //开始日志
        if (log.isDebugEnabled()) {
            log.debug("方法【readPropertyFile】调用结束");
        }
        return properties;
    }

}
