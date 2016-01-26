package com.qianyan.lab.util.common;

import junit.framework.Assert;
import org.junit.Test;


public class BigDecimalUtilTest {

    @Test
    public void testAdd() throws Exception {
        Assert.assertEquals(10.01, BigDecimalUtil.add(10.00d, 0.01d));
        Assert.assertEquals(1.11111111d, BigDecimalUtil.add(0.111111111d, 0.999999999d));
        Assert.assertEquals(0.888888888d, BigDecimalUtil.add(-0.111111111d, 0.999999999d));
    }

    @Test
    public void testAdd1() throws Exception {
        Assert.assertEquals(22.22, BigDecimalUtil.add(20.00d, 2.22d));
        Assert.assertEquals(11.11d, BigDecimalUtil.add(5.00d, 5.00d, 1, 0.11d, 0.000000d));
        Assert.assertEquals(-1.00d, BigDecimalUtil.add());
        Assert.assertEquals(20.0, BigDecimalUtil.add(20.0d));
    }

    @Test
    public void testSub() throws Exception {
        Assert.assertEquals(0.888888888d, BigDecimalUtil.sub(0.999999999d, 0.111111111d));
        Assert.assertEquals(11.89, BigDecimalUtil.sub(12d, 0.11d));
    }

    @Test
    public void testMul() throws Exception {
        Assert.assertEquals(0.11111111088888889d, BigDecimalUtil.mul(0.999999999d, 0.111111111d));
        Assert.assertEquals(144d, BigDecimalUtil.mul(12, 12));
    }

    @Test
    public void testMul1() throws Exception {
        Assert.assertEquals(100d, BigDecimalUtil.mul(10, 10, 1, 1));
        Assert.assertEquals(100d, BigDecimalUtil.mul(10, 10));
    }

    @Test
    public void testDiv() throws Exception {
        Assert.assertEquals(24d, BigDecimalUtil.div(24, 1));
        Assert.assertEquals(0.25d, BigDecimalUtil.div(1, 4));
    }

    @Test
    public void testDiv1() throws Exception {
        Assert.assertEquals(2d, BigDecimalUtil.div(24, 12, 3));
        Assert.assertEquals(2.75d, BigDecimalUtil.div(33, 12, 3));
        Assert.assertEquals(3.333d, BigDecimalUtil.div(10, 3, 3));
        //BigDecimalUtil没有处理除0异常
        //System.out.println(BigDecimalUtil.div(10, 0));
    }

    @Test
    public void testRound() throws Exception {
        Assert.assertEquals(123.123d, BigDecimalUtil.round(123.123d, 4));
        Assert.assertEquals(123d, BigDecimalUtil.round(123.123d, 0));
        Assert.assertEquals(124d, BigDecimalUtil.round(123.523d, 0));
        Assert.assertEquals(123.12d, BigDecimalUtil.round(123.123d, 2));
        Assert.assertEquals(123.13d, BigDecimalUtil.round(123.126d, 2));
    }

    @Test
    public void testFormatMoney() throws Exception {
        System.out.println(BigDecimalUtil.formatMoney(254.36d, 1));
        System.out.println(BigDecimalUtil.formatMoney(2545544.3d, 4));
    }

    @Test
    public void testToFloat() throws Exception {
        Assert.assertEquals(1.87654321f, BigDecimalUtil.toFloat(1.87654321d));
    }

    @Test
    public void testToInt() throws Exception {
        Assert.assertEquals(1, BigDecimalUtil.toInt(1.87654321d));
        System.out.println(BigDecimalUtil.toInt(987654321d));
    }

    @Test
    public void testToLong() throws Exception {
        Assert.assertEquals(1, BigDecimalUtil.toLong(1.87654321d));
    }

    @Test
    public void testReturnMax() throws Exception {
        Assert.assertEquals(7.65421d, BigDecimalUtil.returnMax(7.65421d, 1.87654321d));
        Assert.assertEquals(10d, BigDecimalUtil.returnMax(10, 10));
    }

    @Test
    public void testReturnMin() throws Exception {
        Assert.assertEquals(1.87654321d, BigDecimalUtil.returnMin(3.65421d, 1.87654321d));
    }

    @Test
    public void testCompareTo() throws Exception {
        Assert.assertEquals(1, BigDecimalUtil.compareTo(3.65421d, 1.87654321d));
        Assert.assertEquals(-1, BigDecimalUtil.compareTo(1.87654321d, 3.65421d));
        Assert.assertEquals(0, BigDecimalUtil.compareTo(3.65421d, 3.65421d));
    }
}