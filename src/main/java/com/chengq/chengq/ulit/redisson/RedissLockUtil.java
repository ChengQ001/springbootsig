package com.chengq.chengq.ulit.redisson;

import java.util.concurrent.TimeUnit;

public class RedissLockUtil {
    private static DistributedLocker redissLock;

    public static void setLocker(DistributedLocker locker) {
        redissLock = locker;
    }

    public static void lock(String lockKey) {
        redissLock.lock(lockKey);
    }

    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static void lock(String lockKey, int timeout) {
        redissLock.lock(lockKey, timeout);
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    public static void lock(String lockKey, TimeUnit unit, int timeout) {
        redissLock.lock(lockKey, unit, timeout);
    }

    public static void lock(String lockKey, Work work) {
        try {
            lock(lockKey);
            work.work();
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 异常外抛，此处不捕捉
        } finally {
            unlock(lockKey);
        }
    }

    public static <T> T resultLock(String lockKey, ResultWork<T> work) {
        try {
            lock(lockKey);
            return work.work();
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 异常外抛，此处不捕捉
        } finally {
            unlock(lockKey);
        }
    }

    @FunctionalInterface
    public interface Work{
        void work();
    }

    @FunctionalInterface
    public interface ResultWork<T>{
        T work();
    }

}
