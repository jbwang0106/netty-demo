package com.netty.demo.wechat.demo.server.handler;

import com.netty.demo.wechat.demo.procotol.request.LoginRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.LoginResponsePacket;
import com.netty.demo.wechat.demo.session.Session;
import com.netty.demo.wechat.demo.util.IDUtil;
import com.netty.demo.wechat.demo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {

            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "] 登录成功...");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            System.out.println(new Date() + " 登录失败");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
