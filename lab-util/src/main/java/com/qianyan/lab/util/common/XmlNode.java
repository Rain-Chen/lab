package com.qianyan.lab.util.common;

import java.io.Serializable;
import java.util.*;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 15-11-28
 * Time: 下午4:14.
 */
public class XmlNode implements Map<String, Object>, Serializable {

    private static final long serialVersionUID = 4551241900644141664L;

    /**
     * 子节点信息
     */
    public LinkedHashMap<String, Object> subNode;

    /**
     * 当前节点属性信息
     */
    public LinkedHashMap<String, String> nodeAttr;

    /**
     * 当前节点文本信息
     */
    private Object nodeValue;

    @SuppressWarnings("unchecked")
    public void merge(XmlNode inXmlNode) {
        LinkedHashMap<String, Object> mergerMap = inXmlNode.getSubNode();
        for (Map.Entry<String, Object> entry : mergerMap.entrySet()) {
            //当前值
            Object currentValue = subNode.get(entry.getKey());
            Object mergerValue = entry.getValue();
            if (mergerValue != null) {
                if (currentValue == null) {
                    subNode.put(entry.getKey(), mergerValue);
                } else if (currentValue instanceof XmlNode) {
                    if (mergerValue instanceof XmlNode) {
                        ((XmlNode) currentValue).merge((XmlNode) mergerValue);
                    } else {
                        List<XmlNode> temp = (List<XmlNode>) currentValue;
                        temp.add(0, (XmlNode) currentValue);
                        subNode.put(entry.getKey(), temp);
                    }
                } else if (currentValue instanceof List) {
                    List<XmlNode> temp = (List<XmlNode>) currentValue;
                    if (mergerValue instanceof List) {
                        temp.addAll((List) mergerValue);
                    } else {
                        temp.add((XmlNode) mergerValue);
                    }
                }
            }
        }
    }

    protected XmlNode() {
        this.subNode = new LinkedHashMap<String, Object>();
    }

    public LinkedHashMap<String, Object> getSubNode() {
        return subNode;
    }

    public void setSubNode(LinkedHashMap<String, Object> subNode) {
        this.subNode = subNode;
    }

    public LinkedHashMap<String, String> getNodeAttr() {
        return nodeAttr;
    }

    public void setNodeAttr(LinkedHashMap<String, String> nodeAttr) {
        this.nodeAttr = nodeAttr;
    }

    public Object getNodeValue() {
        if (nodeValue == null && getSubNode().size() > 0) {
            return this.getSubNode().values().iterator().next();
        } else {
            return nodeValue;
        }
    }

    public void setNodeValue(Object nodeValue) {
        this.nodeValue = nodeValue;
    }

    @Override
    public int size() {
        return this.getSubNode().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getSubNode().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getSubNode().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.getSubNode().containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.getSubNode().get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.getSubNode().put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.getSubNode().remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.getSubNode().putAll(m);
    }

    @Override
    public void clear() {
        this.getSubNode().clear();
    }

    @Override
    public Set<String> keySet() {
        return this.getSubNode().keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.getSubNode().values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.getSubNode().entrySet();
    }
}
