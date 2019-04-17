package com.netty.demo.wechat.demo.procotol;

import com.netty.demo.wechat.demo.procotol.command.Command;
import com.netty.demo.wechat.demo.procotol.request.LoginRequestPacket;
import com.netty.demo.wechat.demo.procotol.request.MessageRequestPacket;
import com.netty.demo.wechat.demo.procotol.response.LoginResponsePacket;
import com.netty.demo.wechat.demo.procotol.response.MessageResponsePacket;
import com.netty.demo.wechat.demo.procotol.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializeMap;



    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializeMap = new HashMap<>();
        serializeMap.put(Serializer.JSON_SERIALIZER, Serializer.DEFAULT);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {

        // 创建buffer对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        // 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.JSON_SERIALIZER);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据长度
        int length = byteBuf.readInt();

        // 数据
        byte[] bytes = new byte[length];
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        byteBuf.readBytes(bytes);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializeMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
