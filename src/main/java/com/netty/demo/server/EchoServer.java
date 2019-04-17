package com.netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author: 王江波
 * @Mail: wangjiangbo@sudiyi.cn
 * @Date: 18:26 10/29/2018
 * @Version: 1.0
 * @Description: echoServer
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();

        // 创建eventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建serverBootStrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    // 指定所使用的NIO传输channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个EchoServerHandler到子Channel的ChannelPipeline中
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });

            // 异步的绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            // 获取channel的closeFuture,并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();

        } finally {
            // 关闭EventLoopGroup释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
