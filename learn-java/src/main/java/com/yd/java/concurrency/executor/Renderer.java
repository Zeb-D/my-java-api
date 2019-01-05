package com.yd.java.concurrency.executor;

import com.yd.java.concurrency.PreLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Yd on  2018-02-12
 * @description 使用CompletetionService 渲染可用的页面元素
 **/
public class Renderer {
    private final ExecutorService executorService;

    public Renderer(ExecutorService service) {
        this.executorService = service;
    }

    void renderPage(CharSequence sequence) {
        final List<ImageInfo> imageInfos = scanForImageInfo(sequence);
        CompletionService<ImageData> imageDataCompletionService = new ExecutorCompletionService<ImageData>(executorService);
        for (ImageInfo imageInfo : imageInfos)
            imageDataCompletionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadIamge();
                }
            });

        renderText(sequence);

        try {
            for (int i = 0; i < imageInfos.size(); i++) {
                Future<ImageData> future = imageDataCompletionService.take();
                ImageData imageData = future.get();
                renderIamge(imageData);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw PreLoader.launderThrowable(e.getCause());
        }

    }

    private void renderIamge(ImageData imageData) {
        //将图片渲染在 html TODO

    }

    private void renderText(CharSequence sequence) {
        //渲染 页面 TODO

    }

    private List<ImageInfo> scanForImageInfo(CharSequence html) {
        //在页面上读取到页面标签转化成 ImageInfo-List
        return new ArrayList<ImageInfo>();
    }

}
