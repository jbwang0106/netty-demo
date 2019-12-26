package com.netty.demo.example.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * @author sdy
 * client
 */
public class DiscardClient {

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8009;
    public static final int SIZE = 256;

    public static void main(String[] args) throws Exception {
        final SslContext sslContext;
        if (SSL) {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslContext = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            if (sslContext != null) {
                                pipeline.addLast(sslContext.newHandler(ch.alloc(), HOST, PORT));
                            }

                            pipeline.addLast(new DiscardClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
