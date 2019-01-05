package com.yd.camel.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * 使用redis 作为分布式锁
 *
 * @author Yd on  2018-06-11
 * @description
 **/
public class RedisLock {

    /**
     * 请求redis 时间锁，在尝试一定的时间内
     *
     * @param conn
     * @param lockName       锁的key
     * @param acquireTimeout 尝试获取锁的时间
     * @param lockTimeout    锁的过期时间
     * @return
     */
    public String acquireLockWithTimeout(Jedis conn, String lockName, long acquireTimeout, long lockTimeout) {
        String identifier = UUID.randomUUID().toString();   //锁的值
        String lockKey = "lock:" + lockName;     //锁的键
        int lockExpire = (int) (lockTimeout / 1000);     //锁的过期时间

        long end = System.currentTimeMillis() + acquireTimeout;     //尝试获取锁的时限
        while (System.currentTimeMillis() < end) {      //判断是否超过获取锁的时限
            if (conn.setnx(lockKey, identifier) == 1) {  //判断设置锁的值是否成功
                conn.expire(lockKey, lockExpire);   //设置锁的过期时间
                return identifier;          //返回锁的值
            }
            if (conn.ttl(lockKey) == -1) {      //判断锁是否超时
                conn.expire(lockKey, lockExpire);
            }

            try {
                Thread.sleep(1000);    //等待1秒后重新尝试设置锁的值，此处可能与while条件有冲突
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        // 获取锁失败时返回null
        return null;
    }


    public boolean releaseLock(Jedis conn, String lockName, String identifier) {
        String lockKey = "lock:" + lockName;    //锁的键

        while (true) {
            conn.watch(lockKey);    //监视锁的键
            if (identifier.equals(conn.get(lockKey))) {  //判断锁的值是否和加锁时设置的一致，即检查进程是否仍然持有锁
                Transaction trans = conn.multi();
                trans.del(lockKey);             //在Redis事务中释放锁
                List<Object> results = trans.exec();
                if (results == null) {
                    continue;       //事务执行失败后重试（监视的键被修改导致事务失败，重新监视并释放锁）
                }
                return true;
            }

            conn.unwatch();     //解除监视
            break;
        }
        return false;
    }
}
