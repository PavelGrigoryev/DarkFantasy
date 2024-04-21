package com.grigoryev.hellgate.interceptor.util;

import io.grpc.Context;

public interface Constants {

    String BEARER_TYPE = "Bearer";
    String ACCESS_TOKEN = "access-token";
    Context.Key<String> KEY = Context.key(ACCESS_TOKEN);

}
