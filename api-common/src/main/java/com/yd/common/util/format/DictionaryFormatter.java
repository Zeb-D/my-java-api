package com.yd.common.util.format;

import java.util.Map;

/**
 * @author Yd on  2018-01-27
 * @Description：
 **/
public class DictionaryFormatter {
    private Map<String,String> dictionaryMap;

    public String format(String key){
        return dictionaryMap.get(key);
    }

    public Map<String, String> getDictionaryMap() {
        return dictionaryMap;
    }

    public void setDictionaryMap(Map<String, String> dictionaryMap) {
        this.dictionaryMap = dictionaryMap;
    }
}
