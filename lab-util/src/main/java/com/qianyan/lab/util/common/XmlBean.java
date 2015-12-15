package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static final int MB_SET_ALL = 0;
    private static final int MB_SET_VALUE = 1;
    private static final int MB_SET_ATTR = 2;
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
     * 根据json数据格式以rootPath作为根路径生成报文结构
     *
     * @param json     json数据
     * @param rootPath 需要构造的XmlBean根路径
     * @return XmlBean对象
     */
    public static XmlBean jsonStr2Xml(String json, String rootPath) {
        XmlBean jsonXml = new XmlBean("<" + rootPath + ">");
        Map<String, Object> jsonMap = JSONObject.fromObject(json);
        jsonXml.append(addMap2Xml(jsonMap, false, "", 0));
        return jsonXml;
    }

    private static XmlBean addMap2Xml(Map<String, Object> jsonMap, boolean _more, String str, int index) {
        XmlBean jsonXml = new XmlBean();
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            if (value instanceof JSONObject) {
                jsonXml.setValue(key, "");
                jsonXml.setBeanByPath(key, addMap2Xml((Map<String, Object>) value, _more, str, index));
            } else if (value instanceof String) {
                if (_more) {
                    key = str + "." + key;
                }
                jsonXml.setValue(key, value);
            } else if (value instanceof JSONArray) {
                List<Object> jsonObjects = (List<Object>) value;
                int num = jsonObjects.size();
                for (int i = 0; i < num; i++) {
                    Object jsonObject = jsonObjects.get(i);
                    if (jsonObject instanceof String) {
                        jsonXml.setValue(_more ? str + "." + key + "." + key.substring(0, key.length() - 1) + "[" + i + "]" : key + "." + key.substring(0, key.length() - 1) + "[" + i + "]", jsonObject);
                    } else if (jsonObject instanceof JSONObject) {
                        jsonXml.setBeanByPath(_more ? str + "." + key : key, addMap2Xml((Map<String, Object>) jsonObject, true, key.substring(0, key.length() - 1), i));
                    }
                }
            }
        }
        return jsonXml;
    }

    public XmlNode getRootNode() {
        return this.node;
    }

    public XmlNode getNode() {
        return node;
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
     * 在根节点下增加一个XmlBean
     *
     * @param xmlBean 子节点
     * @return merge后的XmlBean
     */
    @SuppressWarnings("unchecked")
    public XmlBean merge(XmlBean xmlBean) {
        LinkedHashMap<String, Object> currentNodes = this.node.subNode;
        if (currentNodes.isEmpty()) {
            this.node = xmlBean.getNode();
            return this;
        }
        LinkedHashMap<String, Object> mergeMap = xmlBean.node.subNode;
        if (mergeMap.isEmpty()) {
            return this;
        }

        String currentRootNodeName = this.getNodeNames("").get(0);
        String mergeRootNodeName = xmlBean.getNodeNames("").get(0);

        Object currentRootNodeValue = this.node.get(currentRootNodeName);
        Object mergeRootNodeValue = xmlBean.node.subNode.get(mergeRootNodeName);
        if (mergeRootNodeValue != null) {
            if (currentRootNodeValue == null) {
                this.node.put(currentRootNodeName, null);
            } else if (currentRootNodeValue instanceof XmlNode && mergeRootNodeValue instanceof XmlNode) {
                ((XmlNode) currentRootNodeValue).merge((XmlNode) mergeRootNodeValue);
            } else if (currentRootNodeValue instanceof List) {
                if (mergeRootNodeValue instanceof XmlNode) {
                    ((List) currentRootNodeValue).add(mergeRootNodeValue);
                } else {
                    ((List) currentRootNodeValue).addAll((List) mergeRootNodeValue);
                }
            } else {
                if (mergeRootNodeValue instanceof XmlNode) {
                    this.node.put(currentRootNodeName, mergeRootNodeValue);
                } else {
                    ((List) mergeRootNodeValue).add(currentRootNodeName);
                }
            }
        }
        return this;
    }

    /**
     * 判断有没有包含key的子结点
     *
     * @param key 子结点
     * @return if has return true else false
     */
    public boolean hasChildren(String key) {
        LinkedHashMap<String, Object> subNode = this.node.subNode;
        for (Map.Entry<String, Object> entry : subNode.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof XmlNode) {
                XmlNode children = (XmlNode) value;
                Map map = children.subNode;
                if (map.containsKey(key)) {
                    return true;
                }
            }
            if (value instanceof List) {
                List list = (List) value;
                if (list.size() == 0) {
                    return false;
                } else {
                    for (Object aList : list) {
                        if (aList instanceof XmlNode) {
                            XmlNode xmlNode = (XmlNode) aList;
                            Map map = xmlNode.subNode;
                            if (map.containsKey(key)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置属性和值
     *
     * @param key   抽象路径
     * @param value 节点值
     * @param attrs 节点属性
     */
    public void setValueAndAttr(String key, Object value, String... attrs) {
        this.setValueAndAttr(MB_SET_ALL, key, value, attrs);
    }

    /**
     * 设置属性和值
     *
     * @param mode  模式:0设置属性和值，1:设置值，2:设置属性
     * @param key   抽象路径
     * @param value 节点值
     * @param attrs 节点属性
     */
    @SuppressWarnings("unchecked")
    private void setValueAndAttr(int mode, String key, Object value, String... attrs) {
        if (value == null) {
            value = "";
        }
        String[] keys = key.split("\\.");
        int depth = keys.length;
        Map allSubNode = this.node.getSubNode();
        XmlNode xmlNode;
        for (int i = 0; i < depth - 1; i++) {
            int pos = keys[i].indexOf("[");
            if (pos != -1) {
                String r_key = keys[i].substring(pos + 1, keys[i].length() - 1);
                int r_pos = Integer.parseInt(keys[i].substring(pos + 1, keys[i].length() - 1));
                Object nodeValue = allSubNode.get(r_key);
                if (nodeValue == null) {
                    nodeValue = new ArrayList<XmlNode>();
                    allSubNode.put(r_key, nodeValue);
                }
                if (r_pos + 1 > ((List) nodeValue).size()) {
                    xmlNode = new XmlNode();
                    allSubNode = xmlNode.getSubNode();
                    ((List) nodeValue).add(xmlNode);
                } else {
                    if (((List) nodeValue).get(r_pos) == null) {
                        xmlNode = new XmlNode();
                        allSubNode = xmlNode.getSubNode();
                        ((List) nodeValue).add(r_pos, xmlNode);
                    } else {
                        allSubNode = ((XmlNode) ((List) nodeValue).get(r_pos)).getSubNode();
                    }
                }
            } else {
                if (allSubNode.get(keys[i]) == null) {
                    allSubNode.put(keys[i], new XmlNode());
                }
                allSubNode = ((XmlNode) allSubNode.get(keys[i])).getSubNode();
            }
        }

        LinkedHashMap<String, String> attrMap = this.attrToMap(attrs);
        int pos = keys[depth - 1].indexOf("[");
        if (pos != -1) {
            String r_key = keys[depth - 1].substring(0, pos);
            int r_pos = Integer.parseInt(keys[depth - 1].substring(pos + 1, keys[depth - 1].length() - 1));
            Object nodeValue = allSubNode.get(r_key);
            if (nodeValue == null) {
                nodeValue = new ArrayList<XmlNode>();
                allSubNode.put(r_key, nodeValue);
            }

            if (r_pos + 1 > ((List) nodeValue).size()) {
                XmlNode leafNode = new XmlNode();
                this.setValueOrAttrByMode(mode, leafNode, value, attrMap);
                ((List) nodeValue).add(leafNode);
            } else {
                XmlNode nodeList = (XmlNode) ((List) nodeValue).get(r_pos);
                if (nodeList == null) {
                    XmlNode leafNode = new XmlNode();
                    this.setValueOrAttrByMode(mode, leafNode, value, attrMap);
                    ((List) nodeValue).add(r_pos, leafNode);
                } else {
                    this.setValueOrAttrByMode(mode, nodeList, value, attrMap);
                }
            }
        } else {
            if (allSubNode.get(keys[depth - 1]) == null) {
                XmlNode leafNode = new XmlNode();
                this.setValueOrAttrByMode(mode, leafNode, value, attrMap);
                allSubNode.put(keys[depth - 1], leafNode);
            } else {
                this.setValueOrAttrByMode(mode, (XmlNode) allSubNode.get(keys[depth - 1]), value, attrMap);
            }
        }
    }

    private XmlNode setValueOrAttrByMode(int mode, XmlNode leafNode, Object value, LinkedHashMap<String, String> attrMap) {
        if (MB_SET_ALL == mode) {
            leafNode.setNodeValue(value);
            leafNode.setNodeAttr(attrMap);
        }

        if (MB_SET_VALUE == mode) {
            leafNode.setNodeValue(value);
        }

        if (MB_SET_ATTR == mode) {
            leafNode.setNodeAttr(attrMap);
        }

        return leafNode;
    }

    /**
     * 设置报文路径对应的值
     *
     * @param key   抽象路径
     * @param value 值
     */
    public void setValue(String key, Object value) {
        this.setValueAndAttr(MB_SET_VALUE, key, value);
    }

    /**
     * 设置字符型的变量
     *
     * @param key   抽象路径
     * @param value 值
     */
    public void setStrValue(String key, Object value) {
        if (value == null) {
            this.setValue(key, "");
        } else if (value instanceof Date) {
            this.setValue(key, new SimpleDateFormat("yyyyMMddHHmmss").format((Date) value));
        } else {
            this.setValue(key, String.valueOf(value));
        }
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return 值
     */
    public Object getValue(String key) {
        XmlNode xmlNode = this.getNode(key);
        if (xmlNode == null) {
            return null;
        }
        return xmlNode.getNodeValue();
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return 时间类型的值
     */
    public Date getDateValue(String key) {
        String dateStr = getStrValue(key);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME, e, LabUtilErrorMessageConst.ERROR_MESSAGE_200003, dateStr);
        }
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return 字符串型的值
     */
    public String getStrValue(String key) {
        Object obj = getValue(key);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return int类型的值，如果为空则返回0
     */
    public int getIntValue(String key) {
        return NumberUtil.getIntFromObj(getValue(key));
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return int类型的值，如果为空则返回null
     */
    public Integer getIntegerValue(String key) {
        return NumberUtil.getIntegerFromObj(getValue(key));
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return long类型的值，如果为空则返回0
     */
    public long getLongValue(String key) {
        return NumberUtil.getLongFromObj(getValue(key));
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return double类型的值，如果为空则返回0.00
     */
    public double getDoubleValue(String key) {
        return NumberUtil.getDoubleFormObj(getValue(key));
    }

    /**
     * 获取报文路径对应的值
     *
     * @param key 抽象路径
     * @return BigDecimal类型的值，如果为空则返回0
     */
    public BigDecimal getBigDecimalValue(String key) {
        String strValue = getStrValue(key);
        if ("".equals(strValue) || strValue == null) {
            strValue = "0";
        }
        return new BigDecimal(strValue);
    }

    /**
     * 设置报文路径对应属性
     *
     * @param key
     * @param attrs
     */
    public void setAttr(String key, String... attrs) {
        this.setValueAndAttr(MB_SET_ATTR, key, "", attrs);
    }

    /**
     * 获取报文路径属性
     *
     * @param key 抽象路径
     * @return Map 属性组
     */
    public Map<String, String> getAttr(String key) {
        XmlNode xmlNode = this.getNode(key);
        if (xmlNode == null) {
            return null;
        }
        return xmlNode.getNodeAttr();
    }

    /**
     * 属性值转Map
     *
     * @param attrs 属性值
     * @return 属性Map
     */
    private LinkedHashMap<String, String> attrToMap(String... attrs) {
        LinkedHashMap<String, String> attrMap = null;
        if (attrs.length != 0) {
            attrMap = new LinkedHashMap<String, String>();
            for (String attr1 : attrs) {
                String[] attr = attr1.split(":");
                if (attr.length != 2) {
                    break;
                }
                attrMap.put(attr[0], attr[1]);
            }
        }
        return attrMap;
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

    /**
     * 移除XmlBean的一个节点
     *
     * @param key 需要移除的节点
     */
    public void removeNode(String key) {
        int lastDot = key.lastIndexOf(".");
        String parentKey = key.substring(0, lastDot);
        String childKey = key.substring(lastDot + 1);
        XmlNode xmlNode = this.getNode(parentKey);
        if (xmlNode == null) {
            return;
        }
        int pos = childKey.indexOf("[");
        if (pos != -1) {
            String r_key = childKey.substring(0, pos);
            int r_pos = Integer.parseInt(childKey.substring(pos + 1, childKey.length() - 1));
            Object childNodeList = xmlNode.getSubNode().get(r_key);
            if (childNodeList instanceof List) {
                ((List) childNodeList).remove(r_pos);
            }
        } else {
            xmlNode.getSubNode().remove(childKey);
        }
    }

    /**
     * 获取路径节点个数
     *
     * @param key 抽象路径
     * @return 节点个数
     */
    public int getListNum(String key) {
        String[] keys = key.split("\\.");
        int depth = keys.length;
        Map allSubNode = this.node.getSubNode();
        for (int i = 0; i < depth - 1; ++i) {
            int pos = keys[i].indexOf("[");
            if (pos != -1) {
                String r_key = keys[i].substring(0, pos);
                if (!allSubNode.containsKey(r_key)) {
                    return 0;
                }
                int r_pos = Integer.parseInt(keys[i].substring(pos + 1, keys[i].length() - 1));
                Object obj = allSubNode.get(r_key);
                if (obj instanceof List) {
                    if (r_pos >= ((List) allSubNode.get(r_key)).size()) {
                        return 0;
                    }
                    allSubNode = ((XmlNode) ((List) allSubNode.get(r_key)).get(r_pos)).getSubNode();
                } else {
                    allSubNode = ((XmlNode) allSubNode.get(r_key)).getSubNode();
                }
            } else {
                if (!allSubNode.containsKey(keys[i])) {
                    return 0;
                }
                if (!(allSubNode.get(keys[i]) instanceof XmlNode)) {
                    return 0;
                }
                allSubNode = ((XmlNode) allSubNode.get(keys[i])).getSubNode();
            }
        }

        Object value;
        int listNum;
        int pos = keys[depth - 1].indexOf("[");
        if (pos != -1) {
            return 0;
        } else {
            if (!(allSubNode.containsKey(keys[depth - 1]))) {
                return 0;
            }
            value = allSubNode.get(keys[depth - 1]);
            if (value instanceof List) {
                listNum = ((List) value).size();
            } else {
                listNum = 1;
            }
        }
        return listNum;
    }

    /**
     * 根据VO对象，产生报文结构
     *
     * @param obj 将要转换的对象
     * @return XmlBean对象
     */
    public XmlBean getXmlBeanOfVO(Object obj) {
        XmlBean xmlBean = new XmlBean();
        if (obj != null) {
            String className = obj.getClass().getName();
            //标签名（类名）
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
            String noodPath = className + ".";
            xmlBean.setValue(noodPath, null);
            try {
                xmlBean = xmlBean.append(this.getXmlBean(obj));
            } catch (Exception e) {
                log.error("VO对象转换失败，get方法没找到或执行错误！", e);
            }
        }
        return xmlBean;
    }

    /**
     * 将VO转换为XmlBean对象，针对字段转化
     *
     * @param object 将要转换的VO
     * @return XmlBean对象
     * @throws Exception
     */
    private XmlBean getXmlBean(Object object) throws Exception {
        XmlBean xmlBean = new XmlBean();
        if (object != null) {
            Method[] methods = object.getClass().getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    String methodName = method.getName().trim();
                    if (methodName.startsWith("set")) {
                        methodName = methodName.substring(3, methodName.length());
                        String tagName = methodName.replaceAll("^[A-Z]", methodName.substring(0, 1).toLowerCase());
                        methodName = "get" + methodName;
                        Method method1 = object.getClass().getDeclaredMethod(methodName);
                        Object parm = method1.invoke(object);
                        xmlBean.setValue(tagName, parm);
                    }
                }
            }
        }
        return xmlBean;
    }

    /**
     * 根据抽象路径获取XmlBean
     *
     * @param path 抽象路径
     * @return XmlBean对象
     */
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

    /**
     * 递归解析xml
     *
     * @param element dom节点
     * @param subMap  所有xmlBean
     */
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

    /**
     * 解析XmlBean
     *
     * @param stringBuffer 解析结果
     * @param xmlNode      XmlBean节点
     */
    @SuppressWarnings("unchecked")
    private void marshallerXml(StringBuffer stringBuffer, XmlNode xmlNode) {
        Map allSubNode = xmlNode.getSubNode();
        for (Object o : allSubNode.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof XmlNode) {
                XmlNode pNode = (XmlNode) value;
                if (pNode.subNode.size() != 0) {
                    stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr())).append(">");
                    marshallerXml(stringBuffer, pNode);
                    stringBuffer.append("</").append(key).append(">");
                } else {
                    if (pNode.getNodeValue() == null || "".equals(pNode.getNodeValue())) {
                        stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr())).append("/>");
                    } else {
                        stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr())).append(">")
                                .append(formatValue(pNode.getNodeValue())).append("</").append(key)
                                .append(">");
                    }
                }
            }
            if (value instanceof List) {
                List pList = (List) value;
                for (Object listValue : pList) {
                    if (listValue instanceof XmlNode) {
                        XmlNode pNode = (XmlNode) listValue;
                        if (pNode.subNode.size() != 0) {
                            stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr())).append(">");
                            marshallerXml(stringBuffer, pNode);
                            stringBuffer.append("</").append(key).append(">");
                        } else {
                            if (pNode.getNodeValue() == null || "".equals(pNode.getNodeValue())) {
                                stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr())).append("/>");
                            } else {
                                stringBuffer.append("<").append(key).append(getAttrXML(pNode.getNodeAttr()))
                                        .append(">").append(formatValue(pNode.getNodeValue()))
                                        .append("</").append(key).append(">");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 属性拼接转换
     *
     * @param attrMap 属性map
     * @return 属性string
     */
    @SuppressWarnings("unchecked")
    private String getAttrXML(Map attrMap) {
        if (attrMap == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : attrMap.entrySet()) {
            Map.Entry<String, String> attrElement = (Map.Entry<String, String>) o;
            String attrKey = attrElement.getKey();
            String attrValue = attrElement.getValue();
            stringBuilder.append(" ").append(attrKey).append("=").append("\"").append(attrValue).append("\" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 处理Xml特殊自负
     *
     * @param obj 输入值
     * @return 转换后的xml字符串
     */
    private String formatValue(Object obj) {
        return String.valueOf(obj).replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
    }

    /**
     * XmlBean转换为Xml报文
     */
    public String toXML() {
        StringBuffer stringBuffer = new StringBuffer();
        this.marshallerXml(stringBuffer, this.node);
        return stringBuffer.toString();
    }

    /**
     * XmlBean部分内容转换为Xml报文
     *
     * @param key 抽象路径
     * @return xml报文
     */
    public String toXML(String key) {
        if ("".equals(key) || key == null) {
            return "";
        }
        XmlNode subNode = this.getNode(key);
        if ("".equals(subNode) || subNode == null) {
            return "";
        }
        String[] keys = key.split("\\.");
        int depth = keys.length;
        int pos = keys[depth - 1].indexOf("[");
        String r_key;
        if (pos != -1) {
            r_key = keys[depth - 1].substring(0, pos);
        } else {
            r_key = keys[depth - 1];
        }

        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put(r_key, subNode);
        XmlNode pNode = new XmlNode();
        pNode.setSubNode(linkedHashMap);
        StringBuffer stringBuffer = new StringBuffer();
        this.marshallerXml(stringBuffer, pNode);
        return stringBuffer.toString();
    }

    /**
     * XmlBean转换为json
     */
    @SuppressWarnings("unchecked")
    public String toJson() {
        Map allSubNode = this.node;
        StringBuilder resultJson = new StringBuilder();
        resultJson.append("{");
        int nodeSize = allSubNode.size();
        int count = 0;
        for (Object o : allSubNode.entrySet()) {
            count++;
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;
            String key = entry.getKey();
            resultJson.append(getJsonStr(key, entry.getValue(), true));
            if (nodeSize > count) {
                resultJson.append(",");
            }
        }
        resultJson.append("}");
        return resultJson.toString();
    }

    @SuppressWarnings("unchecked")
    private StringBuffer getJsonStr(String key, Object value, boolean oneFlag) {
        StringBuffer retStr = new StringBuffer();
        if (value instanceof XmlNode) {
            XmlNode node = (XmlNode) value;
            int nodeSize = node.size();
            if (nodeSize > 0) {
                if (oneFlag)
                    retStr.append("\"").append(key).append("\"").append(":");
                retStr.append("{");
                int count = 0;
                for (Map.Entry<String, Object> entry : node.entrySet()) {
                    count++;
                    retStr.append(getJsonStr(entry.getKey(), entry.getValue(), true));
                    if (count != nodeSize) {
                        retStr.append(",");
                    }
                }
                retStr.substring(0, retStr.length() - 1);
                retStr.append("}");
            } else {
                retStr.append(oneFlag ? "\"" + key + "\"" + ":" + "\"" + node.getNodeValue() + "\"" : "\"" + node.getNodeValue() + "\"");
            }
        } else if (value instanceof ArrayList) {
            ArrayList<XmlNode> valueList = (ArrayList<XmlNode>) value;
            int size = valueList.size();
            if (size == 1) {
                XmlNode node = valueList.get(0);
                StringBuffer retSb = getJsonStr(key, node, true);
                retSb.insert(retSb.indexOf(":") + 1, "[");
                retStr.append(retSb);
            } else {
                retStr.append("\"").append(key).append("\"").append(":[");
                for (int i = 0; i < size; i++) {
                    XmlNode node = valueList.get(i);
                    retStr.append(getJsonStr(key, node, !(size > 1)));
                    if (i < size - 1) {
                        retStr.append(",");
                    }
                }
            }
            retStr.append("]");
        }
        return retStr;
    }

    @Override
    public String toString() {
        return this.toXML();
    }
}
