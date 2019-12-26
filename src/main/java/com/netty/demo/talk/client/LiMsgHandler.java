package com.netty.demo.talk.client;

import com.netty.demo.talk.helper.CommonHelper;
import com.netty.demo.talk.helper.TimeHelper;
import com.netty.demo.talk.msg.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sdy
 * client端的handler
 */
@Slf4j
@ChannelHandler.Sharable
public class LiMsgHandler extends SimpleChannelInboundHandler<Message> {

    public static final LiMsgHandler INSTANCE = new LiMsgHandler();

    private final String z0 = "吃了没，您吶?";
    private final String l1 = "刚吃。";
    private final String l2 = "您这，嘛去？";
    private final String z3 = "嗨！吃饱了溜溜弯儿。";
    private final String l4 = "有空家里坐坐啊。";
    private final String z5 = "回头去给老太太请安！";

    /**
     * worker只有1个线程，不需要原子类
     */
    private int cnt1 = 0;
    private int cnt2 = 0;
    private int cnt3 = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        String msgMsg = msg.getMsg();
        if (z0.equals(msgMsg)) {
            cnt1 ++;
            ctx.write(new Message(l1));
            ctx.write(new Message(l2));
            if (cnt1 % 1000 == 0) {
                ctx.flush();
            }
        } else if (z3.equals(msgMsg)) {
            cnt2 ++;
            ctx.write(new Message(l4));
            if (cnt2 % 1000 == 0) {
                ctx.flush();
            }
        } else if (z5.equals(msgMsg)) {
            cnt3 ++;
            if (cnt3 == CommonHelper.SEND_COUNT) {
                TimeHelper.end();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("LiMsgHandler exception caught: ", cause);
        ctx.close();
    }
}
