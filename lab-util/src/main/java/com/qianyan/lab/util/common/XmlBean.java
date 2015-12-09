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

    public XmlNode getNode() {
        return node;
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

    /**
     * 创建一个XmlBean对象
     *
     * @param key  节点名称
     * @param node 节点值
     */
    private XmlBean(String key, XmlNode node) {
        this.node = new XmlNode();
        Map<String, Object> map = this.node.getSubNode();
        map.put(key, node);
    }

    /**
     * 获取指定路径下的子节点名称列表，为空返回根节点
     *
     * @param nodePath 节点路径
     * @return 节点名称列表
     */
    public List<String> getNodeNames(String nodePath) {
        List<String> result = new LinkedList<String>();
        if (nodePath == null || "".equals(nodePath)) {
            result.addAll(this.node.keySet());
        } else {
            XmlBean nodeBean = getBeanByPath(nodePath);
            if (nodeBean != null) {
                Object nodeValue = nodeBean.getNode().getNodeValue();
                if (nodeValue instanceof XmlNode) {
                    result.addAll(((XmlNode) nodeValue).keySet());
                } else if (nodeValue instanceof List) {
                    List node_value = (List) nodeValue;
                    for (Object temp : node_value) {
                        if (nodeValue instanceof XmlNode) {
                            result.addAll(((XmlNode) nodeValue).keySet());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 在根节点下添加一个xmlBean
     *
     * @param xmlBean 子结点
     * @return 合并之后的xmlBean
     */
    public XmlBean append(XmlBean xmlBean) {
        LinkedHashMap<String, Object> bean = this.node.subNode;
        if (bean.isEmpty()) {
            this.node = xmlBean.getNode();
            return this;
        }
        for (Map.Entry<String, Object> entry : bean.entrySet()) {
            this.setBeanByPath(entry.getKey(), xmlBean);
        }
        return this;
    }


    /**
     * 根据抽象路径设置XmlBean
     *
     * @param path    抽象路径
     * @param xmlBean xmlBean
     */
    @SuppressWarnings("unchecked")
    public void setBeanByPath(String path, XmlBean xmlBean) {
        String[] keys = path.split("\\.");
        int depth = keys.length;

        Map allSubNode = this.node.getSubNode();
        XmlNode xmlNode;

        for (int i = 0; i < depth - 1; i++) {
            int pos = keys[i].indexOf("[");
            if (pos != -1) {
                String key = keys[i].substring(0, pos);
                int r_pos = Integer.parseInt(keys[i].substring(pos + 1, keys[i].length() - 1));
                Object obj = allSubNode.get(key);
                if (obj == null) {
                    obj = new ArrayList<XmlNode>();
                    allSubNode.put(key, obj);
                }

                if (r_pos + 1 > ((List) obj).size()) {
                    xmlNode = new XmlNode();
                    allSubNode = xmlNode.getSubNode();
                    ((List) obj).add(xmlNode);
                } else {
                    if (((List) obj).get(r_pos) == null) {
                        xmlNode = new XmlNode();
                        allSubNode = xmlNode.getSubNode();
                        ((List) obj).add(r_pos, xmlNode);
                    } else {
                        allSubNode = ((XmlNode) ((List) obj).get(r_pos)).getSubNode();
                        log.debug("node already exists!");
                    }
                }
            } else {
                if (allSubNode.get(keys[i]) == null) {
                    allSubNode.put(keys[i], new XmlNode());
                }
                allSubNode = ((XmlNode) allSubNode.get(keys[i])).getSubNode();
            }
        }

        int pos = keys[depth - 1].indexOf("[");
        XmlNode setNode = xmlBean.getNode();

        if (pos != -1) {
            String key = keys[depth - 1].substring(0, pos);
            int r_pos = Integer.parseInt(keys[depth - 1].substring(pos + 1, keys[depth - 1].length() - 1));
            Object obj = allSubNode.get(key);

            if (obj instanceof XmlNode) {
                Map<String, Object> targetMap = ((XmlNode) obj).subNode;
                Map<String, Object> setMap = setNode.subNode;
                this.mergeNode(targetMap, setMap);

                List<XmlNode> transList = new ArrayList<XmlNode>();
                transList.add((XmlNode) obj);
                allSubNode.put(key, transList);
            } else {
                if (obj == null) {
                    obj = new ArrayList<XmlNode>();
                    allSubNode.put(key, obj);
                    ((List) obj).add(setNode);
                } else {
                    if (r_pos + 1 > ((List) obj).size()) {
                        ((List) obj).add(setNode);
                    } else {
                        Map<String, Object> targetMap = ((XmlNode) (((List) obj).get(r_pos))).getSubNode();
                        Map<String, Object> setMap = setNode.subNode;
                        this.mergeNode(targetMap, setMap);
                    }
                }
            }
        } else {
            if (allSubNode.get(keys[depth - 1]) == null) {
                allSubNode.put(keys[depth - 1], setNode);
            } else {
                Map<String, Object> targetMap = ((XmlNode) allSubNode.get(keys[depth - 1])).getSubNode();
                Map<String, Object> setMap = setNode.subNode;
                this.mergeNode(targetMap, setMap);
            }
        }
    }

    /**
     * 将setMap中的key值 合并到 targetMap中
     *
     * @param targetMap 合并到的Map
     * @param setMap    被合并的Map
     */
    @SuppressWarnings("unchecked")
    private void mergeNode(Map<String, Object> targetMap, Map<String, Object> setMap) {
        Iterator iterator = setMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, XmlNode> entry = (Map.Entry<String, XmlNode>) iterator.next();

            if (!targetMap.containsKey(entry.getKey())) {
                targetMap.put(entry.getKey(), entry.getValue());
            } else {
                Object targetObj = targetMap.get(entry.getKey());
                if (targetObj instanceof List) {
                    ((List) targetObj).add(entry.getValue());
                } else {
                    List<XmlNode> targetList = new ArrayList<XmlNode>();
                    targetList.add((XmlNode) targetObj);
                    targetList.add(entry.getValue());

                    targetMap.put(entry.getKey(), targetList);
                }
            }
        }
    }

    public XmlBean getBeanByPath(String path) {
        if (path == null || "".equals(path)) {
            return null;
        }

        XmlNode xmlNode = this.getNode(path);

        if (xmlNode == null) {
            return null;
        }
        String[] keys = path.split("\\.");
        int depth = keys.length;
        int pos = keys[depth - 1].indexOf("[");
        String key;
        if (pos != -1) {
            key = keys[depth - 1].substring(0, pos);
        } else {
            key = keys[depth - 1];
        }

        return new XmlBean(key, xmlNode);
    }

    public XmlNode getNode(String path) {
        String[] keys = path.split("\\.");
        int depth = keys.length;
        Map allSubNode = this.node.getSubNode();
        for (int i = 0; i < depth - 1; ++i) {
            int pos = keys[i].indexOf("[");
            if (pos != -1) {
                String key = keys[i].substring(0, pos);
                if (!allSubNode.containsKey(key)) {
                    return null;
                }

                int r_pos = Integer.parseInt(keys[i].substring(pos + 1, keys[i].length() - 1));
                Object obj = allSubNode.get(key);
                if (obj instanceof List) {
                    if (r_pos >= ((List) allSubNode.get(key)).size()) {
                        return null;
                    }
                    allSubNode = ((XmlNode) ((List) allSubNode.get(key)).get(r_pos)).getSubNode();
                } else {
                    allSubNode = ((XmlNode) allSubNode.get(key)).getSubNode();
                }
            } else {
                if (!allSubNode.containsKey(keys[i])) {
                    return null;
                }
                Object nodeValue = allSubNode.get(keys[i]);
                if (nodeValue instanceof List) {
                    log.warn(keys[i] + "is a multiple,get value without index will return first item");
                    List temp = (List) nodeValue;
                    if (temp.size() == 0) {
                        return null;
                    } else {
                        nodeValue = temp.get(0);
                    }
                } else if (!(nodeValue instanceof XmlNode)) {
                    return null;
                }
                allSubNode = ((XmlNode) nodeValue).getSubNode();
            }
        }

        Object value;
        XmlNode returnNode;
        int pos = keys[depth - 1].indexOf("[");
        if (pos != -1) {
            String key = keys[depth - 1].substring(0, pos);
            if (!allSubNode.containsKey(key)) {
                return null;
            }
            int r_pos = Integer.parseInt(keys[depth - 1].substring(pos + 1, keys[depth - 1].length() - 1));
            value = allSubNode.get(key);
            if (value instanceof List) {
                returnNode = (XmlNode) ((List) value).get(pos);
            } else {
                returnNode = (XmlNode) value;
            }
        } else {
            if (!allSubNode.containsKey(keys[depth - 1])) {
                return null;
            }
            returnNode = (XmlNode) allSubNode.get(keys[depth - 1]);
        }
        return returnNode;
    }

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
