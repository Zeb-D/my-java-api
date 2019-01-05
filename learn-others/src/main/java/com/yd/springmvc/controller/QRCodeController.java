package com.yd.springmvc.controller;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 二维码生成器
 *
 * @author Yd on  2018-06-28
 * @description
 **/
@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
    private static final long serialVersionUID = 1357779219336485986L;

    @RequestMapping(value = "/generateCode", method = {RequestMethod.GET})
    public void generateCode(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).stream();

        response.setContentType("image/png");
        response.setContentLength(out.size());
        OutputStream outStream = response.getOutputStream();

        outStream.write(out.toByteArray());

        outStream.flush();
        outStream.close();
    }

}
