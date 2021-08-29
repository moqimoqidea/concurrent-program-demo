package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class DoubleLockTest extends Specification {

    private static final Object resourceA = new Object()
    private static final Object resourceB = new Object()

    // stay in: B try get resourceB lock
    // 当线程调用共享对象的 wait() 方法时，当前线程只会释放当前共享对象的锁，当前线程持有的其他共享对象的监视器锁并不会被释放。
    @Ignore
    def 'test two lock execute path'() {
        when:
        Thread A = new Thread(() -> {
            synchronized (resourceA) {
                log.info("A get resourceA lock")
                synchronized (resourceB) {
                    log.info("A get resourceB lock")
                    try {
                        log.info("A release resourceA lock")
                        resourceA.wait()
                    } catch (InterruptedException e) {
                        log.warn(e.printStackTrace())
                    }
                }
            }
        })

        Thread B = new Thread(() -> {
            TimeUnit.SECONDS.sleep(1)
            synchronized (resourceA) {
                log.info("B get resourceA lock")
                log.info("B try get resourceB lock")
                synchronized (resourceB) {
                    try {
                        log.info("B get resourceB lock")
                        log.info("B release resourceA lock")
                        resourceA.wait()
                    } catch (InterruptedException e) {
                        log.warn(e.printStackTrace())
                    }
                }
            }
        })

        A.start()
        B.start()
        A.join()
        B.join()
        log.info("main over")

        then:
        true
    }

}
