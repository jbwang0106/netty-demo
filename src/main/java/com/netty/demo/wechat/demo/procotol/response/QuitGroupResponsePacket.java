package com.netty.demo.wechat.demo.procotol.response;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.command.Command;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {

    private boolean success;

    private String reason;

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
