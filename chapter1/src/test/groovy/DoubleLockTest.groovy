//file:noinspection GroovySynchronizationOnNonFinalField
import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification

@Slf4j
@Ignore
class DoubleLockTest extends Specification {

    private static volatile Object resourceA = new Object()
    private static volatile Object resourceB = new Object()

    // stay in: threadB try get resourceB lock
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
