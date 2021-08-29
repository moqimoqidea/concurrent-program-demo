package com.moqi

import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

@Slf4j
class ThreadTest extends Specification {

    private static int number = 0

    def 'extend Thread class and override run method'() {
        when:
        new MyThread().start()
        TimeUnit.SECONDS.sleep(1)

        then:
        number == 1
    }

    private static class MyThread extends Thread {
        @Override
        void run() {
            log.info('MyThread Run')
            number = 1
        }
    }

    def 'implements Runnable interface and override run method'() {
        when:
        new Thread(new RunnableTask()).start()
        TimeUnit.SECONDS.sleep(1)

        then:
        number == 2
    }

    private static class RunnableTask implements Runnable {
        @Override
        void run() {
            log.info('RunnableTask Run')
            number = 2
        }
    }

    def 'implements Callable interface and override call method'() {
        when:
        def futureTask = new FutureTask(new CallerTask())
        new Thread(futureTask).start()
        def resultNumber = futureTask.get()

        then:
        resultNumber == 3
    }

    private static class CallerTask implements Callable<Integer> {
        @Override
        Integer call() {
            log.info('CallerTask Run')
            return 3
        }
    }

}
