package com.grigoryev.hellgate.interceptor;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingClientCall<ReqT, RespT> extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

    private final MethodDescriptor<ReqT, RespT> method;

    protected LoggingClientCall(ClientCall<ReqT, RespT> delegate, MethodDescriptor<ReqT, RespT> method) {
        super(delegate);
        this.method = method;
    }

    @Override
    public void start(Listener<RespT> responseListener, Metadata headers) {
        log.info("Client call started: {}", method.getFullMethodName());
        super.start(new LoggingClientCallListener<>(responseListener), headers);
    }

    @Override
    public void sendMessage(ReqT message) {
        log.info("Client sent request:\n{}", message);
        super.sendMessage(message);
    }

}
