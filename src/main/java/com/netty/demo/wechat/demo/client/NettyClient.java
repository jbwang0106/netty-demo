package com.netty.demo.wechat.demo.client;

import com.netty.demo.wechat.demo.client.handler.ClientHandler;
import com.netty.demo.wechat.demo.client.handler.LoginRequestHandler;
import com.netty.demo.wechat.demo.client.handler.MessageRequestHandler;
import com.netty.demo.wechat.demo.codec.PacketDecoder;
import com.netty.demo.wechat.demo.codec.PacketEncoder;
import com.netty.demo.wechat.demo.codec.Spliter;
import com.netty.demo.wechat.demo.procotol.request.MessageRequestPacket;
import com.netty.demo.wechat.demo.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                // 指定线程组
                .group(group)
                // 指定io模型
                .channel(NioSocketChannel.class)
                .attr(AttributeKey.valueOf("CLIENT"), "NETTY_CLIENT")
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 数处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
//                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
           if (future.isSuccess()) {
               System.out.println("连接成功...");
               // 连接成功以后，启动控制台线程
               Channel channel = ((ChannelFuture) future).channel();
               startConsoleThread(channel);

           } else if (retry == 0) {
                System.out.println("重连次数已经用完, 放弃连接");
           } else {
               int order = (MAX_RETRY - retry) + 1;
               // 本次重连的时间间隔
               int delay = 1 << order;
               System.out.println(new Date() + ": 连接失败， 第" + order + "次重连...");
               bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
           }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端： ");
                    Scanner scanner = new Scanner(System.in);

                    String message = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(message);

                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }
}
