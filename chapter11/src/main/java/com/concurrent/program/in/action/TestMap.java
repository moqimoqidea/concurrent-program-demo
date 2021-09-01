package com.concurrent.program.in.action;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Hello world!
 */
public class TestMap {

    // (1)创建map,key为topic,value为设备列表
    static ConcurrentHashMap<String, CopyOnWriteArrayList<String>> map = new ConcurrentHashMap<>();

    /**
     * {"topic1":["device11","device22"],"topic2":["device111","device222"]}
     * {"topic1":["device11","device22"],"topic2":["device111","device222"]}
     * {"topic1":["device11","device22"],"topic2":["device111","device222"]}
     */
    public static void main(String[] args) {
        // (2)进入直播间topic1 线程one
        Thread threadOne = new Thread(() -> {
            CopyOnWriteArrayList<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device1");
            list1.add("device2");

            map.put("topic1", list1);
            System.out.println(JSON.toJSONString(map));
        });
        // (3)进入直播间topic1 线程two
        Thread threadTwo = new Thread(() -> {
            CopyOnWriteArrayList<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device11");
            list1.add("device22");

            map.put("topic1", list1);

            System.out.println(JSON.toJSONString(map));
        });

        // (4)进入直播间topic2 线程three
        Thread threadThree = new Thread(() -> {
            CopyOnWriteArrayList<String> list1 = new CopyOnWriteArrayList<>();
            list1.add("device111");
            list1.add("device222");

            map.put("topic2", list1);

            System.out.println(JSON.toJSONString(map));
        });

        // (5)启动线程
        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }
}
