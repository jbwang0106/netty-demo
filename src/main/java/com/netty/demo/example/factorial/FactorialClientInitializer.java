package com.netty.demo.example.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

/**
 * @author sdy
 * client initializer
 */
public class FactorialClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // 解压缩handler
//        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

        // 编解码handler
        pipeline.addLast(new NumberEncoder());
        pipeline.addLast(new BigIntegerDecoder());

        // 业务handler
        pipeline.addLast(new FactorialClientHandler());
    }
}
