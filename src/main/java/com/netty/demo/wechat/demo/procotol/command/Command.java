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

    /**
     * 创建群组请求指令
     */
    Byte CREATE_GROUP_REQUEST = 5;

    /**
     * 创建群组响应指令
     */
    Byte CREATE_GROUP_RESPONSE = 6;

    /**
     * 登出请求指令
     */
    Byte LOGOUT_REQUEST = 7;

    /**
     * 登出响应指令
     */
    Byte LOGOUT_RESPONSE = 8;
}
