package com.yd.springmvc.handle;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 * 　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
 * IllegalArgumentException—请求报文参数缺失或无效
 * 　　　　　　　　 以及springmvc自定义异常等，如下：
 * SpringMVC自定义异常对应的status code
 * Exception                       HTTP Status Code
 * ConversionNotSupportedException         500 (Internal Server Error)
 * HttpMessageNotWritableException         500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException    404 (Not Found)
 * TypeMismatchException                   400 (Bad Request)
 * HttpMessageNotReadableException         400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 */
@ControllerAdvice
public class CExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /***********************************Java Exception*****************************************/
    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult runtimeExceptionHandler(RuntimeException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseResult nullPointerExceptionHandler(NullPointerException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseResult illegalArgumentExceptionHandler(IllegalArgumentException ex) {

        return doException(ex, ErrorCode.ERROR_INVALID_PARAMS.getCode());
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResponseResult classCastExceptionHandler(ClassCastException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseResult iOExceptionHandler(IOException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResponseResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResponseResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    // 上传文件超过10M异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseResult maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
        return doException(ex, 60002);
    }

    /***********************************spring framwork DefaultException*****************************************/

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseResult requestNotReadable(HttpMessageNotReadableException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResponseResult requestTypeMismatch(TypeMismatchException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseResult requestMissingServletRequest(MissingServletRequestParameterException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseResult request405(HttpRequestMethodNotSupportedException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResponseResult request406(HttpMediaTypeNotAcceptableException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResponseResult server500(RuntimeException ex) {

        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult ExceptionHandler(Exception ex) {
        return doException(ex, ErrorCode.ERROR_DEFAULT.getCode());
    }

    //处理Exception
    public ResponseResult doException(Exception ex, Integer errorCode) {
        JSONObject jsonObject = new JSONObject();
        List<Map> stackList = new ArrayList<>();
        //获取异常堆栈，转换为JSONObject
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            Map<String, String> stack = new HashMap<>();
            stack.put("" + i, ex.getStackTrace()[i].toString());
            stackList.add(stack);
        }

        jsonObject.put("exception", ex.getClass().toString());
        jsonObject.put("ExceptionStartTrace", stackList);
        jsonObject.put("status", errorCode);
        jsonObject.put("msg", ex.getMessage());
        logger.error(jsonObject.toJSONString());

        return ResponseResult.error(errorCode, ex.getMessage());
    }
}
