package com.example.fitness;

/**
 * 测试类，用于测试各种方法
 */
public class Text {
    public static void main(String[] args) throws
            InterruptedException {
        MyThread myThread1 = new MyThread();
        myThread1.setName("线程一");
        myThread1.start();
        MyThread myThread2 = new MyThread();
        myThread2.setName("线程二");
        myThread2.start();
    }
}
class MyThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}