package com.yd.java.concurrency.task;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        public Indexer(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    File file = queue.take();
                    System.out.println("Indexer index file :" + file.getPath());
                } catch (InterruptedException e) {
                }
            }
        }
    }