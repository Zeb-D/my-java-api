package com.yd.java.concurrency.deadLock;

import java.util.HashSet;
import java.util.Set;

/**
 * 使用开放调用来避免对象之间的死锁
 *
 * @author Yd on  2018-05-09
 * @description
 **/
public class TaxiDispatcher {

    class Taxi {
        private final Dispatcher dispatcher;
        private Point location, destination;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            boolean reachedDestination;
            synchronized (this) {//避免协作对象之间的死锁
                this.location = location;
                reachedDestination = location.equals(destination);
            }
            if (reachedDestination)
                dispatcher.notifyAvailable(this);
        }
    }

    class Dispatcher {
        private final Set<Taxi> taxis, availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<>();
            availableTaxis = new HashSet<>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }


        public synchronized void printLocation() {
            Set<Taxi> copy;
            synchronized (this) {
                copy = new HashSet<>(taxis);//采用复制拷贝 打印实时位置
            }
            copy.stream().forEach(System.out::println);
        }
    }

    class Point {
        private int x, y;
    }
}
