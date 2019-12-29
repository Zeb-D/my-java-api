package com.yd.feign;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import java.util.Map;

/**
 * @author Yd on  2018-04-19
 * @description
 **/
public class ApolloServiceTest {
    public static void main(String[] args) {
        ApolloService apolloService = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
//                .requestInterceptor(new BasicAuthRequestInterceptor("username", "password"))
                .target(ApolloService.class, " http://erp2.test.pg.com.cn:7080");
        System.out.println(apolloService.queryConfigs("SampleApp","application"));

    }
}

@Headers({"Content-Type: application/json;charset=UTF-8"})
interface ApolloService{
    @RequestLine("GET /configs/{appid}/default/{namespaceName}?ip=192.168.2.47")
    Map queryConfigs(@Param(value = "appid") String appid, @Param(value = "namespaceName") String namespaceName);//查询流程定义
}
