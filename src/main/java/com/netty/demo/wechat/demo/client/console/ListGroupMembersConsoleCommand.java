package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.ListGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        ListGroupRequestPacket listGroupRequestPacket = new ListGroupRequestPacket();

        System.out.println("输入groupId, 获取群成员列表：");
        String groupId = scanner.nextLine();
        listGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(listGroupRequestPacket);
    }
}
