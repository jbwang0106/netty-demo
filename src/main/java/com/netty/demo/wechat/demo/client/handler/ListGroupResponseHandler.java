package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.JoinGroupResponsePacket;
import com.netty.demo.wechat.demo.procotol.response.ListGroupResponsePacket;
import com.netty.demo.wechat.demo.server.handler.ListGroupRequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListGroupResponseHandler extends SimpleChannelInboundHandler<ListGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupResponsePacket listGroupResponsePacket) throws Exception {

        if (listGroupResponsePacket.isSuccess()) {
            System.out.println("群组【" + listGroupResponsePacket.getGroupId() + "】中的成员包括：" + listGroupResponsePacket.getSessionList());
        }
    }
}
