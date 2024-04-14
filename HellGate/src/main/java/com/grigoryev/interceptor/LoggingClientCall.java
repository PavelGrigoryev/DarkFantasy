package com.grigoryev.interceptor;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.quarkus.logging.Log;

public class LoggingClientCall<ReqT, RespT> extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

    private final MethodDescriptor<ReqT, RespT> method;

    protected LoggingClientCall(ClientCall<ReqT, RespT> delegate, MethodDescriptor<ReqT, RespT> method) {
        super(delegate);
        this.method = method;
    }

    @Override
    public void start(Listener<RespT> responseListener, Metadata headers) {
        Log.infof("Client call started: %s", method.getFullMethodName());
        super.start(new LoggingClientCallListener<>(responseListener), headers);
    }

    @Override
    public void sendMessage(ReqT message) {
        Log.infof("Client sent request:\n%s", message);
        super.sendMessage(message);
    }

}
