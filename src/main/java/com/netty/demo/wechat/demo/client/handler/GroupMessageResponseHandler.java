package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {

        if (groupMessageResponsePacket.isSuccess()) {
            System.out.println("收到群【" + groupMessageResponsePacket.getGroupId() + "】中 【" + groupMessageResponsePacket.getFromUser() + "】发来的消息：" + groupMessageResponsePacket.getMessage());
        }
    }
}
