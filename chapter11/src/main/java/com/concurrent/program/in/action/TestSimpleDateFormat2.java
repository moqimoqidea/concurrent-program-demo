package com.concurrent.program.in.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created on 2020-08-29
 */
public class TestSimpleDateFormat2 {

    // (1)创建 threadLocal 实例
    static ThreadLocal<DateFormat> safeSdf = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * Wed Dec 13 15:17:27 CST 2017
     * Wed Dec 13 15:17:27 CST 2017
     * Wed Dec 13 15:17:27 CST 2017
     * .........
     */
    public static void main(String[] args) {
        // (2)创建多个线程，并启动
        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(() -> {
                try {// (3)使用单例日期实例解析文本
                    System.out.println(safeSdf.get().parse("2017-12-13 15:17:27"));
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    // (4)使用完毕记得清除，避免内存泄露
                    safeSdf.remove();
                }
            });
            thread.start();// (4)启动线程
        }
    }

}
