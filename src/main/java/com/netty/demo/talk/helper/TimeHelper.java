package com.netty.demo.talk.helper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sdy
 * 时间helper
 */
@Slf4j
public class TimeHelper {

    /**
     * 开始
     */
    static long start;

    /**
     * 结束
     */
    static long end;

    public static void start() {
        start = System.currentTimeMillis();
        log.info("start: {}", start);
    }

    public static void end() {
        end = System.currentTimeMillis();
        log.info("end: {}", end);
        long time = end - start;
        log.info("总计耗时{}s", time);
    }
}
