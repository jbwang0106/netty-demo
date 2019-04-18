package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();

        System.out.println("输入groupId, 退出群聊：");

        String groupId = scanner.nextLine();
        quitGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
