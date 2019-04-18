package com.netty.demo.wechat.demo.procotol.request;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.command.Command;
import lombok.Data;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return Command.LOGOUT_REQUEST;
    }
}
