package com.song.thread.six;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.test")
public class Test {

    public static void main(String args[]) throws InterruptedException {
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                log.debug("线程1 running ... ");
                method1();
            }
        };
        t1.run();

        log.debug("主线程运行");
        Thread t2 = new Thread(new Soil(),"线程2");
        t2.start();
    }

    private static int method1() {
        int x = 0;
        int y = 100;

        int z = 0;
        z = x+y;

        log.debug("方法1" + z);
        return z;
    }


}
