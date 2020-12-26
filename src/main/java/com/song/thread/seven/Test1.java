package com.song.thread.seven;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j(topic = "c.test")
public class Test1 {

    // 线程1 等待 线程2 的下载结果
    public static void main(String args[]){
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            log.debug("等待结果");
            List<String> list = (List<String>)guardedObject.get();
            log.debug("结果大小：{}",list.size());
        },"t1").start();

        new Thread(()->{
            log.debug("执行下载");
            List<String> download = null;
            try {
                download = Downloader.download();
            } catch (IOException e) {
                e.printStackTrace();
            }
            guardedObject.complete(download);
        },"t2").start();
    }

}

class GuardedObject{

    private Object response;

    public Object get() {
        synchronized (this) {
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();
        }
    }

}
