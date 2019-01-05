package com.yd.poi.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Created by Yd on 2018/7/10.
 */
public class EnumConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {

        return null;
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }
}
