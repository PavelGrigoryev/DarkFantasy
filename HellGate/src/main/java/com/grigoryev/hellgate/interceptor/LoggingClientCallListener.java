package com.grigoryev.hellgate.interceptor;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingClientCallListener<RespT> extends ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> {

    protected LoggingClientCallListener(ClientCall.Listener<RespT> delegate) {
        super(delegate);
    }

    @Override
    public void onMessage(RespT message) {
        log.info("Client received response:\n{}", message);
        super.onMessage(message);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        log.info("Client call closed with status: {}", status.getCode());
        super.onClose(status, trailers);
    }

}
