package com.netty.demo.wechat.demo.server.handler;

import com.netty.demo.wechat.demo.procotol.request.LogoutRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.LogoutResponsePacket;
import com.netty.demo.wechat.demo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {}


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket loginRequestPacket) throws Exception {

        SessionUtil.unBindSession(ctx.channel());

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);

        ctx.channel().writeAndFlush(logoutResponsePacket);
    }

}
