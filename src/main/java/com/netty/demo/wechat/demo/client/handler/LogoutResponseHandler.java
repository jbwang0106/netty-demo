package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.response.LoginResponsePacket;
import com.netty.demo.wechat.demo.session.Session;
import com.netty.demo.wechat.demo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 请求登录的handler
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponseHandler> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponseHandler logoutResponseHandler) throws Exception {
       SessionUtil.unBindSession(ctx.channel());
    }

}
