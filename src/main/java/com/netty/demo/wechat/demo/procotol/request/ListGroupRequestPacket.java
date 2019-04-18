package com.netty.demo.wechat.demo.procotol.request;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.command.Command;
import lombok.Data;

@Data
public class ListGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_REQUEST;
    }
}
