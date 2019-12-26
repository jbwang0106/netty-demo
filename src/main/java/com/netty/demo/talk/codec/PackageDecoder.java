package com.netty.demo.talk.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author sdy
 * 包解析器
 */
public class PackageDecoder extends ByteToMessageDecoder {

    private static LengthDecoder lengthDecoder = new LengthDecoder();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readableBytes();
        if (len <= LengthDecoder.LENGTH_FIELD_LENGTH) {
            return;
        }

        Object decode = lengthDecoder.decode(ctx, in);

        if (decode != null) {
            out.add(decode);
        }
    }
}
