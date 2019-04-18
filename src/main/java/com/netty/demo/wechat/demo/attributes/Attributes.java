package com.netty.demo.wechat.demo.attributes;

import com.netty.demo.wechat.demo.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
