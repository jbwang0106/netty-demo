package com.netty.demo.talk.helper;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sdy
 * 线程的factory
 */
public class NamedThreadFactory implements ThreadFactory {

    private AtomicInteger idx = new AtomicInteger(0);
    private String prefix;

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + idx.getAndIncrement());
    }
}
