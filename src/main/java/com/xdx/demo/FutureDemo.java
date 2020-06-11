package com.xdx.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    private ExecutorService executor= Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(6000);
            Integer result= input*input;
            System.out.println("当前线程："+Thread.currentThread().getName()+",输入："+input+"计算结果:"+result);
            return result;
        });
    }
    public static void main(String[] args) {
        Future<Integer> future1 = new FutureDemo().calculate(2);
        Future<Integer> future2 = new FutureDemo().calculate(6);
        long startTime=System.currentTimeMillis();
        while (!(future1.isDone()&&future2.isDone())) {
//            System.out.println("计算中");
            if(System.currentTimeMillis()-startTime>10000){
                System.out.println("主线程"+Thread.currentThread().getName()+"程序超时");
                throw new RuntimeException("超时了");
            }
        }
        try {
            Integer result=future1.get();
            System.out.println("主线程"+Thread.currentThread().getName()+",等待时间："+(System.currentTimeMillis()-startTime)+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
