package com.netty.demo.wechat.demo.server;

import com.netty.demo.wechat.demo.codec.PacketDecoder;
import com.netty.demo.wechat.demo.codec.PacketEncoder;
import com.netty.demo.wechat.demo.codec.Spliter;
import com.netty.demo.wechat.demo.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyServer {

    public static void main(String[] args) {

        // 引导类，用来进行服务端启动相关的工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 负责进行监听的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 负责数据读取的业务线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) {
                        System.out.println("服务端启动中...");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new LogoutRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        ch.pipeline().addLast(new QuitGroupRequestHandler());
                        ch.pipeline().addLast(new ListGroupRequestHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                })
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                .childAttr(AttributeKey.valueOf("c"), "c")
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);


        int port = 8000;
        bind(serverBootstrap, port);
    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port)
                .addListener(future -> {
                   if (future.isSuccess()) {
                       System.out.println("成功绑定到【" +  port + "】端口");
                   } else {
                       System.out.println("端口【" +  port + "】绑定失败");
                       bind(bootstrap, port + 1);
                   }
                });
    }
}
