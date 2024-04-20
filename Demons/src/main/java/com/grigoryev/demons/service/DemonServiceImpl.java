package com.grigoryev.demons.service;

import com.google.protobuf.Empty;
import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonServiceGrpc;
import com.grigoryev.demons.util.DemonGenerator;
import com.grigoryev.id.IdRequest;
import io.grpc.stub.StreamObserver;

import java.util.Map;

public class DemonServiceImpl extends DemonServiceGrpc.DemonServiceImplBase {

    private final Map<Long, Demon> demonMap;

    public DemonServiceImpl() {
        demonMap = DemonGenerator.getDEMONS();
    }

    @Override
    public void findById(IdRequest request, StreamObserver<Demon> responseObserver) {
        Demon demon = demonMap.get(request.getId());
        responseObserver.onNext(demon);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Empty request, StreamObserver<Demon> responseObserver) {
        demonMap.forEach((k, v) -> responseObserver.onNext(v));
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<IdRequest> findAllByIds(StreamObserver<Demon> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(IdRequest idRequest) {
                Demon demon = demonMap.get(idRequest.getId());
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

        };
    }

}
