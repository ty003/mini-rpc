package com.javalearning.minirpc.codec.impl;

import com.alibaba.fastjson.JSON;
import com.javalearning.minirpc.codec.Decoder;

/**
 * 基于JSON的反序列化实现
 */
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decoder(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
