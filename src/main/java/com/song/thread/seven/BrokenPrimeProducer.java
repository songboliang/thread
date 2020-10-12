package com.song.thread.seven;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BrokenPrimeProducer extends Thread{

    private final BlockingQueue<BigInteger> queue;

    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    public void run(){
        try{
            BigInteger p = BigInteger.ONE;
            while (!cancelled){
                queue.put(p.nextProbablePrime());
            }
        }catch (InterruptedException consumed){

        }
    }

    public void cancel(){
        cancelled = true;
    }

    void consumePrimes()throws InterruptedException{
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(8);
        BrokenPrimeProducer  producer = new BrokenPrimeProducer(primes);
        producer.start();
        try{
            while (needMorePrimes())
                consume(primes.take());
        }finally {
            producer.cancel();
            System.out.println(queue);
        }
    }

    private void consume(BigInteger take) throws InterruptedException {
        queue.put(take);
    }

    private boolean needMorePrimes() {
        return queue.size() < 7;
    }


    public static void main(String args[]) throws InterruptedException {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(8);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.consumePrimes();
    }

}

