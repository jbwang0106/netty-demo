package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.GroupMessageRequestPacket;
import com.netty.demo.wechat.demo.procotol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("发送消息给某个：");

        String groupId = scanner.nextLine();
        String message = scanner.nextLine();
        channel.writeAndFlush(new GroupMessageRequestPacket(groupId, message));
    }
}
