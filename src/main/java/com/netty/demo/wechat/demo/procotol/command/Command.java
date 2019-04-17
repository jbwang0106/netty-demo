package com.netty.demo.wechat.demo.procotol.command;

public interface Command {

    /**
     * 登录请求指令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录响应指令
     */
    Byte LOGIN_RESPONSE = 2;

    /**
     * 消息请求指令
     */
    Byte MESSAGE_REQUEST = 3;

    /**
     * 消息响应指令
     */
    Byte MESSAGE_RESPONSE = 4;
}
