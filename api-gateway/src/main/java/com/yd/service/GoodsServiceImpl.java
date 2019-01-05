package com.yd.service;

import com.yd.core.APIMapping;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author Yd on  2017-12-05
 * @Description：
 **/
@Service
public class GoodsServiceImpl {
    //无缝集成
    @APIMapping("mq.api.goods.add")
    public Goods addGoods(Goods goods,Integer id){
        return goods;

    }
    @APIMapping("mq.com.goods.find")
    public Integer find(Integer id){
        return id;
    }
    public static class Goods implements Serializable{
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
