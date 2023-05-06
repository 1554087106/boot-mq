package org.whz.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author: hongzhi
 * @date: 2023/5/6 17:51
 * @Description //TODO
 */
@Slf4j
public class MainTest {
    public static void main(String[] args) {
        log.info("开始执行方法...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("休息5秒钟，线程结束...");
    }
}
