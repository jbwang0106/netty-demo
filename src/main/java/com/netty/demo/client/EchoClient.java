package com.netty.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: 王江波
 * @Mail: wangjiangbo@sudiyi.cn
 * @Date: 10:55 10/30/2018
 * @Version: 1.0
 * @Description: echoClient
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // 创建eventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 指定eventLoopGroup以处理客户端事件，需要适用于NIO的实现
            bootstrap.group(group)
                    // 适用于NIO传输的channel类型
                    .channel(NioSocketChannel.class)
                    // 设置服务器的inetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 在创建channel时，向channelPipeline中添加一个echoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture future = bootstrap.connect().sync();
            // 阻塞，直到channel关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: " + EchoClient.class.getSimpleName() + "<host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
