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

    /**
     * 加入群组请求指令
     */
    Byte JOIN_GROUP_REQUEST = 9;

    /**
     * 加入群组响应指令
     */
    Byte JOIN_GROUP_RESPONSE = 10;

    /**
     * 退出群组请求指令
     */
    Byte QUIT_GROUP_REQUEST = 11;

    /**
     * 退出群组响应指令
     */
    Byte QUIT_GROUP_RESPONSE = 12;

    /**
     * 群组列表请求指令
     */
    Byte LIST_GROUP_REQUEST = 13;

    /**
     * 群组列表响应指令
     */
    Byte LIST_GROUP_RESPONSE = 14;
}
