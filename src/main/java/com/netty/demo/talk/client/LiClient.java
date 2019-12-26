package com.netty.demo.talk.client;

import com.netty.demo.talk.codec.MsgDecoder;
import com.netty.demo.talk.codec.MsgEncoder;
import com.netty.demo.talk.codec.PackageDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 李大爷
 * @author sdy
 */
public class LiClient {

    public static void main(String[] args) {

        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        // 包解析器
                        pipeline.addLast(new PackageDecoder());
                        // 消息编码器
                        pipeline.addLast(MsgEncoder.INSTANCE);
                        // 消息解码器
                        pipeline.addLast(new MsgDecoder());
                        // 业务处理的handler
                        pipeline.addLast(LiMsgHandler.INSTANCE);
                    }
                });


        bootstrap.connect("localhost", 7000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功...");
            } else {
                System.out.println("连接建立失败...");
            }
        });

    }
}
