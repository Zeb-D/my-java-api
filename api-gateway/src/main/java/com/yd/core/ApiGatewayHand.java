package com.yd.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Yd on  2017-12-05
 * @Description：
 **/
public class ApiGatewayHand implements InitializingBean, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ApiGatewayHand.class);

    private static final String METHOD = "method";
    private static final String PARAMS = "params";
    final ParameterNameDiscoverer parameterNameDiscoverer;
    ApiStore apiStore;

    public ApiGatewayHand() {
        parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    //post请求额外处理
    public static Map<String, Object> receivePost(HttpServletRequest request) throws IOException {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //将json字符串转换为json对象
        Map<String, Object> json = JSON.parseObject(sb.toString());
        ;
//        for (Map.Entry<String, Object> m:json.entrySet()) {
////            request.getParameterMap().put(m.getKey(),m.getValue());
//            request.setAttribute(m.getKey(),m.getValue());
//        }
        return json;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        apiStore.loadFromSpringBeans();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        apiStore = new ApiStore(applicationContext);
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        //系统参数验证
        String params = request.getParameter(PARAMS);
        String method = request.getParameter(METHOD);

        if ("POST".equalsIgnoreCase(request.getMethod())) {//post请求时，信息是放在body中
            try {
                Map<String, Object> reqestMap = receivePost(request);
                params = reqestMap.get(PARAMS) != null ? reqestMap.get(PARAMS).toString() : null;
                method = reqestMap.get(METHOD) != null ? reqestMap.get(METHOD).toString() : null;
            } catch (IOException e) {
                log.debug("获取post请求时解析出错啦啦啦" + e.getMessage());
            }
        }

        Object result;
        ApiStore.ApiRunable apiRunable = null;
        try {
            apiRunable = sysParamsValdate(request);
            log.info("请求接口[]={" + method + "}，参数={" + params + "}");
            Object[] args = buildParams(apiRunable, params, request, response);
            result = apiRunable.run(args);
        } catch (ApiException e) {
            response.setStatus(500);//封装异常
            log.error("请求接口={" + method + "}异常，参数={" + params + "}", e);
            result = handleError(e);
        } catch (IllegalAccessException e) {
            response.setStatus(500);//封装异常
            log.error("请求接口={" + method + "}异常，参数={" + params + "}", e);
            result = handleError(e);
        } catch (InvocationTargetException e) {
            response.setStatus(500);//封装异常
            log.error("请求接口={" + method + "}异常，参数={" + params + "}", e.getTargetException());
            result = handleError(e);
        } catch (Exception e) {
            response.setStatus(500);//封装异常
            log.error("请求接口={" + method + "}异常，参数={" + params + "}", e);
            result = handleError(e);
        }

        //统一返回结果
        returnResult(result, response);
    }

    //输出结果
    private void returnResult(Object result, HttpServletResponse response) {
        try {
            String json = JSON.toJSONString(result);
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("expires", 0);
            if (json != null) {
                response.getWriter().write(json);
            }
        } catch (IOException e) {
            log.error("服务器响应异常", e);
            throw new RuntimeException(e);
        }

    }

    private Object handleError(Throwable throwable) {
        String code = "";
        String msg = "";
        if (throwable instanceof ApiException) {
            code = "001";
            msg = throwable.getMessage();
        } else {//扩展其他异常
            code = "002";
            msg = throwable.getMessage();
        }

        Map<String, Object> result = new HashMap();
        result.put("code", code);
        result.put("msg", msg);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        throwable.printStackTrace(stream);
//        result.put("stack",out.toString());
        return result;
    }

    private Object[] buildParams(ApiStore.ApiRunable apiRunable, String params, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = JSON.parseObject(params, Map.class);
        } catch (Exception e) {
            throw new ApiException("调用失败:JSON 字符串转格式错误，请检查params参数");
        }
        if (map == null) {
            map = new HashMap();
        }

        Method method = apiRunable.getTargetMethod();//javassist
        List<String> paramNames = Arrays.asList(parameterNameDiscoverer.getParameterNames(method));
        //goods ,id
        Class<?>[] paramTypes = method.getParameterTypes();

//        for (Map.Entry<String, Object> m : map.entrySet()) {
//            if (!paramNames.contains(m.getKey())) {
        if (!map.keySet().containsAll(paramNames)) {
            throw new ApiException("调用失败：接口参数中不满足" + paramNames + "的格式");
        }
//        }

        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (map.containsKey(paramNames.get(i))) {
                try {
                    args[i] = convertJsonToBean(map.get(paramNames.get(i)), paramTypes[i]);
                } catch (Exception e) {
                    throw new ApiException("调用失败：指定参数格式错误或者值错误'" + paramNames.get(i) + "'对应的格式为'" + paramTypes[i] + "'\n"
                            + e.getMessage());
                }
            } else {
                args[i] = null;
            }
        }


        return args;
    }

    private <T> Object convertJsonToBean(Object val, Class<T> targetClass) {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            if (val.toString().matches("[0-9]+")) {
                result = new Date(Long.parseLong(val.toString()));
            } else {
                throw new ApiException("日期必须是长整型的时间戳");
            }
        } else if (String.class.equals(targetClass)) {
            if (val instanceof String) {
                result = val;
            } else {
                throw new ApiException("转换目标类型必须为字符串");
            }
        } else {
//            JSON.parseObject(val.toString(), targetClass);
            result = JSON.toJavaObject((JSONObject) val, targetClass);
        }

        return result;
    }

    private ApiStore.ApiRunable sysParamsValdate(HttpServletRequest request) {
        //系统参数验证
        String params = request.getParameter(PARAMS);
        String apiName = request.getParameter(METHOD);

        ApiStore.ApiRunable apiRunable;
        if (apiName == null || "".equals(apiName.trim())) {
            throw new ApiException("调用失败：参数'method'为空");
        } else if (params == null) {
            throw new ApiException("调用失败：参数'params'为空");
        } else if ((apiRunable = apiStore.findApiRunable(apiName)) == null) {
            throw new ApiException("调用失败：指定API不存在" + apiName);
        }
        //可以进行其他的签名参数 等等

        return apiRunable;
    }
}
