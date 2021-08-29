package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class NotifyAllTest extends Specification {

    private static final Object resource = new Object()

    @Shared
    Thread A = new Thread(() -> {
        synchronized (resource) {
            log.info("A get resource lock")
            try {
                log.info("A begin wait")
                resource.wait()
                log.info("A end wait")
            } catch (InterruptedException e) {
                log.warn(e.printStackTrace())
            }
        }
    })

    @Shared
    Thread B = new Thread(() -> {
        synchronized (resource) {
            log.info("B get resource lock")
            try {
                log.info("B begin wait")
                resource.wait()
                log.info("B end wait")
            } catch (InterruptedException e) {
                log.warn(e.printStackTrace())
            }
        }
    })

    @Shared
    Thread notifyC = new Thread(() -> {
        synchronized (resource) {
            log.info("C begin notify")
            resource.notify()
        }
    })

    @Shared
    Thread notifyAllC = new Thread(() -> {
        synchronized (resource) {
            log.info("C begin notify all")
            resource.notifyAll()
        }
    })

    @Ignore
    def "notify just weak up one thread"() {
        when:
        A.start()
        B.start()
        TimeUnit.SECONDS.sleep(1)
        notifyC.start()
        A.join()
        B.join()
        notifyC.join()
        log.info("main over")

        then:
        true
    }

    def "notifyAll weak up all thread"() {
        when:
        A.start()
        B.start()
        TimeUnit.SECONDS.sleep(1)
        notifyAllC.start()
        A.join()
        B.join()
        notifyAllC.join()
        log.info("main over")

        then:
        true
    }

    @Ignore
    def "notifyAll not weak up a thread after itself"() {
        when:
        A.start()
        TimeUnit.SECONDS.sleep(1)
        notifyAllC.start()
        A.join()
        notifyAllC.join()

        log.info("now start B thread")
        B.start()
        B.join()
        log.info("main over")

        then:
        true
    }

}
