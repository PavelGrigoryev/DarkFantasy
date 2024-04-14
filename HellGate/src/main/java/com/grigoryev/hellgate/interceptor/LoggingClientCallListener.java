package com.grigoryev.hellgate.interceptor;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.Status;
import io.quarkus.logging.Log;

public class LoggingClientCallListener<RespT> extends ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> {

    protected LoggingClientCallListener(ClientCall.Listener<RespT> delegate) {
        super(delegate);
    }

    @Override
    public void onMessage(RespT message) {
        Log.infof("Client received response:\n%s", message);
        super.onMessage(message);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        Log.infof("Client call closed with status: %s", status.getCode());
        super.onClose(status, trailers);
    }

}
