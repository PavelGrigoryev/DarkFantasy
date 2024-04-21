package com.grigoryev.hellgate.interceptor;

import com.grigoryev.hellgate.interceptor.util.Constants;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@Slf4j
@GrpcGlobalServerInterceptor
public class AuthServerInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authToken = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        Status status;
        if (authToken == null) {
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!authToken.startsWith(Constants.BEARER_TYPE)) {
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            String tokenValue = authToken.substring(Constants.BEARER_TYPE.length()).trim();
            if (!tokenValue.startsWith(Constants.ACCESS_TOKEN)) {
                status = Status.UNAUTHENTICATED.withDescription("Invalid access token authHeaderValue");
            } else {
                Context context = Context.current()
                        .withValue(Constants.KEY, authToken);
                status = Status.OK.withDescription("Successfully authenticated");
                log.info("Status: {}, {}", status.getCode(), status.getDescription());
                return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
            }
        }
        log.warn("Authentication failed. Status: {}, {}", status.getCode(), status.getDescription());
        serverCall.close(status, new Metadata());
        return new ServerCall.Listener<>() {
        };
    }

}
