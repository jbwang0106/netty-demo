package com.netty.demo.wechat.demo.util;

import com.netty.demo.wechat.demo.attributes.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


public class LoginUtil {

    public static void markLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
