package com.netty.demo.example.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.math.BigInteger;

/**
 * @author sdy
 * server handler
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {

    private BigInteger lastMultiplier = new BigInteger("1");
    private BigInteger factorial = new BigInteger("1");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        System.out.println("server msg = " + msg);
        lastMultiplier = msg;
        factorial = factorial.multiply(msg);
        ctx.writeAndFlush(factorial);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier, factorial);
    }

    /**
     * 发生异常的时候调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
