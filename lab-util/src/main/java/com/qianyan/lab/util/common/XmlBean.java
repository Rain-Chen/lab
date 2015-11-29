package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午3:41.
 * 报文工具类，通用数据交换格式
 */
public class XmlBean implements Serializable {

    private static final long serialVersionUID = 6475807186759135764L;
    private final transient Log log = LogFactory.getLog(this.getClass());

    /**
     * 当前节点信息
     */
    private XmlNode node;

    public XmlBean() {
        this.node = new XmlNode();
    }

    public XmlBean(XmlNode xmlNode) {
        if (xmlNode == null) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, LabUtilErrorMessageConst.ERROR_MESSAGE_200001);
        }
        if (xmlNode.size() != 1) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, LabUtilErrorMessageConst.ERROR_MESSAGE_200002);
        }
        this.node = xmlNode;
    }

    public XmlNode getRootNode() {
        return this.node;
    }

    /**
     * 从xml字符串构造XmlBean对象
     *
     * @param xmlStr xml字符串
     */
    public XmlBean(String xmlStr) {
        this();
        fromXML(xmlStr);
    }

    /*
    public List<String> getNodeNames(String nodePath) {
        List<String> result = new LinkedList<String>();
        if (nodePath == null || "".equals(nodePath)) {
            result.addAll(this.node.keySet());
        }else {
            XmlBean
        }
    }
    */

    /**
     * xml报文转XmlBean
     *
     * @param xml 报文字符串
     */
    public void fromXML(String xml) {
        try {
            Map subMap = this.node.getSubNode();
            Document doc = DocumentHelper.parseText(xml.trim());
            Element rootElement = doc.getRootElement();
            String rootName = rootElement.getName();

            Map<String, String> rootAttr = this.getElementAttr(rootElement);

            XmlNode rootNode = null;
            if (rootElement.isTextOnly()) {
                rootNode = new XmlNode();
                rootNode.setNodeValue(rootElement.getTextTrim());
                rootNode.setNodeAttr((LinkedHashMap<String, String>) rootAttr);
                this.putValue(subMap, rootName, rootNode);
            } else {
                rootNode = new XmlNode();
                rootNode.setNodeAttr((LinkedHashMap<String, String>) rootAttr);
                this.putValue(subMap, rootName, rootNode);
                subMap = ((XmlNode) subMap.get(rootName)).getSubNode();
                this.traverseDOM(rootElement, subMap);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void traverseDOM(Element element, Map subMap) {
        for (Iterator iterator = element.elementIterator(); iterator.hasNext(); ) {
            Element elem = (Element) iterator.next();
            String elemName = elem.getName();
            Map<String, String> attrMap = this.getElementAttr(elem);
            if (elem.isTextOnly()) {
                XmlNode emptyNode = new XmlNode();
                emptyNode.setNodeValue(elem.getStringValue().trim());
                emptyNode.setNodeAttr((LinkedHashMap<String, String>) attrMap);
                this.putValue(subMap, elemName, emptyNode);
            } else {
                XmlNode subNode = new XmlNode();
                subNode.setNodeAttr((LinkedHashMap<String, String>) attrMap);
                this.putValue(subMap, elemName, subNode);
                Map ssMap;
                if (subMap.get(elemName) instanceof List) {
                    List ssList = (List) subMap.get(elemName);
                    ssMap = ((XmlNode) ssList.get(ssList.size() - 1)).getSubNode();
                } else {
                    ssMap = ((XmlNode) subMap.get(elemName)).getSubNode();
                }
                traverseDOM(elem, ssMap);
            }
        }
    }

    /**
     * 为XmlBean节点设置值
     *
     * @param subMap 子节点
     * @param name   属性名称
     * @param node   XmlNode
     */
    @SuppressWarnings("unchecked")
    private void putValue(Map subMap, String name, XmlNode node) {
        Object obj = subMap.get(name);
        if (obj == null) {
            subMap.put(name, node);
        } else if (obj instanceof List) {
            ((List) obj).add(node);
        } else {
            List list = new ArrayList();
            list.add(obj);
            list.add(node);
            subMap.put(name, list);
        }
    }

    /**
     * 获取节点元素的属性
     *
     * @param element 节点
     * @return Map格式的节点的属性名和属性值
     */
    private Map<String, String> getElementAttr(Element element) {
        Map<String, String> attrMap = new LinkedHashMap<String, String>();
        for (Iterator iterator = element.attributeIterator(); iterator.hasNext(); ) {
            Attribute attribute = (Attribute) iterator.next();
            attrMap.put(attribute.getName(), attribute.getValue());
        }
        return attrMap;
    }

}
