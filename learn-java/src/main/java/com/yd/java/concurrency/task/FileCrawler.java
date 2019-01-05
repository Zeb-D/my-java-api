package com.yd.java.concurrency.task;


import com.yd.common.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    public FileCrawler(File root, String fileNameLike) {
        this.fileQueue = new ArrayBlockingQueue<File>(10);
        this.fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return FileUtil.assertIsFileByLike(pathname, fileNameLike);
            }
        };
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawel(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //传入一个跟路径，再判断是否是目录则 递归该目录，否则put到队列中
    private void crawel(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File file : entries) {
                if (file.isDirectory()) {
                    crawel(file);
                } else if (!fileQueue.contains(file)) {
                    System.out.println("FileCrawler crawle file: " + file.getName());
                    fileQueue.put(file);
                }
            }
        }
    }
}