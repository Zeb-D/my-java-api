package com.yd.objectPool;


import org.apache.commons.pool2.*;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by Zeb灬D on 2018/2/7
 * Description:
 */
public class PoolTest {
    public static void main(String[] args) {
        PooledObjectFactory<String> stringFactory = new BasePooledObjectFactory<String>() {
            @Override
            public String create() throws Exception {
                return "aa" + System.currentTimeMillis();
            }

            @Override
            public PooledObject wrap(String obj) {
                return new DefaultPooledObject<String>(obj);
            }

            @Override
            public void passivateObject(PooledObject<String> p) throws Exception {
                super.passivateObject(p);
            }
        };
        ObjectPool<String> safePool = new GenericObjectPool(PoolUtils.synchronizedPooledFactory(stringFactory));
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String s = safePool.borrowObject();
                System.out.println(s);
                safePool.returnObject(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}