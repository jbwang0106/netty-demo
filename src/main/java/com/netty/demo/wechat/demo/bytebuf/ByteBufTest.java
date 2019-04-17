package com.netty.demo.wechat.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {

    public static void main(String[] args) {

        // 创建一个byteBuf,指定capacity和maxCapacity
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        println("allocate ByteBuf(9, 100)", buffer);

        // write方法改变写指针，写完之后指针味道capacity的时候，buffer仍然可写
        buffer.writeBytes(new byte[] {1, 2, 3, 4});
        println("writeBytes(new byte[] {1, 2, 3, 4})", buffer);

        // write 方法改变写指针， 写完之后写指针未到capacity的时候，buffer仍然可写， 写完int之后，写指针增加4
        buffer.writeInt(12);
        println("writeInt(12)", buffer);

        // write方法改变写指针， 写完之后写指针等于capacity的时候， buffer不可写
        buffer.writeBytes(new byte[] {5});
        println("writeBytes(new byte[] {5})", buffer);

        // write方法改变写指针， 写的时候发现buffer不可写则开始扩容， 扩容之后capacity随即改变
        buffer.writeBytes(new byte[] {6});
        println("writeBytes(new byte[] {6})", buffer);

        // get 方法不改变写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        println("getByte()", buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        println("setByte()", buffer);

        // read方法改变指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        println("readBytes("+ dst.length +")", buffer);


    }

    private static void println(String action, ByteBuf buffer) {
        System.out.println("after =======================" +action + "===================");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable: " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();

    }
}
