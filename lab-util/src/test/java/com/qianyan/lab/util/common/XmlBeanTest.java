package com.qianyan.lab.util.common;

import org.junit.Test;

public class XmlBeanTest {

    @Test
    public void testGetRootNode() throws Exception {

    }

    @Test
    public void testFromXML() throws Exception {
        String xml = "<Hello><word>你好</word></Hello>";
        XmlBean xmlBean = new XmlBean();
        xmlBean.fromXML(xml);

        System.out.println();
    }
}