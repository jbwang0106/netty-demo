package com.netty.demo.wechat.demo.client.handler;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.PacketCodeC;
import com.netty.demo.wechat.demo.procotol.request.LoginRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.LoginResponsePacket;
import com.netty.demo.wechat.demo.procotol.response.MessageResponsePacket;
import com.netty.demo.wechat.demo.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(new Date() + ": 客户端开始登录...");

        // 创建登录请求的对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("hello");
        loginRequestPacket.setPassword("world");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markLogin(ctx.channel());
                System.out.println(new Date() + "客户端登录成功");
            } else {
                System.out.println(new Date() + "客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + " 收到服务端的消息：" + messageResponsePacket.getMessage());

        }
    }
}
