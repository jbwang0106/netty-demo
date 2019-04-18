package com.netty.demo.wechat.demo.server.handler;

import com.netty.demo.wechat.demo.procotol.request.GroupMessageRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.GroupMessageResponsePacket;
import com.netty.demo.wechat.demo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {

        // 1.拿到groupId，构造群聊消息的响应
        String groupId = groupMessageRequestPacket.getGroupId();

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setGroupId(groupId);
        groupMessageResponsePacket.setSuccess(true);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        // 2.拿到群组对应的channelGroup
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
