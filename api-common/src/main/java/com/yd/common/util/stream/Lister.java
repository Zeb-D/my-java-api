package com.yd.common.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lister {
    public Lister() {
    }

    public static <T> List<T> of(Object... objects) {
        return (List)(objects != null && objects.length != 0 ? Arrays.asList(objects) : new ArrayList());
    }

    public static <T> List<T> copyOf(List<T> objects) {
        return (List)(objects != null && objects.size() != 0 ? (List)Serializer.unserialize(Serializer.serialize(objects)) : new ArrayList());
    }

    public static <T> List<T> copyOf(List<T> dist, List<T> source) {
        if (source != null && source.size() != 0) {
            dist.addAll((List)Serializer.unserialize(Serializer.serialize(source)));
            return dist;
        } else {
            return dist;
        }
    }
}