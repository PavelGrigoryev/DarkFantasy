package com.grigoryev.battlefield.service;

import com.google.protobuf.Empty;
import com.grigoryev.heroes.DeleteResponse;
import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.HeroService;
import com.grigoryev.heroes.IdRequest;
import com.grigoryev.heroes.MutinyHeroServiceGrpc;
import com.grigoryev.heroes.SaveHeroRequest;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HeroServiceImpl implements HeroService {

    @GrpcClient("hero-client")
    MutinyHeroServiceGrpc.MutinyHeroServiceStub heroClient;

    @Override
    public Uni<Hero> findById(IdRequest request) {
        return heroClient.findById(request);
    }

    @Override
    public Uni<Hero> save(SaveHeroRequest request) {
        return heroClient.save(request);
    }

    @Override
    public Uni<Hero> updateById(Hero request) {
        return heroClient.updateById(request);
    }

    @Override
    public Uni<DeleteResponse> deleteById(IdRequest request) {
        return heroClient.deleteById(request);
    }

    @Override
    public Multi<Hero> findAll(Empty request) {
        return heroClient.findAll(request);
    }

    @Override
    public Multi<Hero> findAllByIds(Multi<IdRequest> request) {
        return heroClient.findAllByIds(request);
    }

}
