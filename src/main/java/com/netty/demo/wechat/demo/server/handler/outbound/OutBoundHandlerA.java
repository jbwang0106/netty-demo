package com.netty.demo.wechat.demo.server.handler.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.Date;

public class OutBoundHandlerA extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(new Date() + " OutBoundHandlerA " + msg);
        super.write(ctx, msg, promise);
    }
}
