package com.netty.demo.wechat.demo.client.console;

import com.netty.demo.wechat.demo.procotol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.println("【拉人群聊】输入userId列表，userId之间英文逗号分隔开：");

        String userIds = scanner.nextLine();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
