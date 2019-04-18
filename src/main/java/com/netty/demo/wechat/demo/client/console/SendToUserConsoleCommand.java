package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("发送消息给某个用户：");

        String userId = scanner.nextLine();
        String message = scanner.nextLine();
        channel.writeAndFlush(new MessageRequestPacket(userId, message));
    }
}
