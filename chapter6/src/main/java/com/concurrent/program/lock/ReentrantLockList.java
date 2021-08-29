package com.concurrent.program.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2020-08-29
 */
public class ReentrantLockList {
    // 线程不安全的list
    private final ArrayList<String> array = new ArrayList<>();
    // 独占锁
    private final ReentrantLock lock = new ReentrantLock();

    // 添加元素
    public void add(String e) {

        lock.lock();
        try {
            array.add(e);

        } finally {
            lock.unlock();

        }
    }

    // 删元素
    public void remove(String e) {

        lock.lock();
        try {
            array.remove(e);

        } finally {
            lock.unlock();

        }
    }

    // 获取数据
    public String get(int index) {

        lock.lock();
        try {
            return array.get(index);

        } finally {
            lock.unlock();

        }
    }
}
