package com.grigoryev.battlefield.service;

import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonServiceGrpc;
import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.HeroServiceGrpc;
import com.grigoryev.heroes.IdRequest;
import com.grigoryev.heroesvsdemons.HeroVsDemon;
import com.grigoryev.heroesvsdemons.HeroesVsDemonsServiceGrpc;
import com.grigoryev.heroesvsdemons.IdsRequest;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.concurrent.CountDownLatch;

@GrpcService
@RequiredArgsConstructor
public class HeroVsDemonServiceImpl extends HeroesVsDemonsServiceGrpc.HeroesVsDemonsServiceImplBase {

    @GrpcClient("hero-client")
    private final HeroServiceGrpc.HeroServiceStub heroClient;

    @GrpcClient("demon-client")
    private final DemonServiceGrpc.DemonServiceStub demonClient;

    @Override
    public void findHeroVsDemonById(IdsRequest request, StreamObserver<HeroVsDemon> responseObserver) {
        CountDownLatch finishLatch = new CountDownLatch(2);
        HeroVsDemon.Builder builder = HeroVsDemon.newBuilder();

        heroClient.findById(IdRequest.newBuilder().setId(request.getHeroId()).build(), new StreamObserver<>() {
            @Override
            public void onNext(Hero hero) {
                builder.setHero(hero);
                finishLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
            }
        });

        demonClient.findById(com.grigoryev.demons.IdRequest.newBuilder().setId(request.getDemonId()).build(), new StreamObserver<>() {
            @Override
            public void onNext(Demon demon) {
                builder.setDemon(demon);
                finishLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
            }
        });

        try {
            finishLatch.await();
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            responseObserver.onError(e);
        }
    }

    @Override
    public StreamObserver<IdsRequest> findAllHeroVsDemonByIds(StreamObserver<HeroVsDemon> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(IdsRequest idsRequest) {
                CountDownLatch finishLatch = new CountDownLatch(2);
                HeroVsDemon.Builder builder = HeroVsDemon.newBuilder();

                heroClient.findById(IdRequest.newBuilder().setId(idsRequest.getHeroId()).build(), new StreamObserver<>() {
                    @Override
                    public void onNext(Hero hero) {
                        builder.setHero(hero);
                        finishLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        responseObserver.onError(t);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
                demonClient.findById(com.grigoryev.demons.IdRequest.newBuilder().setId(idsRequest.getDemonId()).build(), new StreamObserver<>() {
                    @Override
                    public void onNext(Demon demon) {
                        builder.setDemon(demon);
                        finishLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        responseObserver.onError(t);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });

                try {
                    finishLatch.await();
                    responseObserver.onNext(builder.build());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }

        };
    }

}
