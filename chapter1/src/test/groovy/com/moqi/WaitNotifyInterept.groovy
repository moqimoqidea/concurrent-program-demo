package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class WaitNotifyInterrupt extends Specification {

    private static final Object resource = new Object()
    private static int number = 0

    def "if wait thread be interrupt will thrown exception"() {
        when:
        Thread threadA = new Thread(() -> {
            try {
                log.info("---begin---")
                synchronized (resource) {
                    resource.wait()
                }
                log.info("---end---")
            } catch (InterruptedException e) {
                log.warn(e.printStackTrace())
                number = 1
            }
        })

        and:
        threadA.start()
        TimeUnit.SECONDS.sleep(1)
        log.info("---begin interrupt threadA---")
        threadA.interrupt()
        log.info("---end interrupt threadA---")
        TimeUnit.SECONDS.sleep(1)

        then:
        number == 1
    }

}
