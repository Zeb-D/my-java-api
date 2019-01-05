package com.yd.java.concurrency.executor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yd on  2018-02-12
 * @description 图片数据（从一个herf 获取到图片流，再到相应的html 位置进行渲染）
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {
    //图片位置
    private Integer x;
    private Integer y;
    //图片信息 流
    private byte[] data;

}
