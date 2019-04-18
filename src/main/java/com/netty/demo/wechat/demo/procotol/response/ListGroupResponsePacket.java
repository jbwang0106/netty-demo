package com.netty.demo.wechat.demo.procotol.response;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.command.Command;
import com.netty.demo.wechat.demo.session.Session;
import lombok.Data;

import java.util.List;

@Data
public class ListGroupResponsePacket extends Packet {

    private boolean success;

    private String reason;

    private String groupId;

    private List<Session>  sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_RESPONSE;
    }
}
