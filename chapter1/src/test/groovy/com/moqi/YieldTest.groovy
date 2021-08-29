package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class YieldTest extends Specification {

    def "yield test"() {
        when:
        new Thread(new Yield()).start()
        new Thread(new Yield()).start()
        new Thread(new Yield()).start()
        TimeUnit.SECONDS.sleep(1)

        then:
        true
    }

    private static class Yield implements Runnable {
        @Override
        void run() {
            def thread = Thread.currentThread().toString()
            for (int i = 0; i < 5; i++) {
                //当i=0时候出让cpu执行权，放弃时间片，进行下一轮调度
                if (i == 0) {
                    log.info(thread + " yield cpu...")
                    //当前 出让cpu执行权，放弃时间片，进行下一轮调度
                    Thread.yield()
                }
            }

            log.info(thread + " is over")
        }
    }
}
