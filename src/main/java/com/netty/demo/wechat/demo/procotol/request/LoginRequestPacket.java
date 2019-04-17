package com.netty.demo.wechat.demo.procotol.request;

import com.netty.demo.wechat.demo.procotol.command.Command;
import com.netty.demo.wechat.demo.procotol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
