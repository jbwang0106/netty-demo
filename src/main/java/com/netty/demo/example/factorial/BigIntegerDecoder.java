package com.netty.demo.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.math.BigInteger;
import java.util.List;

/**
 * @author sdy
 * 解码器
 */
public class BigIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }

        in.markReaderIndex();

        // 检查magic number
        int magicNumber = in.readUnsignedByte();
        if (magicNumber != 'F') {
            in.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }

        // 等待直到能读取到完整的数据为止
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        // 转换成bigInteger
        byte[] decoded = new byte[dataLength];
        in.readBytes(decoded);

        out.add(new BigInteger(decoded));

    }
}
