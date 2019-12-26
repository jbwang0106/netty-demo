package com.netty.demo.example.factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author sdy
 * client
 */
public class FactorialClient {

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new FactorialClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
            FactorialClientHandler handler = (FactorialClientHandler) channelFuture.channel().pipeline().last();

            System.out.println("~~~~~~~~~~~~~~~~~" + handler.getFactorial());
        } finally {
            group.shutdownGracefully();
        }

    }
}
