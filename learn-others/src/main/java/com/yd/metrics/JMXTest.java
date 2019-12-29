package com.yd.metrics;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zeb灬D on 2017/11/27
 * Description:
 */
public class JMXTest {
    public static void main(String[] args) {
        MyMBean myXMBean = new My();
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(myXMBean, new ObjectName("myapp:type=webserver,name=Port 8080"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(myXMBean.getName());
            }
        };

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);

    }
}