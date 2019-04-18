package com.netty.demo.wechat.demo.procotol.response;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.command.Command;
import com.netty.demo.wechat.demo.session.Session;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {

    private boolean success;

    private String reason;

    private String groupId;

    private String message;

    private Session fromUser;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSGAE_RESPONSE;
    }
}
