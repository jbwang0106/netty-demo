package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.println("输入groupId, 加入群聊：");

        String groupId = scanner.nextLine();
        joinGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
