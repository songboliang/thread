package com.song.thread.six;

import java.util.concurrent.Executor;

/**
 *
 * 让TaskExecutionWebServer像ThreadPerTaskWebServer一样运行
 *
 */
public class ThreadPerTaskExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }


    public static void main(String args[]){
        Runnable r = () -> System.out.println("running...");
    }
}
