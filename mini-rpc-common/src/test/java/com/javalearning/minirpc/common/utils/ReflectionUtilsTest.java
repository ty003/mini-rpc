package com.javalearning.minirpc.common.utils;

import junit.framework.TestCase;

import java.lang.reflect.Method;

public class ReflectionUtilsTest extends TestCase {

    public void testNewInstance() {
        TestClass tClass = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(tClass);
    }

    public void testGetPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, methods.length);
        assertEquals("b", methods[0].getName());
    }

    public void testInvoke() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method b = methods[0];
        TestClass tClass = ReflectionUtils.newInstance(TestClass.class);
        Object result = ReflectionUtils.invoke(tClass, b);
        assertEquals("b", result);
    }
}