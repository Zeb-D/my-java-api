package com.yd.objectPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by Zeb灬D on 2018/4/10
 * Description:
 */
public class CarPoolFactory extends BasePooledObjectFactory<Car> {

    static GenericObjectPool<Car> pool = null;

    public synchronized static GenericObjectPool<Car> getInstance() {
        if (pool == null) {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxIdle(-1);
            poolConfig.setMaxTotal(-1);
            poolConfig.setMinIdle(100);
            poolConfig.setLifo(false);
            pool = new GenericObjectPool<Car>(new CarPoolFactory(), poolConfig);
        }
        return pool;
    }

    public static Car borrowObject() throws Exception {
        return CarPoolFactory.getInstance().borrowObject();
    }

    public static void returnObject(Car car) throws Exception {
        CarPoolFactory.getInstance().returnObject(car);
    }

    public static void clear() {
        CarPoolFactory.getInstance().clear();
    }

    public static void main(String[] args) {
        Car car = null;
        try {
            car = CarPoolFactory.borrowObject();
            car.setName("benchi");
            System.out.println(car.toString());
            CarPoolFactory.returnObject(car);
            car = CarPoolFactory.borrowObject();
            System.out.println(car.toString());
            CarPoolFactory.returnObject(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car create() throws Exception {
        return new Car();
    }

    @Override
    public PooledObject<Car> wrap(Car obj) {
        return new DefaultPooledObject<Car>(obj);
    }
}