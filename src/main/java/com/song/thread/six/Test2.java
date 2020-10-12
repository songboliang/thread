package com.song.thread.six;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * 两阶段终止模式
 *
 */
@Slf4j(topic = "c.test2")
public class Test2 {

    public static void main(String args[]) throws InterruptedException {
        TwoPhaseTernination t = new TwoPhaseTernination();
        t.start();
        TimeUnit.SECONDS.sleep(4);
        t.stop();
    }

}

@Slf4j(topic = "c.test2")
class TwoPhaseTernination{

    private Thread monitor;

    // 启动线程监控
    public void start(){
        monitor = new Thread(()->{

            // 循环监控
            while(true){
                Thread current = Thread.currentThread();

                //是否被打断
                if(current.isInterrupted()){
                    log.debug("料理后事");
                    break;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //因为sleep出现异常后，会清楚打断标记
                    //需要重置打断标记
                    current.interrupt();
                }

            }

        }, "monitor");
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }

}
