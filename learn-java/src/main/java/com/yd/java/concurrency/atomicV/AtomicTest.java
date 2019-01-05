package com.yd.java.concurrency.atomicV;

import java.awt.event.ActionListener;
import java.sql.PseudoColumnUsage;
import java.util.concurrent.atomic.*;

/**
 * 原子变量测试，非阻塞算法如CAS、链接加载/存储条件
 * 原子变量提供与volatile 类型变量相同的内存语义，还额外支出原子更新，使他更加理想地用于计数器、系列发生器和统计数据收集等
 * 另外比基于锁的方案更具有伸缩性
 * @author Yd on  2018-05-12
 * @description
 **/
public class AtomicTest {
    //1、计量器
    AtomicInteger atomicInteger = new AtomicInteger();
    AtomicLong atomicLong = new AtomicLong();
    AtomicBoolean atomicBoolean = new AtomicBoolean();
    AtomicReference atomicReference = new AtomicReference();
    //2、域更新器

    //3、数组

    //4、复合变量

    //ABA 问题，解决方案：更新一对值
    AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(new Integer(1),1);
    AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<>(new Integer(1),true);
}
