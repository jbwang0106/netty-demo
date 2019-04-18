package com.netty.demo.wechat.demo.server.handler;

import com.netty.demo.wechat.demo.procotol.request.ListGroupRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.ListGroupResponsePacket;
import com.netty.demo.wechat.demo.session.Session;
import com.netty.demo.wechat.demo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupRequestHandler extends SimpleChannelInboundHandler<ListGroupRequestPacket> {

    public static final ListGroupRequestHandler INSTANCE = new ListGroupRequestHandler();

    private ListGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupRequestPacket listGroupRequestPacket) throws Exception {

        // 1.获取群组对应的channelGroup
        String groupId = listGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 遍历channelGroup，获取所有的channel中的session
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 3.构造响应发送给客户端
        ListGroupResponsePacket listGroupResponsePacket = new ListGroupResponsePacket();
        listGroupResponsePacket.setSuccess(true);
        listGroupResponsePacket.setGroupId(groupId);
        listGroupResponsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(listGroupResponsePacket);

    }
}
