package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import com.qianyan.lab.util.properties.PropertiesUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with Intellij IDEA
 * by rain chen on 2016/1/27.
 */
public final class StringUtil {

    //文件路径
    public static final String FILE_SEPARATOR_REG = File.separator.equals("/") ? File.separator : File.separator + File.separator;

    //classpath前缀
    public static final String FILE_PRE_CLASSPATH = "classpath:";

    //webRoot前缀
    private static final String FILE_PRE_WEB_ROOT = "webroot:";

    //服务器文件路径
    private static final String FILE_PRE_SERVER = "server:";

    private static final Log log = LogFactory.getLog(StringUtil.class);

    //web工程根路径，默认值
    private static String webRootPath = PropertiesUtil.readString("lab-util", "");

    //单利
    private StringUtil() {

    }

    /**
     * 系统工程根路径
     *
     * @return 若未手动设置则会是lab-util设置的值，若手动设置则未设置的值
     */
    public static String getWebRootPath() {
        return webRootPath;
    }

    /**
     * 设置web工程根路径
     *
     * @param path 路径
     */
    public static void setWebRootPath(String path) {
        webRootPath = path;
    }


    /**
     * 对象为空对象(null)时，返回空字符串("")
     *
     * @param obj 待验证字符串对象
     * @return 若对象为空则返回"".若不为空直接返回
     */
    public static String convertEmptyWhenNull(String obj) {
        if (obj == null) {
            return "";
        } else {
            return obj;
        }
    }

    /**
     * 验证字符串数字是否为正数
     *
     * @param str 字符串数字
     * @return 正数:true 其他:false
     */
    public static boolean isPositiveNum(String str) {
        Pattern pattern;
        if (str == null) {
            return false;
        }
        pattern = Pattern.compile("^\\d+$");
        Matcher isNumber = pattern.matcher(str);
        return isNumber.matches();
    }

    /**
     * 验证两个对象是否相等
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 都为空相等返回true，有一个为空一个不为空返回false，最后调用对象的equals方法比较
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        return obj1 == null && obj2 == null || obj1 != null && obj2 != null && obj1.equals(obj2);
    }

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

    /**
     * 验证字符串是否为null或空字符串("")
     *
     * @param str 待验证对象
     * @return null或者""返回true，否则返回false
     */
    public static boolean isSpaceOrNull(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 验证字符串是否为数字
     *
     * @param str 待验证字符串对象
     * @return 数字:true 非数字:false
     */
    public static boolean isNumber(String str) {
        if (isSpaceOrNull(str)) {
            return false;
        }
        Pattern pattern;
        pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        Matcher isNumber = pattern.matcher(str);
        return isNumber.matches();
    }

    /**
     * 自定义正则表达式验证
     *
     * @param str   待验证字符串对象
     * @param regex 正则
     * @return 通过:true 不通过:false
     */
    public static boolean regularValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 将字节数组以Base64编码，默认使用Commons Codec的实现
     *
     * @param bytes 待编码的字节数组
     * @return 经Base64编码后的字符串
     */
    public static String encodeBase64(byte[] bytes) {
        Base64 encoder = new Base64();
        return encoder.encodeToString(bytes);
    }

    /**
     * 将字符串以Base64编码，默认使用Commons Codec的实现
     *
     * @param str 待编码的字符串
     * @return 经过Base64编码后的字符串
     */
    public static String encodeBase64(String str) {
        Base64 encoder = new Base64();
        return new String(encoder.encode(str.getBytes()));
    }

    /**
     * 将对象以Base64编码，默认使用Commons Codec的实现
     *
     * @param obj 待编码的对象
     * @return 经过编码后的对象
     */
    public static Object encodeBase64(Object obj) {
        Base64 encoder = new Base64();
        try {
            return encoder.encode(obj);
        } catch (EncoderException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, LabUtilErrorMessageConst.ERROR_MESSAGE_200005, obj.toString());
        }
    }

    /**
     * 将字符串以Base64编码，使用bouncy castle的实现
     *
     * @param bytes 待编码的数组
     * @return 经过Base64编码后的字符串
     */
    public static String encodeBase64WithBC(byte[] bytes) {
        return new String(org.bouncycastle.util.encoders.Base64.encode(bytes));
    }

    /**
     * 使用Base64解码字符串，默认使用Commons Codec的实现
     *
     * @param str 待解码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeBase64(String str) {
        Base64 encoder = new Base64();
        return encoder.encode(str.getBytes());
    }

    /**
     * 使用Base64解码字符串，默认使用Commons Codec的实现
     *
     * @param str 待解码的字符串
     * @return 解码后的字符串
     */
    public static String decodeBase64Str(String str) {
        Base64 encoder = new Base64();
        return new String(encoder.encode(str.getBytes()));
    }

    /**
     * 使用Base64解码字符串，使用bouncy castle的实现
     *
     * @param str 待解码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeBase64WithBC(String str) {
        return org.bouncycastle.util.encoders.Base64.decode(str);
    }

    /**
     * 使用Base64解码字符串，使用bouncy castle的实现
     *
     * @param str 待解码的字符串
     * @return 解码后的字符串
     */
    public static String decodeBase64StrWithBC(String str) {
        return new String(org.bouncycastle.util.encoders.Base64.decode(str));
    }

    /**
     * 转换文件路径，支持的方式有
     * >*classpath: dir/dir1/file 通过classpath读取文件
     * >*webroot:   dir/dir1/file 通过web工程路径读取文件
     * >*server:    dir/dir1/file 通过服务器路径读取
     * >*           c:/dir1/file  通过绝对路径读取
     *
     * @param path
     * @return
     */
    public static String formatFilePath(String path) {
        if (log.isDebugEnabled()) {
            log.debug("方法【formatFilePath】调用开始，参数[path]=" + path);
        }

        String realFilePath;
        if (StringUtil.isNotEmptyOrNull(path) && path.startsWith(FILE_PRE_CLASSPATH)) {
            ClassLoader classLoader = StringUtil.class.getClassLoader();
            String fileClassPath = path.substring(10, path.length());
            URL url = classLoader.getResource(fileClassPath.trim());
            if (url == null) {
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, LabUtilErrorMessageConst.ERROR_MESSAGE_200006, path);
            } else {
                realFilePath = url.getFile();
            }
        } else if (StringUtil.isNotEmptyOrNull(path) && path.startsWith(FILE_PRE_WEB_ROOT)) {
            String webRootPath = getWebRootPath();
            if (!webRootPath.endsWith(File.separator)) {
                webRootPath += File.separator;
            }
            realFilePath = webRootPath + path.substring(8, path.length());
        } else if (StringUtil.isNotEmptyOrNull(path) && path.startsWith(FILE_PRE_SERVER)) {
            String rootPath = ServerFile.getServiceFileFolder();
            if (!rootPath.endsWith(File.separator)) {
                rootPath += File.separator;
            }
            realFilePath = rootPath + path.substring(7, path.length());
        } else {
            realFilePath = path;
        }

        realFilePath = realFilePath.replaceAll("\\\\", FILE_SEPARATOR_REG);
        realFilePath = realFilePath.replaceAll("/", FILE_SEPARATOR_REG);
        try {
            realFilePath = URLDecoder.decode(realFilePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("路径编码错误", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("方法【formatFilePath】调用结束，参数[path]=" + path);
        }
        return realFilePath;
    }

    /**
     * 字符替换，使用commons-lang的StringUtils
     *
     * @param text         输入字符串
     * @param searchString 需要替换的字符串
     * @param replacement  替换字符
     * @return 替换后的字符串
     */
    public static String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement);
    }

    /**
     * 获取commons-lang的StringUtils对象
     *
     * @return StringUtils对象
     */
    public static StringUtils getStringUtils() {
        return new StringUtils();
    }

    /**
     * 汉字转拼音缩写(首字母大写)
     *
     * @param str 需要转换的字符串
     * @return 拼音缩写
     */
    public static String getPYStr(String str) {
        String result = null;
        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            String pinYin;
            if ((int) c >= 33 && (int) c <= 126) {
                pinYin = String.valueOf(c);
            } else {
                pinYin = PinyinHelper.toHanyuPinyinStringArray(c)[0];
            }
            if (StringUtil.isNotEmptyOrNull(pinYin)) {
                result += pinYin.substring(0, 1).toUpperCase();
            }
        }
        return result;
    }

    /**
     * 对象转换为字符串，默认值为空串""
     *
     * @param obj 需要被转换的对象
     * @return 如果为null则返回""，否则返回valueOf值
     */
    public static String obj2Str(Object obj) {
        return obj2Str(obj, "");
    }

    /**
     * 对象转换为字符串
     *
     * @param obj          需要被转换的对象
     * @param defaultValue 缺省值
     * @return 如果为null则返回缺省值，否则返回valueOf值
     */
    public static String obj2Str(Object obj, String defaultValue) {
        String value = defaultValue;
        if (obj != null) {
            value = String.valueOf(obj);
        }
        return value;

    }

}
