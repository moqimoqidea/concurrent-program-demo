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

}
