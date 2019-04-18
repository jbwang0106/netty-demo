package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginCosoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入登录用户名：");
        String userName = scanner.nextLine();

        loginRequestPacket.setUsername(userName);
        loginRequestPacket.setPassword(userName);

        channel.writeAndFlush(loginRequestPacket);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
