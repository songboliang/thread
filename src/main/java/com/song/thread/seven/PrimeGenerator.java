package com.song.thread.seven;

import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ThreadSafe
public class PrimeGenerator implements  Runnable{

    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while(!cancelled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
    }

    public void cancel(){
        cancelled = true;
    }

    public synchronized List<BigInteger> get(){
        return  new ArrayList<>(primes);
    }

    List<BigInteger> aSecondOfPrimes()throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }finally {
            generator.cancel();
        }
        return generator.get();
    }

    public static void main(String args[]) throws InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        List<BigInteger> bigIntegers = primeGenerator.aSecondOfPrimes();
        System.out.println(bigIntegers);
    }

}
