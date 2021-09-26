package com.cofii2.methods;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class MThreads {
    
    //Benjamin Winterberg
    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    //Benjamin Winterberg
    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            //System.err.println("termination interrupted");
        }
        finally {
            /*
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            */
            executor.shutdownNow();
        }
    }

    public static void printCurrentThread(){
        System.out.println(Thread.currentThread().getName() + " start");
    }
    public static void printCurrentThread(String concat){
        System.out.println(Thread.currentThread().getName() + " " + concat + " start");
    }
    //-------------------------------------
    private MThreads(){

    }
}
