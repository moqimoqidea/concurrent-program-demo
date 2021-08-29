//file:noinspection GroovySynchronizationOnNonFinalField

import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@Slf4j
class DoubleLockTest extends Specification {

    private static volatile Object resourceA = new Object()
    private static volatile Object resourceB = new Object()

    // stay in: threadB try get resourceB lock
    // 当线程调用共享对象的 wait() 方法时，当前线程只会释放当前共享对象的锁，当前线程持有的其他共享对象的监视器锁并不会被释放。
    @Ignore
    def 'test two lock execute path'() {
        when:
        Thread threadA = new Thread(() -> {
            try {
                synchronized (resourceA) {
                    log.info('threadA get resourceA lock')
                    synchronized (resourceB) {
                        log.info('threadA get resourceB lock')
                        log.info('threadA release resourceA lock')
                        resourceA.wait()
                    }
                }
            } catch (InterruptedException e) {
                log.warn(e.getMessage())
            }
        })

        Thread threadB = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1)
                synchronized (resourceA) {
                    log.info('threadB get resourceA lock')
                    log.info('threadB try get resourceB lock')
                    synchronized (resourceB) {
                        log.info('threadB get resourceB lock')
                        log.info('threadB release resourceA lock')
                        resourceA.wait()
                    }
                }
            } catch (InterruptedException e) {
                log.warn(e.getMessage())
            }
        })

        threadA.start()
        threadB.start()
        threadA.join()
        threadB.join()
        log.info("main over")

        then:
        true
    }

}
