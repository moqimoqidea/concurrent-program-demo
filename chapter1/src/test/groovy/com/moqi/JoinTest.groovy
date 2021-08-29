package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class JoinTest extends Specification {

    // 2021-08-29 11:16:33:552 INFO  - wait all child thread over!
    // 2021-08-29 11:16:34:536 INFO  - child threadOne over!
    // 2021-08-29 11:16:35:537 INFO  - child threadTwo over!
    // 2021-08-29 11:16:35:538 INFO  - all child thread over!
    def "test join method"() {
        when:
        //1.创建线程1，模拟执行任务
        Thread threadOne = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (InterruptedException e) {
                log.warn(e.printStackTrace())
            }
            log.info("child threadOne over!")
        })

        //2.创建线程2，模拟执行任务
        Thread threadTwo = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (InterruptedException e) {
                log.warn(e.printStackTrace())
            }
            log.info("child threadTwo over!")
        })

        //3.两个线程执行
        threadOne.start()
        threadTwo.start()
        log.info("wait all child thread over!")

        //3.等待子线程执行完毕，返回
        threadOne.join()
        threadTwo.join()
        log.info("all child thread over!")

        then:
        true
    }

    // 线程A调用线程B的join方法后会被阻塞，
    // 当其他线程调用了线程A的interrupt（）方法中断了线程A时，
    // 线程A会抛出InterruptedException异常而返回。
    def "join with interrupted"() {
        when:
        //1.线程one,模拟任务
        Thread threadOne = new Thread(() -> {
            log.info("threadOne begin run!")
            while (true) {
            }
        })

        //2.获取主线程
        final Thread mainThread = Thread.currentThread();

        //3.线程two 模拟中断主线程
        Thread threadTwo = new Thread(() -> {
            //休眠1s
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (InterruptedException e) {
                e.printStackTrace()
            }
            //中断主线程
            mainThread.interrupt()
        })

        //4. 启动子线程
        threadOne.start()
        //延迟1s启动线程
        threadTwo.start()

        //5. 等待线程one执行完毕
        try {
            threadOne.join()
        } catch (InterruptedException e) {
            log.warn("main thread: " + e)
        }

        then:
        true
    }

}
