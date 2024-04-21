package com.grigoryev.demons.interceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthServerInterceptor implements ServerInterceptor {

    private static final String BEARER_TYPE = "Bearer";
    private static final String ACCESS_TOKEN = "access-token";
    private static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key(ACCESS_TOKEN);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authToken = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        Status status;
        if (authToken == null) {
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!authToken.startsWith(BEARER_TYPE)) {
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            String tokenValue = authToken.substring(BEARER_TYPE.length()).trim();
            if (!tokenValue.startsWith(ACCESS_TOKEN)) {
                status = Status.UNAUTHENTICATED.withDescription("Invalid access token authHeaderValue");
            } else {
                Context context = Context.current()
                        .withValue(CLIENT_ID_CONTEXT_KEY, authToken);
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
