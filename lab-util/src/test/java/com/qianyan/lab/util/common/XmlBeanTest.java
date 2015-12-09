package com.qianyan.lab.util.common;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class XmlBeanTest {

    @Test
    public void testGetRootNode() throws Exception {

    }

    @Test
    public void testFromXML() throws Exception {
        String xml = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        System.out.println(xmlBean);
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
}