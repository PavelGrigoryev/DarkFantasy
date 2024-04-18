package com.grigoryev.battlefield.service;

import com.google.protobuf.Empty;
import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonServiceGrpc;
import com.grigoryev.demons.IdRequest;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class DemonServiceImpl extends DemonServiceGrpc.DemonServiceImplBase {

    @GrpcClient("demon-client")
    private final DemonServiceGrpc.DemonServiceStub demonClient;

    @Override
    public void findById(IdRequest request, StreamObserver<Demon> responseObserver) {
        demonClient.findById(request, new StreamObserver<>() {

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

        });
    }

    @Override
    public void findAll(Empty request, StreamObserver<Demon> responseObserver) {
        demonClient.findAll(request, new StreamObserver<>() {

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

        });
    }

    @Override
    public StreamObserver<IdRequest> findAllByIds(StreamObserver<Demon> responseObserver) {
        return demonClient.findAllByIds(new StreamObserver<>() {

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

        });
    }

}
