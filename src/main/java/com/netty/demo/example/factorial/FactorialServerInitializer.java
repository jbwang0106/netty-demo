package com.netty.demo.example.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

/**
 * @author sdy
 * server initializer
 */
public class FactorialServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 压缩解压缩handler
//        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

        // 编解码器handler
        pipeline.addLast(new NumberEncoder());
        pipeline.addLast(new BigIntegerDecoder());

        // 业务handler
        pipeline.addLast(new FactorialServerHandler());
    }
}
