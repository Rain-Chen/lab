package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-30
 * Time: 下午2:37.
 */
public class JaxbUtil {

    private static final Log log = LogFactory.getLog(JaxbUtil.class);

    //单利
    private JaxbUtil() {
    }

    /**
     * 从类转换为xml字符串
     *
     * @param bean javaBean对象
     * @return xml报文字符串
     */
    public static String marshal(Object bean) {
        Writer writer = null;
        String packageName = bean.getClass().getPackage().getName();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            writer = new StringWriter();
            marshaller.marshal(bean, writer);
        } catch (JAXBException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200008, "JAXB");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.warn("XML转换文件流关闭失败!", e);
            }
        }
        return writer.toString();
    }

    /**
     * 从xml报文字符串解码为类实例
     *
     * @param clazz javaBean对象字节码
     * @param xml   转换为对象的xml报文
     * @param <T>   javaBean对象类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(Class<T> clazz, String xml) {
        T t = null;
        try {
            String packageName = clazz.getPackage().getName();
            JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            InputStream inputStream = stringToInputStream(xml);
            t = (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200009, xml);
        }
        return t;
    }

    /**
     * @param clazz       javaBean对象字节码
     * @param inputStream 转换为对象的输入流
     * @param <T>         javaBean对象类型
     * @return javaBean对象类型的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(Class<T> clazz, InputStream inputStream) {
        T t = null;
        try {
            String packageName = clazz.getPackage().getName();
            JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200009, "JAXB");
        }
        return t;
    }

    /**
     * xml报文转换为输入流
     *
     * @param str xml报文字符串
     * @return 输入流
     */
    private static InputStream stringToInputStream(String str) {
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200010, "报文", "utf-8");
        }
        return stream;
    }
}
