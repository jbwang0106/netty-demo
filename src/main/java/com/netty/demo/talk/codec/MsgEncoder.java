package com.netty.demo.talk.codec;

import com.netty.demo.talk.helper.CommonHelper;
import com.netty.demo.talk.msg.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sdy
 * 消息编码器
 */
@ChannelHandler.Sharable
public class MsgEncoder extends MessageToByteEncoder<Message> {

    public static final MsgEncoder INSTANCE = new MsgEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (msg == null) {
            throw new RuntimeException("encode msg null");
        } else {
            byte[] bytes = msg.getMsg().getBytes(CommonHelper.UTF8);
            int len = bytes.length;
            out.writeByte(len);
            out.writeBytes(bytes);
        }
    }
}
