package com.javalearning.minirpc.codec.impl;


import com.alibaba.fastjson.JSON;
import com.javalearning.minirpc.codec.Encoder;

/**
 * 基于JSON的序列化实现
 */
public class JSONEncoder implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
