package com.yd.RestTemplate;

import lombok.Delegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.*;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public abstract class FilterRestTemplate implements RestOperations {
    @Delegate//代理没用吗？
    protected volatile RestTemplate restTemplate;

    public FilterRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}