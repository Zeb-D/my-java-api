package com.yd.java.concurrency.deadLock;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 开始一个循环，在典型条件下制定死锁
 * @author Yd on  2018-05-09
 * @description 两个银行账户进行加减
 **/
public class DemonstrateDeadLock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITEADIONS = 1000000;

    private static final Object tieLock = new Object();

    public static void main(String[] args) {
        Random random = new Random();
        Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(new BigDecimal(random.nextInt(100)));
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(()->{
                for (int j = 0; j < NUM_ITEADIONS; j++) {
                    transferMoney(accounts[random.nextInt(NUM_ACCOUNTS)],accounts[random.nextInt(NUM_ACCOUNTS)],new BigDecimal(random.nextInt(10)));
                }
            }).start();
        }
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(i+"-->"+accounts[i].getBalance());
        }

    }

    public static void transferMoney(Account fromAcct, Account toAcct, BigDecimal money) {
        class Helper {
            public void transfer() {
                if (fromAcct.getBalance().compareTo(money) < 0) {
                    throw new RuntimeException("fromAcct balance < " + money);
                } else {
                    fromAcct.sub(money);
                    toAcct.add(money);
                }
            }
        }

        int formHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcct);
        //保证顺序上锁
        if (formHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }
        } else if (formHash > toHash) {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

}

class Account {
    private BigDecimal balance;

    public Account(BigDecimal amount) {
        this.balance = amount;
    }

    public void add(BigDecimal money) {
        balance.add(money);
    }

    public void sub(BigDecimal money) {
        balance.subtract(money);
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
