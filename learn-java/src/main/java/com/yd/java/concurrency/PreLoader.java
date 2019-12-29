package com.yd.java.concurrency;

import com.alibaba.fastjson.JSON;
import com.yd.httpClient.ClientResult;
import com.yd.httpClient.HttpStatus;
import com.yd.common.util.HttpClientUtil;
import com.yd.entity.ProductInfo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Yd on  2018-02-07
 * @description
 **/
public class PreLoader {
       private final FutureTask<ProductInfo> futureTask = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
           @Override
           public ProductInfo call() throws Exception {
               return loadProductInfo();
           }
       });


       private final Thread thread = new Thread(futureTask);

       public void start(){
           this.thread.start();
       }

       public ProductInfo get() throws ExecutionException, InterruptedException {
           return futureTask.get();
       }

       public static RuntimeException launderThrowable(Throwable t){
           if (t instanceof RuntimeException){
               return (RuntimeException) t;
           }else if (t instanceof Error){
               throw  (Error)t;
           }else {
                throw new IllegalStateException("uncheked exception",t);
           }
       }

       public static ProductInfo loadProductInfo(){
           String GET_ORGLIST_URL = "http://b2b.test.pg.com.cn/base/pgsearch.php?data=acc_dc&format=json";
           ClientResult clientResult = HttpClientUtil.get(GET_ORGLIST_URL, null);
           if (clientResult != null) {
               HttpStatus status = clientResult.getStatus();
               if (HttpStatus.OK.equals(status)) {
                   System.out.println("orgMapList : " + JSON.toJSON(clientResult.getResult()));
                   List<ProductInfo> productInfoList = JSON.parseArray(clientResult.getResult(),ProductInfo.class);
                   return productInfoList.get(0);
               }
           }
           return null;
       }
}
