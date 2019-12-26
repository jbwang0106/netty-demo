package com.netty.demo.example.factorial;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sdy 
 * client handler
 */
public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {

    private ChannelHandlerContext ctx;
    private int receivedMessages;
    private int next = 1;
    final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<>();
    
    public BigInteger getFactorial() {
        boolean interrupted = false;
        try {
            for (; ; ) {
                try {
                    return answer.take();
                } catch (InterruptedException ignore) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        sendNumbers();
    }

    private void sendNumbers() {
//        ChannelFuture future = null;
//        for (int i = 0; i < 4096 && next <= 1000; i++) {
//            future = ctx.write(next);
//            next ++;
//        }
//
//        if (next <= 1000) {
//            assert future != null;
//            future.addListener(numberSender);
//        }
//
//        ctx.flush();
        ChannelFuture future = ctx.write(next++);
        assert future != null;
        future.addListener(numberSender);
        ctx.flush();
    }

    private final ChannelFutureListener numberSender = (ChannelFuture future) -> {
        if (future.isSuccess()) {
            if (next <= 1000) {
                sendNumbers();
            }
        } else {
          future.cause().printStackTrace();
          future.channel().close();
        }
    };

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        System.out.println("client msg = " + msg);
        receivedMessages ++;
        if (receivedMessages == 1000) {
            ctx.channel().close().addListener(future -> {
                boolean offer = answer.offer(msg);
                assert offer;
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
