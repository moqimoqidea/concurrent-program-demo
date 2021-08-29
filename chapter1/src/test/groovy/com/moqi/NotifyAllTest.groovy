package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class NotifyAllTest extends Specification {

    private static final Object resource = new Object()

    @Ignore
    def "notify just weak up one thread"() {
        when:
        Thread A = new Thread(() -> {
            synchronized (resource) {
                log.info("A get resource lock")
                try {
                    log.info("A begin wait")
                    resource.wait()
                    log.info("A end wait")
                } catch (InterruptedException e) {
                    log.warn(e.getMessage())
                }
            }

        })
        Thread B = new Thread(() -> {
            synchronized (resource) {
                log.info("B get resource lock")
                try {
                    log.info("B begin wait")
                    resource.wait()
                    log.info("B end wait")
                } catch (InterruptedException e) {
                    log.warn(e.getMessage())
                }
            }

        })
        Thread C = new Thread(() -> {
            synchronized (resource) {
                log.info("C begin notify")
                resource.notify()
            }

        })

        and:
        A.start()
        B.start()
        TimeUnit.SECONDS.sleep(1)
        C.start()
        A.join()
        B.join()
        C.join()
        log.info("main over")

        then:
        true
    }

    def "notifyAll weak up all thread"() {
        when:
        Thread A = new Thread(() -> {
            synchronized (resource) {
                log.info("A get resource lock")
                try {
                    log.info("A begin wait")
                    resource.wait()
                    log.info("A end wait")
                } catch (InterruptedException e) {
                    log.warn(e.getMessage())
                }
            }

        })
        Thread B = new Thread(() -> {
            synchronized (resource) {
                log.info("B get resource lock")
                try {
                    log.info("B begin wait")
                    resource.wait()
                    log.info("B end wait")
                } catch (InterruptedException e) {
                    log.warn(e.getMessage())
                }
            }

        })
        Thread C = new Thread(() -> {
            synchronized (resource) {
                log.info("C begin notify all")
                resource.notifyAll()
            }

        })

        and:
        A.start()
        B.start()
        TimeUnit.SECONDS.sleep(1)
        C.start()
        A.join()
        B.join()
        C.join()
        log.info("main over")

        then:
        true
    }

}
