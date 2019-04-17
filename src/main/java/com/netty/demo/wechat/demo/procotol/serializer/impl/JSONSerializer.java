package com.netty.demo.wechat.demo.procotol.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.netty.demo.wechat.demo.procotol.serializer.Serializer;
import com.netty.demo.wechat.demo.procotol.serializer.SerializerAlorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
