package com.javalearning.minirpc.codec;

import com.javalearning.minirpc.codec.impl.JSONDecoder;
import com.javalearning.minirpc.codec.impl.JSONEncoder;
import junit.framework.TestCase;

public class JSONDecoderTest extends TestCase {

    public void testDecoder() {
        JSONEncoder encoder = new JSONEncoder();
        JSONDecoder decoder = new JSONDecoder();
        TestClass testClass = new TestClass();
        testClass.setName("miniRPC");
        testClass.setAge(18);
        byte[] bytes = encoder.encode(testClass);
        TestClass decoderClass = decoder.decoder(bytes, TestClass.class);
        assertEquals(testClass.getName(), decoderClass.getName());
        assertEquals(testClass.getAge(), decoderClass.getAge());
    }
}