package com.qianyan.lab.util.common;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class XmlBeanTest {

    @Test
    public void testGetRootNode() throws Exception {

    }

    @Test
    public void testXml() {
        XmlNode xmlNode = new XmlNode();
        XmlNode subNode = new XmlNode();
        XmlNode endNode = new XmlNode();

        endNode.setNodeValue("123");
        subNode.put("Test", endNode);
        xmlNode.put("Root", subNode);
        XmlBean xmlBean = new XmlBean(xmlNode);
        Assert.assertEquals(xmlBean.getStrValue("Root.Test"), "123");
    }

    @Test
    public void testXml_1() {
        XmlNode subNode = new XmlNode();
        XmlNode endNode = new XmlNode();
        endNode.setNodeValue("123");
        subNode.put("Test", endNode);

        XmlBean xmlBean = new XmlBean(subNode);
        Assert.assertEquals(xmlBean.getStrValue("Test"), "123");
    }

    @Test
    public void testFromXML() throws Exception {
        String xml = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        System.out.println(xmlBean);
        Assert.assertEquals(xmlBean.toString(), "<Hello><word>你好</word></Hello>");
    }

    @Test
    public void testGetNodeNames() {
        String xml = "<Hello><word>你好</word><test>测试节点</test></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        List<String> nodeNames = xmlBean.getNodeNames("");
        System.out.println(Arrays.toString(nodeNames.toArray()));
    }

    @Test
    public void testAppend() {
        String xml1 = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean1 = new XmlBean();
        xmlBean1.fromXML(xml1);
        String xml2 = "<First><second>世界</second></First>";
        XmlBean xmlBean2 = new XmlBean();
        xmlBean2.fromXML(xml2);
        xmlBean1.append(xmlBean2);
        System.out.println(xmlBean1);
    }

    @Test
    public void testGetAttr() {
        String xml = "<Hello><world id='123'>你好</world></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);
        Assert.assertEquals("{id=123}", xmlBean.getAttr("Hello.world").toString());
    }

    @Test
    public void testToString() {
        String xml = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        String toStr = xmlBean.toString();
        Assert.assertEquals(toStr, "<Hello><word>你好</word></Hello>");
    }

    @Test
    public void testToJson() {
        String xml = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        String jsonStr = xmlBean.toJson();
        System.out.println(jsonStr);
        Assert.assertEquals(jsonStr, "{\"Hello\":{\"word\":\"你好\"}}");
    }

    @Test
    public void testToJson_1() {
        XmlBean xmlBean = new XmlBean("<Hello><Test></Test><treeNode><name>节点1</name><code>Node1</code></treeNode><treeNode><name>节点2</name><code>Node2</code></treeNode></Hello>");
        System.out.println(xmlBean.toJson());
        Assert.assertEquals(xmlBean.toJson(), "{\"Hello\":{\"Test\":\"\",\"treeNode\":[{\"name\":\"节点1\",\"code\":\"Node1\"},{\"name\":\"节点2\",\"code\":\"Node2\"}]}}");
    }


    @Test
    public void testHasChildren() {
        String xml = "<Hello><Test></Test><treeNode><name>节点1</name><code>Node1</code></treeNode><treeNode><name>节点2</name><code>Node2</code></treeNode></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        System.out.println(xmlBean.hasChildren("treeNode"));    //true
        System.out.println(xmlBean.hasChildren("name"));        //false
    }

    @Test
    public void testToXml() {
        String xml = "<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        Assert.assertEquals("<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>", xmlBean.toXML());
    }

    @Test
    public void testRemoveNode() {
        String xml = "<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);
        xmlBean.removeNode("Hello.Test");

        Assert.assertEquals("<Hello><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>", xmlBean.toXML());
    }

    @Test
    public void testMutliField() {
        String xml = "<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        Assert.assertEquals("Node1", xmlBean.getStrValue("Hello.treeNode.name"));
        Assert.assertEquals("Node1", xmlBean.getStrValue("Hello.treeNode[0].name"));
        Assert.assertEquals("Node2", xmlBean.getStrValue("Hello.treeNode[1].name"));
    }

    @Test
    public void testXml_2() {
        XmlBean xmlBean = new XmlBean("<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>");

        Assert.assertEquals("<Hello><Test/><treeNode><name>Node1</name><code>code1</code></treeNode><treeNode><name>Node2</name><code>code2</code></treeNode></Hello>", xmlBean.toXML());
    }
}