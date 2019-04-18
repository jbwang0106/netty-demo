package com.netty.demo.wechat.demo.codec;

import com.netty.demo.wechat.demo.procotol.Packet;
import com.netty.demo.wechat.demo.procotol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
