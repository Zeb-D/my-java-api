package com.yd.mq.activemq.entity;

/**
 * @author Yd on  2017-12-15
 * @Description：
 **/
public class User2 extends User{
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    protected Integer id;

    public User2(Integer id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return "User2{" +
                "id=" + id +
                '}';
    }
}
