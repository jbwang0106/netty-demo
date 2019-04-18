package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {

        if (quitGroupResponsePacket.isSuccess()) {
            System.out.println("退出群【" + quitGroupResponsePacket.getGroupId() + "】成功");
        } else {
            System.out.println("退出群【" + quitGroupResponsePacket.getGroupId() + "】失败， 失败原因是：" + quitGroupResponsePacket.getReason());
        }
    }
}
