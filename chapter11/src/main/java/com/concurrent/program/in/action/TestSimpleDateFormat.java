package com.concurrent.program.in.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created on 2020-08-29
 */
public class TestSimpleDateFormat {

    // (1)创建单例实例
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Wed Dec 13 15:17:27 CST 2017
     * Exception in thread "Thread-23" java.lang.NumberFormatException: For input string: ".2720174E"
     * 	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:2043)
     * 	at sun.misc.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
     * 	at java.lang.Double.parseDouble(Double.java:538)
     * 	at java.text.DigitList.getDouble(DigitList.java:169)
     * 	at java.text.DecimalFormat.parse(DecimalFormat.java:2089)
     * 	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
     * 	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
     * 	at java.text.DateFormat.parse(DateFormat.java:364)
     * 	at com.concurrent.program.in.action.TestSimpleDateFormat.lambda$main$0(TestSimpleDateFormat.java:19)
     * 	at java.lang.Thread.run(Thread.java:748)
     * Exception in thread "Thread-41" Exception in thread "Thread-39" Exception in thread "Thread-38" Exception in thread "Thread-40" java.lang.NumberFormatException: empty String
     * Wed Dec 13 15:17:27 CST 2017
     */
    public static void main(String[] args) {
        // (2)创建多个线程，并启动
        for (int i = 0; i < 100; ++i) {
            Thread thread = new Thread(() -> {
                try {// (3)使用单例日期实例解析文本
                    System.out.println(sdf.parse("2017-12-13 15:17:27"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            thread.start();// (4)启动线程
        }
    }

}
