package com.xdx.demo;

import com.xdx.demo.cache.ICache;

import java.util.concurrent.*;

public class FutureDemo {
    private ExecutorService executor= Executors.newFixedThreadPool(2);
    private ExecutorService executor2 = Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(0);
            Integer result= input*input;
            if (input == 2) {
                executor2.execute(()->{
                    try {
                        Thread.sleep(0);
                        System.out.println(Thread.currentThread().getName()+"执行存库操作");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            System.out.println("当前线程："+Thread.currentThread().getName()+",输入："+input+"计算结果:"+result);
            return result;
        });
    }
    public Future<Integer>getFuture(int a){
        if (a == 1) {

            return new Future<Integer>() {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return false;
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }

                @Override
                public boolean isDone() {
                    return true;
                }

                @Override
                public Integer get() throws InterruptedException, ExecutionException {
                    long starttime=System.currentTimeMillis();
                    System.out.println("开始执行线程1");
                    int b=0;
                    for(int i=0;i<1000000000;i++){
                        b++;
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"，耗时:"+(System.currentTimeMillis()-starttime)+"ms");
                    return b;
                }

                @Override
                public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                    return null;
                }
            };
        }else{
            return new FutureDemo().calculate(4);
        }
    }
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        FutureDemo fd=new FutureDemo();
        Future<Integer>f1=fd.getFuture(1);
        if (f1.isDone()) {
            try {
                System.out.println(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Future<Integer> future1 = new FutureDemo().calculate(2);
        Future<Integer> future2 = new FutureDemo().calculate(6);
        while (!(future1.isDone()&&future2.isDone())) {
            if(System.currentTimeMillis()-startTime>6200){
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
