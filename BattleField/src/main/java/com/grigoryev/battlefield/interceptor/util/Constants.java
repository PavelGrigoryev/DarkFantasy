package com.grigoryev.battlefield.interceptor.util;

import io.grpc.Context;

public interface Constants {

    String ACCESS_TOKEN = "access-token";
    Context.Key<String> KEY = Context.key(ACCESS_TOKEN);

}
