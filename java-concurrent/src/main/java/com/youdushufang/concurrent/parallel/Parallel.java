package com.youdushufang.concurrent.parallel;

import com.youdushufang.concurrent.utils.Log;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// CyclicBarrier 用于多个线程间需要共同等待一个节点的情况，例如下面的例子里，
// 检查函数 check 需要等待一组订单和物流信息，获取订单信息和获取物流信息有一个时间差，
// CyclicBarrier 指定了两个参与方，await 函数会一直阻塞到所有参与方"参与"进来，
// 因此实际上更快的获取物流信息的线程每次都会等待获取订单信息的线程

// 当然如果后续的 check 流程也比较耗时，就会造成数据在队列里积压
public class Parallel {

    // 订单信息队列
    private Vector<String> orderList = new Vector<>();

    // 物流信息队列
    private Vector<String> logisticsList = new Vector<>();

    // 固定一个大小的线程池，避免多线程并发处理后续结果
    private Executor executor = Executors.newFixedThreadPool(1);

    // 回调函数会在到达临界点时调用，"cyclic" 意味着到达临界点后计数器会重置
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2,
            () -> executor.execute(this::check));

    public void checkAll() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // 分页获取订单数据
            for (int i = 0; i < 5; i++) {
                orderList.add(getOrders());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            // 分页获取物流数据
            for (int i = 0; i < 5; i++) {
                logisticsList.add(getLogistics());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        t1.join();
        t2.join();
    }

    // 依赖 getOrders 和 getLogistics 获取到的数据检查相关信息
    private void check() {
        String orders = orderList.remove(0);
        String logistics = logisticsList.remove(0);
        Log.log("check " + orders + ", " + logistics);
    }

    // 获取账单信息
    private String getOrders() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String order = "or" + System.currentTimeMillis() / 1000L;
        Log.log("got order: " + order);
        return order;
    }

    // 获取物流信息
    private String getLogistics() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String logistics = "lo" + System.currentTimeMillis() / 1000L;
        Log.log("got logistics: " + logistics);
        return logistics;
    }
}