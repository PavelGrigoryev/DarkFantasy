package com.grigoryev.heroes.service;

import com.google.protobuf.Empty;
import com.grigoryev.heroes.DeleteResponse;
import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.HeroServiceGrpc;
import com.grigoryev.heroes.SaveHeroRequest;
import com.grigoryev.heroes.mapper.HeroMapper;
import com.grigoryev.heroes.repository.HeroRepository;
import com.grigoryev.id.IdRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class HeroServiceImpl extends HeroServiceGrpc.HeroServiceImplBase {

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;

    public HeroServiceImpl() {
        heroRepository = new HeroRepository();
        heroMapper = Mappers.getMapper(HeroMapper.class);
    }

    @Override
    public void findById(IdRequest request, StreamObserver<Hero> responseObserver) {
        Hero hero = heroRepository.findById(request.getId())
                .map(heroMapper::toHero)
                .orElseThrow(() -> {
                    StatusRuntimeException runtimeException = getStatusRuntimeException(request.getId());
                    responseObserver.onError(runtimeException);
                    return runtimeException;
                });
        responseObserver.onNext(hero);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Empty request, StreamObserver<Hero> responseObserver) {
        heroRepository.findAll()
                .stream()
                .map(heroMapper::toHero)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<IdRequest> findAllByIds(StreamObserver<Hero> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(IdRequest idRequest) {
                Hero hero = heroRepository.findById(idRequest.getId())
                        .map(heroMapper::toHero)
                        .orElseGet(() -> Hero.newBuilder()
                                .setId(idRequest.getId())
                                .setName("Hero with this id is not exist")
                                .build());
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

        };
    }

    @Override
    public void save(SaveHeroRequest request, StreamObserver<Hero> responseObserver) {
        Hero hero = Optional.of(request)
                .map(heroMapper::toHeroEntity)
                .flatMap(heroRepository::save)
                .map(heroMapper::toHero)
                .orElseThrow(() -> {
                    StatusRuntimeException runtimeException = Status.ABORTED
                            .withDescription("Cant save hero")
                            .asRuntimeException();
                    responseObserver.onError(runtimeException);
                    return runtimeException;
                });
        responseObserver.onNext(hero);
        responseObserver.onCompleted();
    }

    @Override
    public void updateById(Hero request, StreamObserver<Hero> responseObserver) {
        Hero hero = Optional.of(request)
                .map(heroMapper::toHeroEntity)
                .flatMap(heroRepository::updateById)
                .map(heroMapper::toHero)
                .orElseThrow(() -> {
                    StatusRuntimeException runtimeException = getStatusRuntimeException(request.getId());
                    responseObserver.onError(runtimeException);
                    return runtimeException;
                });
        responseObserver.onNext(hero);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteById(IdRequest request, StreamObserver<DeleteResponse> responseObserver) {
        DeleteResponse response = heroRepository.deleteById(request.getId())
                .map(heroEntity -> DeleteResponse.newBuilder()
                        .setMessage("Hero with id %s was successfully deleted".formatted(heroEntity.getId()))
                        .build())
                .orElseThrow(() -> {
                    StatusRuntimeException runtimeException = getStatusRuntimeException(request.getId());
                    responseObserver.onError(runtimeException);
                    return runtimeException;
                });
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private StatusRuntimeException getStatusRuntimeException(Long id) {
        return Status.NOT_FOUND
                .withDescription("Hero with id %s is not found".formatted(id))
                .asRuntimeException();
    }

}
