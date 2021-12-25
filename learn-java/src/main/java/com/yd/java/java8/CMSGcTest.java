package com.yd.java.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 1.初始标记：为了收集应用程序的对象引用需要暂停应用程序线程，该阶段完成后，应用程序线程再次启动。
 * 2.并发标记：从第一阶段收集到的对象引用开始，遍历所有其他的对象引用。
 * 3.并发预清理：改变当运行第二阶段时，由应用程序线程产生的对象引用，以更新第二阶段的结果。
 * 4.重标记：由于第三阶段是并发的，对象引用可能会发生进一步改变。因此，应用程序线程会再一次
 * 被暂停以更新这些变化，并且在进行实际的清理之前确保一个正确的对象引用视图。
 * 这一阶段十分重要，因为必须避免收集到仍被引用的对象。
 * 5.并发清理：所有不再被应用的对象将从堆里清除掉。
 * 6.并发重置：收集器做一些收尾的工作，以便下一次GC周期能有一个干净的状态。
 * <p>
 * 其中4个阶段(名字以Concurrent开始的)与实际的应用程序是并发执行的，
 * 而其他2个阶段需要暂停应用程序线程(STW).
 * </p>
 * <p>
 * java -Xms100m -Xmx100m -Xmn50m -XX:+PrintGCDetails
 * -XX:+UseConcMarkSweepGC -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
 * -XX:+PrintTenuringDistribution CMSGcTest
 * </p>
 *
 * @author created by Zeb灬D on 2021-12-25 16:54
 */
public class CMSGcTest {
    private static final int _10MB = 10 * 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        test();
    }

    /**
     * VM arg： -Xms100m(设置最大堆内存) -Xmx100m(设置初始堆内存)
     * -Xmn50m(设置新生代大小)
     * -XX:+PrintGCDetails(打印GC日志详细信息)
     * -XX:+UseConcMarkSweepGC (采用 cms gc算法)
     * -XX:+UseParNewGC (新生代采用并行GC方式,
     * 高版本的jdk使用了UseConcMarkSweepGC参数时 这个参数会自动开启)
     * -XX:SurvivorRatio=8 (新生代eden区与survivor区空间比例8:1,
     * eden:fromsurvivor:tosurvivor -->8:1:1)
     * -XX:MaxTenuringThreshold=1 (用于控制对象能经历多少次
     * Minor GC(young gc)才晋升到老年代,默认15次)
     * -XX:+PrintTenuringDistribution(输出survivor区幸存对象的年龄分布)
     * -XX:CMSInitiatingOccupancyFraction=68 *(设置老年代空间使用率多少时触发第一次cms *gc,默认68%)
     *
     * @throws InterruptedException
     */
    public static void test() throws InterruptedException {
        List<byte[]> list = new ArrayList<>();
        for (int n = 1; n < 8; n++) {
            byte[] alloc = new byte[_10MB];
            list.add(alloc);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
