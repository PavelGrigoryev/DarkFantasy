package com.grigoryev.hellgate.service;

import com.google.protobuf.Empty;
import com.grigoryev.hellgate.service.observer.HeroStreamObserver;
import com.grigoryev.heroes.DeleteResponse;
import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.HeroServiceGrpc;
import com.grigoryev.heroes.SaveHeroRequest;
import com.grigoryev.id.IdRequest;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class HeroServiceImpl extends HeroServiceGrpc.HeroServiceImplBase {

    @GrpcClient("hero-client")
    private final HeroServiceGrpc.HeroServiceStub heroClient;

    @Override
    public void findById(IdRequest request, StreamObserver<Hero> responseObserver) {
        heroClient.findById(request, new HeroStreamObserver(responseObserver));
    }

    @Override
    public void findAll(Empty request, StreamObserver<Hero> responseObserver) {
        heroClient.findAll(request, new HeroStreamObserver(responseObserver));
    }

    @Override
    public StreamObserver<IdRequest> findAllByIds(StreamObserver<Hero> responseObserver) {
        return heroClient.findAllByIds(new HeroStreamObserver(responseObserver));
    }

    @Override
    public void save(SaveHeroRequest request, StreamObserver<Hero> responseObserver) {
        heroClient.save(request, new HeroStreamObserver(responseObserver));
    }

    @Override
    public void updateById(Hero request, StreamObserver<Hero> responseObserver) {
        heroClient.updateById(request, new HeroStreamObserver(responseObserver));
    }

    @Override
    public void deleteById(IdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        heroClient.deleteById(request, new StreamObserver<>() {

            @Override
            public void onNext(DeleteResponse response) {
                responseObserver.onNext(response);
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
