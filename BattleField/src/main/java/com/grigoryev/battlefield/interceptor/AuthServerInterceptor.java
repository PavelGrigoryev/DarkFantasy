package com.grigoryev.battlefield.interceptor;

import com.grigoryev.battlefield.interceptor.util.Constants;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.quarkus.grpc.GlobalInterceptor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@GlobalInterceptor
public class AuthServerInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authToken = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        Context context = Context.current()
                .withValue(Constants.KEY, authToken);
        return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
    }

}
