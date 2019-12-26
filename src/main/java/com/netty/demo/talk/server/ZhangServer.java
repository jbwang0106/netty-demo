package com.netty.demo.talk.server;

import com.netty.demo.talk.codec.MsgDecoder;
import com.netty.demo.talk.codec.MsgEncoder;
import com.netty.demo.talk.codec.PackageDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sdy
 * server
 */
@Slf4j
public class ZhangServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new PackageDecoder());
                        pipeline.addLast(new MsgDecoder());
                        pipeline.addLast(MsgEncoder.INSTANCE);

                        pipeline.addLast(ZhangServerHandler.INSTANCE);
                    }
                });

        serverBootstrap.bind(7000).addListener(future -> {
            if (future.isSuccess()) {
                log.info("启动成功...");
            } else {
                log.error("启动失败", future.cause());
            }
        });

    }

}
