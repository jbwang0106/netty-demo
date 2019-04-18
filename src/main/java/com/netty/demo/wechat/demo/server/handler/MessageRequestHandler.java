package com.netty.demo.wechat.demo.server.handler;

import com.netty.demo.wechat.demo.procotol.request.MessageRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.MessageResponsePacket;
import com.netty.demo.wechat.demo.session.Session;
import com.netty.demo.wechat.demo.util.SessionUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        // 1.拿到发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        String message = messageRequestPacket.getMessage();
        messageResponsePacket.setMessage(message);
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        // 3. 拿到消息接收方的channel
        Channel toChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        if (toChannel != null && SessionUtil.hasLogin(toChannel)) {
            toChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败");
        }

    }
}
