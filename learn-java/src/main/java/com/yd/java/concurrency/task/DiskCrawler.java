package com.yd.java.concurrency.task;


import com.yd.common.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yd on  2018-02-07
 * @description 文件搜索：以生产者、消费者形式 共同 来维持一个工作队列（）
 * todo-main测试待通过
 **/
public class DiskCrawler {
    private final static Integer BOUND = 10;//队列数量为10
    private final static Integer N_CONSUMERS = 2;//消费者的数量
    private final static CountDownLatch endGate = new CountDownLatch(BOUND);//主要用于主线程测试

    /**
     * 首先传入一个跟路径 和 一个FileName ，然后启动一个生产者就搜索符合规则的目录，然后启动两个消费者就打印出 它们的文件目录
     *
     * @param fileRoot
     */
    public static void startIndexing(String fileRoot, String fileNameLike) {
        File[] roots = FileUtil.searchFile(fileRoot);
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(BOUND);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return FileUtil.assertIsFileByLike(file, fileNameLike);
            }
        };
        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String rootPath = "D:\\ChromeDownload";
        String fileNameLike = "apache";
        startIndexing(rootPath, fileNameLike);
        Thread.currentThread().setDaemon(true);//todo 任务还未结束，主线程就结束了
    }

}
