package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.CreateGroupResponsePacket;
import com.netty.demo.wechat.demo.procotol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {

        if (joinGroupResponsePacket.isSuccess()) {
            System.out.println("加入群【" + joinGroupResponsePacket.getGroupId() + "】成功");
        } else {
            System.out.println("加入群【" + joinGroupResponsePacket.getGroupId() + "】失败， 失败原因是：" + joinGroupResponsePacket.getReason());
        }
    }
}
