package com.yd.core;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * API IOC 大仓库
 *
 * @author Yd on  2017-12-05
 * @Description：
 **/
public class ApiStore {
    private ApplicationContext applicationContext;
    //Api 保存的地方
    private HashMap<String, ApiRunable> apiMap = new HashMap<String, ApiRunable>();

    public ApiStore(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext);
        this.applicationContext = applicationContext;
    }

    //基于Spring IOC的Bean查找对应Apiz中的方法
    public void loadFromSpringBeans() {
        //IOC 所有的Bean
        //Spring IOC的Bean扫描
        String[] names = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        //反射
        for (String name : names) {
            type = applicationContext.getType(name);
            for (Method method : type.getDeclaredMethods()) {
                APIMapping apiMapping = method.getAnnotation(APIMapping.class);
                if (apiMapping != null) {
                    addApiItem(apiMapping, name, method);
                }
            }
        }

    }

    public ApiRunable findApiRunable(String name) {
        return apiMap.get(name);
    }

    public Boolean containsApi(String name) {
        return apiMap.containsKey(name);
    }

    public List<ApiRunable> findApiRunables(String name) {
        if (name == null) {
            throw new IllegalArgumentException("api name must not null");
        }
        List<ApiRunable> apiRunables = new ArrayList<ApiRunable>(20);
        for (ApiRunable apiRunable : apiMap.values()) {
            if (apiRunable.apiName.equals(name)) {
                apiRunables.add(apiRunable);
            }
        }
        return apiRunables;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //添加api
    private void addApiItem(APIMapping apiMapping, String name, Method method) {
        //检测API结构
        for (Class<?> aClass : method.getParameterTypes()) {
            for (Field field : aClass.getDeclaredFields()) {
                if (Object.class.equals(field.getType())) {//参数是组合实体的话，不允许有Object字段
                    throw new ApiException("接口不符合规范，参数实体结构不规范");
                }
            }

        }
        ApiRunable apiRunable = new ApiRunable();
        apiRunable.apiName = apiMapping.value();
        apiRunable.targetMethod = method;
        apiRunable.targetName = name;
        apiMap.put(apiMapping.value(), apiRunable);
    }

    public class ApiRunable {
        String apiName;//mq.api.goods.add
        String targetName;//Ioc Bean 名称
        Object target;//GoodsServiceIMPL
        Method targetMethod;//目标方法 add

        //多线程安全问题
        public Object run(Object... args) throws InvocationTargetException, IllegalAccessException {
            if (target == null) {
                target = applicationContext.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public Class<?>[] getParamTypes() {
            return targetMethod.getParameterTypes();
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

        public void setTargetMethod(Method targetMethod) {
            this.targetMethod = targetMethod;
        }
    }

}
