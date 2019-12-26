package com.netty.demo.talk.server;

import com.netty.demo.talk.helper.CommonHelper;
import com.netty.demo.talk.helper.NamedThreadFactory;
import com.netty.demo.talk.helper.TimeHelper;
import com.netty.demo.talk.msg.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sdy
 */
@Slf4j
@ChannelHandler.Sharable
public class ZhangServerHandler extends SimpleChannelInboundHandler<Message> {

    public static final ZhangServerHandler INSTANCE = new ZhangServerHandler();

    private final String z0 = "吃了没，您吶?";
    private final String l1 = "刚吃。";
    private final String l2 = "您这，嘛去？";
    private final String z3 = "嗨！吃饱了溜溜弯儿。";
    private final String l4 = "有空家里坐坐啊。";
    private final String z5 = "回头去给老太太请安！";

    private int cnt1 = 0;
    private int cnt2 = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        String msgMsg = msg.getMsg();
        if (l2.equals(msgMsg)) {
            cnt1 ++;
            ctx.write(new Message(z3));
            if (cnt1 % 1000 == 0) {
                ctx.flush();
            }
        } else if (l4.equals(msgMsg)) {
            cnt2 ++;
            ctx.write(new Message(z5));
            if (cnt2 % 1000 == 0) {
                ctx.flush();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 在这里进行10000的发送
        TimeHelper.start();
     //   ctx.writeAndFlush(new Message(z0));

        new NamedThreadFactory("send").newThread(() -> {
            for (int i = 0; i < CommonHelper.SEND_COUNT; i++) {
                ctx.write(new Message(z0));
                if (i % 1000 == 0) {
                    ctx.flush();
                }
            }
        }).start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("Zhang server handler exception caught: ", cause);
        ctx.close();
    }
}
