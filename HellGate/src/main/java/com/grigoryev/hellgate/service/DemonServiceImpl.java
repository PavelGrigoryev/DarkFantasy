package com.grigoryev.hellgate.service;

import com.google.protobuf.Empty;
import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonServiceGrpc;
import com.grigoryev.hellgate.interceptor.credentials.TokenCredentials;
import com.grigoryev.hellgate.service.observer.DemonStreamObserver;
import com.grigoryev.id.IdRequest;
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
        demonClient.withCallCredentials(TokenCredentials.create())
                .findById(request, new DemonStreamObserver(responseObserver));
    }

    @Override
    public void findAll(Empty request, StreamObserver<Demon> responseObserver) {
        demonClient.withCallCredentials(TokenCredentials.create())
                .findAll(request, new DemonStreamObserver(responseObserver));
    }

    @Override
    public StreamObserver<IdRequest> findAllByIds(StreamObserver<Demon> responseObserver) {
        return demonClient.withCallCredentials(TokenCredentials.create())
                .findAllByIds(new DemonStreamObserver(responseObserver));
    }

}
