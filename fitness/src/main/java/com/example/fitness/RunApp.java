package com.example.fitness;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过run运行程序
 */
public class RunApp {
    public static void main  (String[] args)throws SQLException {
        ExecutorService executorService = Executors.newFixedThreadPool(3); // 3 是线程池的大小

        // 启动多个线程
        for (int i = 0; i < 3; i++) { // 这里启动了 3 个线程，您可以根据需要调整数量
            MyThread myThread = new MyThread();
            executorService.execute(myThread); // 提交任务给线程池执行
        }

        // 关闭线程池
        executorService.shutdown();
        MainApp.main(args);
    }

}

