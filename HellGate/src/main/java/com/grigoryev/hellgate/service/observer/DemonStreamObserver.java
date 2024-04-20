package com.grigoryev.hellgate.service.observer;

import com.grigoryev.demons.Demon;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemonStreamObserver implements StreamObserver<Demon> {

    private StreamObserver<Demon> responseObserver;

    @Override
    public void onNext(Demon demon) {
        responseObserver.onNext(demon);
    }

    @Override
    public void onError(Throwable throwable) {
        responseObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }

}
