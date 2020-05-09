package com.javalearning.minirpc.codec;

import com.javalearning.minirpc.codec.impl.JSONEncoder;
import junit.framework.TestCase;

public class JSONEncoderTest extends TestCase {

    public void testEncoder() {
        JSONEncoder encoder = new JSONEncoder();
        TestClass testClass = new TestClass();
        testClass.setName("miniRPC");
        testClass.setAge(18);
        byte[] bytes = encoder.encode(testClass);
        assertNotNull(bytes);
    }
}