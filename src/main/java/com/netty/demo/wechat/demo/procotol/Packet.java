package com.netty.demo.wechat.demo.procotol;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本号
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
