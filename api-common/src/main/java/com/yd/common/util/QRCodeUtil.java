package com.yd.common.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Yd on  2018-06-28
 * @description
 **/
public class QRCodeUtil {
    //根据链接生成JPG文件
    public static void generateCode(String url) throws IOException {
        ByteArrayOutputStream output = QRCode.from(url).to(ImageType.PNG).stream();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/QR.jpg"));
        fileOutputStream.write(output.toByteArray());
        output.close();
        fileOutputStream.close();
    }

    public static void main(String[] args) throws IOException {
        generateCode("https://github.com/Zeb-D/distributed-id");
    }

}
