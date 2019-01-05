package com.yd.common.util.format;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yd on  2018-01-27
 * @Description：
 **/
@Configuration
public class DictionaryFormatterSummary {

    @Bean(name = "format.type")
    public DictionaryFormatter type() {
        final Map<String, String> type = new HashMap<String, String>();
        type.put("1", "是");
        type.put("0", "否");
        return new DictionaryFormatter() {{
            this.setDictionaryMap(type);
        }};
    }

    @Bean(name = "format.goods.status")
    public DictionaryFormatter goodsStatus() {
        final Map<String, String> type = new HashMap<String, String>();
        type.put("draft", "草稿");
        type.put("approving", "待审");
        type.put("publish", "发布");
        type.put("rejected", "驳回");
        return new DictionaryFormatter() {{
            this.setDictionaryMap(type);
        }};
    }


}
