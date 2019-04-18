package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        String message = messageResponsePacket.getMessage();

        System.out.println(" 收到[" + fromUserId + "] + " + fromUserName + "的消息-> " + message);
    }
}
