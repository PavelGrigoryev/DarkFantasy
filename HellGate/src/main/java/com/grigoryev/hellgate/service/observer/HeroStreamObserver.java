package com.grigoryev.hellgate.service.observer;

import com.grigoryev.heroes.Hero;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeroStreamObserver implements StreamObserver<Hero> {

    private StreamObserver<Hero> responseObserver;

    @Override
    public void onNext(Hero hero) {
        responseObserver.onNext(hero);
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
