package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class WaitNotifyInterruptTest extends Specification {

    private static final Object resource = new Object()
    private static int number = 0

    def "if wait thread be interrupt will thrown exception"() {
        when:
        Thread A = new Thread(() -> {
            log.info("---begin---")
            synchronized (resource) {
                try {
                    resource.wait()
                } catch (InterruptedException e) {
                    log.warn(e.printStackTrace())
                    number = 1
                }
            }
            log.info("---end---")
        })

        and:
        A.start()
        TimeUnit.SECONDS.sleep(1)
        log.info("---begin interrupt A---")
        A.interrupt()
        log.info("---end interrupt A---")
        TimeUnit.SECONDS.sleep(1)

        then:
        number == 1
    }
}
