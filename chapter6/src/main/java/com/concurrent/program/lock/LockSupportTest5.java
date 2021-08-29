package com.concurrent.program.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用带 this 重载的 park 方法可以多看到这一句
 * parking to wait for  <0x0000000795855c08> (a com.concurrent.program.lock.LockSupportTest5)
 */
public class LockSupportTest5 {

    public void testPark() {
        LockSupport.park(this);
    }

    /**
     * LockSupport.park();
     *
     * "main" #1 prio=5 os_prio=31 tid=0x00007f9af980f800 nid=0xe03 waiting on condition [0x000070000e81f000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
     * 	at com.concurrent.program.lock.LockSupportTest5.testPark(LockSupportTest5.java:8)
     * 	at com.concurrent.program.lock.LockSupportTest5.main(LockSupportTest5.java:17)
     *
     * 	LockSupport.park(this);
     *
     * 	"main" #1 prio=5 os_prio=31 tid=0x00007fd089809000 nid=0x1003 waiting on condition [0x0000700006ec0000]
     *    java.lang.Thread.State: WAITING (parking)
     * 	at sun.misc.Unsafe.park(Native Method)
     * 	- parking to wait for  <0x0000000795855c08> (a com.concurrent.program.lock.LockSupportTest5)
     * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     * 	at com.concurrent.program.lock.LockSupportTest5.testPark(LockSupportTest5.java:8)
     * 	at com.concurrent.program.lock.LockSupportTest5.main(LockSupportTest5.java:23)
     */
    public static void main(String[] args) {
        LockSupportTest5 test5 = new LockSupportTest5();
        test5.testPark();
    }

}
