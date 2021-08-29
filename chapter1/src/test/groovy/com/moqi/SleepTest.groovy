package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Slf4j
class SleepTest extends Specification {

    //1. 创建一个独占锁
    private static final Lock lock = new ReentrantLock()

    def "test sleep"() {
        when:
        //2. 创建线程A
        Thread A = new Thread(() -> {
            // 获取独占锁
            lock.lock()
            try {
                log.info("child A is in sleep")
                TimeUnit.SECONDS.sleep(1)
                log.info("child A is in awake")
            } catch (InterruptedException e) {
                e.printStackTrace()
            } finally {
                // 释放锁
                lock.unlock()
            }
        })

        //2. 创建线程B
        Thread B = new Thread(() -> {
            // 获取独占锁
            lock.lock()
            try {
                log.info("child B is in sleep")
                TimeUnit.SECONDS.sleep(1)
                log.info("child B is in awake")
            } catch (InterruptedException e) {
                e.printStackTrace()
            } finally {
                // 释放锁
                lock.unlock()
            }
        })

        // 启动线程
        A.start()
        B.start()
        A.join()
        B.join()
        log.info("main over.")

        then:
        true
    }
}
