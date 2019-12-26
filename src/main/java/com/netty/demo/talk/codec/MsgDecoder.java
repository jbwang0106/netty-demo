package com.netty.demo.talk.codec;

import com.netty.demo.talk.helper.CommonHelper;
import com.netty.demo.talk.msg.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author sdy
 * 消息解码器
 */
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null) {
            throw new RuntimeException("decode byte null");
        } else {
            int len = in.readByte() & 0xff;
            byte[] bytes = new byte[len];
            in.readBytes(bytes);

            Message message = new Message(new String(bytes, CommonHelper.UTF8));
            out.add(message);
        }
    }
}
