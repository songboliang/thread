package com.song.thread.six;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 创建一个可以容纳100个线程的线程池
 *
 */
public class TaskExecutionWebServer {

    private static final int NTHREADS =100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String args[])throws IOException{

        ServerSocket socket = new ServerSocket(8099);
        while (true){
            Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        handleRequest(connection);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(task);
        }

    }

    private static void handleRequest(Socket connection) throws InterruptedException {

        System.out.println("执行中.....");
        //Thread.sleep(1000);
        System.out.println("执行结束。");

    }
}
