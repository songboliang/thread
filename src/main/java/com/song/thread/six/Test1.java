package com.song.thread.six;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 *
 * join（）方法等待线程执行结束再继续执行当前主线程
 * join(long time) 方法等待指定时间，如果线程没有结束，则继续执行主线程，不在继续等待
 *
 */
@Slf4j(topic = "c.1")
public class Test1 {

    static int r =0;
    public static void main(String args[]) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(()->{
           log.debug("开始。。。");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r=10;
            log.debug("结束。。。");
        });
        t1.start();
        t1.join(5);
        log.debug("结果为:{}",r);
        log.debug("结束");
    }

}
