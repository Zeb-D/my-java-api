package com.yd.java.concurrency.executor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

/**
 * @author Yd on  2018-02-12
 * @description 图片信息, 从html 页面上的url，返回到一个图片数据
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {
    private String url;
    private ImageData imageData;

    public ImageData downloadIamge() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();
        return new ImageData(x, y, (x + "" + y).getBytes());
    }

}
