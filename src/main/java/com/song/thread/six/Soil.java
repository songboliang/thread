package com.song.thread.six;

public class Soil implements Runnable{

    @Override
    public void run() {
        int i=100;
        while(i<1){
            System.out.println("石油开始售卖");
            i--;
        }
    }
}
