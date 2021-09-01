package com.concurrent.program.in.action;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created on 2020-08-29
 */
public class TestTimer {

    // 创建定时器对象
    static Timer timer = new Timer();

    /**
     * ---one Task---
     * Exception in thread "Timer-0" java.lang.RuntimeException: error
     * 	at com.concurrent.program.in.action.TestTimer$1.run(TestTimer.java:26)
     * 	at java.util.TimerThread.mainLoop(Timer.java:555)
     * 	at java.util.TimerThread.run(Timer.java:505)
     */
    public static void main(String[] args) {
        // 添加任务1,延迟500ms执行
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("---one Task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error ");
            }
        }, 500);
        // 添加任务2，延迟1000ms执行
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                for (; ; ) {
                    System.out.println("---two Task---");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);

    }
}
