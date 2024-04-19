package com.grigoryev.hellgate.service;

import com.grigoryev.heroesvsdemons.HeroVsDemon;
import com.grigoryev.heroesvsdemons.HeroesVsDemonsService;
import com.grigoryev.heroesvsdemons.IdsRequest;
import com.grigoryev.heroesvsdemons.MutinyHeroesVsDemonsServiceGrpc;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HeroVsDemonServiceImpl implements HeroesVsDemonsService {

    @GrpcClient("hero-vs-demon-client")
    MutinyHeroesVsDemonsServiceGrpc.MutinyHeroesVsDemonsServiceStub client;

    @Override
    public Uni<HeroVsDemon> findHeroVsDemonById(IdsRequest request) {
        return client.findHeroVsDemonById(request);
    }

    @Override
    public Multi<HeroVsDemon> findAllHeroVsDemonByIds(Multi<IdsRequest> request) {
        return client.findAllHeroVsDemonByIds(request);
    }

}
