package com.grigoryev.hellgate.service;

import com.grigoryev.demons.Demon;
import com.grigoryev.demons.IdRequest;
import com.grigoryev.demons.MutinyDemonServiceGrpc;
import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.MutinyHeroServiceGrpc;
import com.grigoryev.heroesvsdemons.HeroVsDemon;
import com.grigoryev.heroesvsdemons.HeroesVsDemonsService;
import com.grigoryev.heroesvsdemons.IdsRequest;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

@GrpcService
public class HeroVsDemonServiceImpl implements HeroesVsDemonsService {

    @GrpcClient("demon-client")
    MutinyDemonServiceGrpc.MutinyDemonServiceStub demonClient;
    @GrpcClient("hero-client")
    MutinyHeroServiceGrpc.MutinyHeroServiceStub heroClient;

    @Override
    public Uni<HeroVsDemon> findHeroVsDemonById(IdsRequest request) {
        Uni<Demon> demonUni = demonClient.findById(IdRequest.newBuilder().setId(request.getDemonId()).build());
        Uni<Hero> heroUni = heroClient.findById(com.grigoryev.heroes.IdRequest.newBuilder().setId(request.getHeroId()).build());
        return Uni.combine()
                .all()
                .unis(demonUni, heroUni)
                .asTuple()
                .onItem()
                .transform(this::getHeroVsDemon);
    }

    @Override
    public Multi<HeroVsDemon> findAllHeroVsDemonByIds(Multi<IdsRequest> request) {
        return request.flatMap(idsRequest -> {
            Uni<Demon> demonUni = demonClient.findById(IdRequest.newBuilder().setId(idsRequest.getDemonId()).build());
            Uni<Hero> heroUni = heroClient.findById(com.grigoryev.heroes.IdRequest.newBuilder().setId(idsRequest.getHeroId()).build());

            return Uni.combine()
                    .all()
                    .unis(demonUni, heroUni)
                    .asTuple()
                    .onItem()
                    .transform(this::getHeroVsDemon)
                    .toMulti();
        });
    }

    private HeroVsDemon getHeroVsDemon(Tuple2<Demon, Hero> tuple) {
        Demon demon = tuple.getItem1();
        Hero hero = tuple.getItem2();
        return HeroVsDemon.newBuilder()
                .setHero(hero)
                .setDemon(demon)
                .build();
    }

}
