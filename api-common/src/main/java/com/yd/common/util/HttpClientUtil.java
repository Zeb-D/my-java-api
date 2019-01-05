package com.yd.common.util;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yd.httpClient.Client;
import com.yd.httpClient.ClientRequest;
import com.yd.httpClient.ClientResult;
import com.yd.httpClient.HttpStatus;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Yd on  2018-02-08
 * @description http-cliet 请求工具类
 **/
public class HttpClientUtil {


    /**
     * http-Get请求
     *
     * @param url  -请求Get接口
     * @param params - 请求参数
     * @return
     */
    public static ClientResult get(String url, Map<String, String> params) {
        Client client = new Client(url + param(params));
        ClientRequest request = new ClientRequest("");
        return client.build(request).get();
    }

    public static ClientResult get(String url, Map<String, String> params, Map<String, String> header) {
        Client client = new Client(url + param(params));
        ClientRequest request = new ClientRequest("");
        request.setHeaders(header);
        return client.build(request).get();
    }

    public static ClientResult get(String url) {
        Client client = new Client(url);
        ClientRequest request = new ClientRequest("");
        return client.build(request).get();
    }

    public static ClientResult post(String url, Map<String, String> body) {
        Client client = new Client(url);
        ClientRequest request = new ClientRequest("");
        request.setParams(body);
        return client.build(request).post();
    }

    public static ClientResult post(String url, Map<String, String> body, Map<String, String> header) {
        Client client = new Client(url);
        ClientRequest request = new ClientRequest("");
        request.setHeaders(header);
        request.setParams(body);
        return client.build(request).post();
    }

    public static ClientResult post(String url) {
        Client client = new Client(url);
        ClientRequest request = new ClientRequest("");
        return client.build(request).post();
    }


    public static String param(Map<String, String> args) {
        if (args == null || args.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        return "?" + sb.substring(0, sb.length() - 1);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String GET_ORGLIST_URL = "http://restapi.amap.com/v3/config/district?subdistrict=3&key=89e51c7379bc5544035285a938555498";
        String userName = "admin";
        String password = "test";
        String auth = userName + ":" + password;
        String encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Authorization", authHeader);
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
//        header.put("User-Agent",userAgent);
        String cookie="FLOWABLE_REMEMBER_ME=K2FERlY2YWNDTmRKbHBlQ1RHKzhIQT09OjcrU0ZMaWNZbENQcERCaG8wRXM1dmc9PQ";
        header.put("Cookie",cookie);

        ClientResult clientResult = get(GET_ORGLIST_URL, null, header);
        if (clientResult != null) {
            HttpStatus status = clientResult.getStatus();
            if (HttpStatus.OK.equals(status)) {
                System.out.println("http-client result-data :\n " + JSON.toJSON(clientResult.getResult()));
            }
        }


    }
}
