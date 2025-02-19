# 《Java并发编程之美》

作者：翟陆续（加多），某大型互联网公司资深开发工程师，并发编程网编辑；热衷并发编程，微服务架构设计，中间件基础设施，著作[《深度剖析Apache Dubbo技术内幕》](https://github.com/zhailuxu/Dubbo-Demo/blob/master/README.md)、[《Java并发编程之美》](https://github.com/zhailuxu/concurrent-program-demo/blob/master/README.md)、[《Java异步编程实战》](https://github.com/zhailuxu/async-program-demo/blob/master/README.md) ,微信公众号：技术原始积累。

## 前言
并发编程相比 Java 中其他知识点学习门槛较高，从而导致很多人望而却步。但无论是职场面试，还是高并发/高流量的系统的实现，却都离不开并发编程，于是能够真正掌握并发编程的人成为了市场迫切需求的人才。

## 学习并发编程
Java并发编程作为Java技术栈中的一块顶梁柱，其学习成本还是比较大的，很多人学习起来感到没有头绪，感觉无从下手？那么学习并发编程是否有一些技巧在里面那？

其实为了让开发者从Java并发编程的苦海中解脱出来，大神Doug Lea特意为Java开发人员做了一件事情，那就是在JDK中提供了Java 并发包（JUC），该包提供了常用的并发相关的工具类，比如锁、并发安全的队列、并发安全的列表、线程池、线程同步器等。有了JUC包，开发人员编写并发程序时候，不在那么吃力了，但是工具虽好，但是如果你对其原理不了解，还是很容易犯错，也就是不懂原理，多吃亏。

比如最简单的并发安全的队列LinkedBlockingQueue，其offer与put方法的区别，什么时候用offer，什么时候用put,你可能在某个时间点知道，但是过一段时间你就可能会忘记，但是如果你对其原理了解，翻看下代码，就可以知道offer是非阻塞的，队列满了了，就丢弃当前元素；put是阻塞的，队列满则会挂起当前线程进行等待。

比如使用线程池时候，意在让调用线程把任务放入线程池后直接返回，让任务异步执行。如果你没注意如果拒绝策略为CallerRunsPolicy，并且不知道线程池队列满后，拒绝策略的执行是当前调用线程，而你在拒绝策略里面做了很耗时的动作，则当前调用线程就会被阻塞很久。

比如当你使用Executors.newFixedThreadPool等创建线程池时候，如果你不知道其内部是创建了一个无界队列，那么当大量任务被投递到创建的线程池里面后，可能就会造成OOM。另外当你不知道线程池里面的线程是用户线程或者是deamon线程时候，并且没有调用线程池的shutdown方法，则创建线程池的应用可能就不能优雅退出。

上面列出了几个例子，意在说明虽然有了JUC包，其实还有很多实例可以说明，不懂原理，多吃亏。那么我们为何不能花些时间来研究下JUC包重要组件实现原理那？有人可能会说，我有去看啊，但是看不懂啊？每个组件里面涉及的知识太多了。没错，JUC包的实现确实是并发编程基础知识搭建起来的，所以在看组件原理实现前，大家应该先去把并发相关的基础学好了，并且由浅入深的进行研究。

比如最基础的线程基础操作原语原语notify/wait系列，join方法，sleep方法，yield方法，线程中断的理解，死锁的产生与避免，什么时候用户线程与deamon线程，什么是伪共享以及如何解决？Java内存模型是什么？什么是内存不可见性以及如何避免？volatile与Synchronized内存语义是什么，用来解决什么问题？什么是CAS操作，其出现为了解决什么问题，其本身存在什么问题，ABA问题是什么？什么是指令重排序，如何避免？什么是原子性操作？什么是独占锁，共享锁，公平锁，非公平锁？

如果你已经掌握了上面基础，那么你可以先看JUC包中最简单的基于CAS无锁实现的原子性操作类比如AtomicLong的实现,你会疑问其中的变量value为何使用volatile修饰（多线程下保证内存可见性）？然后大家可以看JDK8新增原子操作类LongAdder，在非常高的并发请求下AtomicLong的性能会受影响，虽然AtomicLong使用CAS但是CAS失败后还是通过无限循环的自旋锁不断尝试的，在高并发下N多线程同时去操作一个变量会造成大量线程CAS失败然后处于自旋状态，这大大浪费了cpu资源，降低了并发性。那么既然AtomicLong性能由于过多线程同时去竞争一个变量的更新而降低的，那么如果把一个变量分解为多个变量，让同样多的线程去竞争多个资源那么性能问题不就解决了？是的，JDK8提供的LongAdder就是这个思路。看到这里大家或许会眼前一亮，原来如此。



然后可以看比较简单的并发安全的基于写时拷贝的CopyOnWriteArrayList的实现，以及探究其迭代器的弱一致性的实现原理（也就是写时拷贝），虽然其实现里面用到了独占锁，但是可以先不用深入锁的细节。

如果你已经掌握了上面内容，那么下面就如核心环节，也就是对JUC包中锁的研究，一开始你肯定要先把LockSupport类研究透，其是锁中让线程挂起与唤醒的基础设施。由于锁是基于AQS（AbstractQueuedSynchronizer）实现的，所以你肯定要先把AQS搞清楚了，你会发现AQS 中维持了一个单一的状态信息 state, 可以通过 getState,setState,compareAndSetState 函数修改其值；对于 ReentrantLock 的实现来说，state 可以用来表示当前线程获取锁的可重入次数；对应读写锁 ReentrantReadWriteLock 来说 state 的高 16 位表示读状态也就是获取该读锁的次数，低 16 位表示获取到写锁的线程的可重入次数；对于 semaphore 来说 state 用来表示当前可用信号的个数；对于 FutureTask 来说，state 用来表示任务状态（例如还没开始，运行，完成，取消）；对应 CountDownLatch 和 CyclicBarrie 来说 state 用来表示计数器当前的值。

你会知道AQS 有个内部类 ConditionObject 是用来结合锁实现线程同步，ConditionObject 可以直接访问 AQS 对象内部的变量，比如 state 状态值和 AQS 队列；ConditionObject 是条件变量，每个条件变量对应着一个条件队列 (单向链表队列)，用来存放调用条件变量的 await() 方法后被阻塞的线程。

你会知道 AQS 类并没有提供可用的 tryAcquire 和 tryRelease，正如 AQS 是锁阻塞和同步器的基础框架，tryAcquire 和 tryRelease 需要有具体的子类来实现。子类在实现 tryAcquire 和 tryRelease 时候要根据具体场景使用 CAS 算法尝试修改状态值 state, 成功则返回 true, 否者返回 false。子类还需要定义在调用 acquire 和 release 方法时候 state 状态值的增减代表什么含义。

比如继承自 AQS 实现的独占锁 ReentrantLock，定义当 status 为 0 的时候标示锁空闲，为 1 的时候标示锁已经被占用，在重写 tryAcquire 时候，内部需要使用 CAS 算法看当前 status 是否为 0，如果为 0 则使用 CAS 设置为 1，并设置当前线程的持有者为当前线程，并返回 true, 如果 CAS 失败则 返回 false。

比如继承自 AQS 实现的独占锁实现 tryRelease 时候，内部需要使用 CAS 算法把当前 status 值从 1 修改为 0，并设置当前锁的持有者为 null，然后返回 true, 如果 cas 失败则返回 false。

AQS知道什么东东了，然后锁的话肯定是先看最简单的独占锁ReentrantLock了，你可以先画出其类图结构，看看其有哪些变量和方法，你会发现其分公平锁与独占锁之分（回顾基础篇？），类图中状态值state代表线程获取该锁的可重入次数，当一个线程第一次获取该锁时候state的值为0，该线程第二次获取后该锁状态值为1，这就是可重入次数。然后加大难度，看看读写锁ReentrantReadWriteLock是怎么玩的，当然还有JDK新增的StampedLock别忘了。

等锁研究完了，那么你可以对并发队列进行研究了，其中队列要分基于CAS的无阻塞队列ConcurrentLinkedQueue 和其他的基于锁的阻塞队列，自然先看比较简单的ArrayBlockingQueue，LinkedBlockingQueue，ConcurrentLinkedQueue，别忘了高级的优先级队列PriorityBlockingQueue和延迟队列DelayQueue了。

不对，是漏了一大块了，线程池那？，线程池主要解决两个问题：一方面当执行大量异步任务时候线程池能够提供较好的性能，在不使用线程池的时候，每当需要执行异步任务时候是直接 new一线程进行运行，而线程的创建和销毁是需要开销的。使用线程池时候，线程池里面的线程是可复用的，不会每次执行异步任务时候都重新创建和销毁线程。另一方面线程池提供了一种资源限制和管理的手段，比如可以限制线程的个数，动态新增线程等，每个 ThreadPoolExecutor 也保留了一些基本的统计数据，比如当前线程池完成的任务数目等。

这就完了？不，前面讲解过 Java 中线程池 ThreadPoolExecutor 原理探究，ThreadPoolExecutor 是 Executors 工具类里面的一部分功能，下面来介绍另外一部分功能也就是 ScheduledThreadPoolExecutor 的实现，后者是一个可以指定一定延迟时间后或者定时进行任务调度执行的线程池。

等等，有实践？当然要有，虽然Java并发编程内容很广，但是还是有一些规则可以遵循的，比如线程，线程池创建时候要指定名称以便排查问题，线程池使用完毕记得关闭，ThreadLocal使用完毕记得调用remove清理，SimpleDateFormat是线程不安全的等等。

如果你对上面内容感兴趣，并且对学并发无从下手，那么机会来了，《Java并发编程之美》这本书，就是按照这个思路来编写的，并且该书在京东上被列为10大精选书籍之一
