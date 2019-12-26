package com.netty.demo.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.math.BigInteger;

/**
 * @author sdy
 * number encoder
 */
public class NumberEncoder extends MessageToByteEncoder<Number> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out) throws Exception {
        BigInteger v;
        if (msg instanceof BigInteger) {
            v = (BigInteger) msg;
        } else {
            v = new BigInteger(String.valueOf(msg));
        }

        byte[] data = v.toByteArray();
        int length = data.length;

        // magic
        out.writeByte((byte) 'F');
        // data length
        out.writeInt(length);
        // data
        out.writeBytes(data);
    }
}
