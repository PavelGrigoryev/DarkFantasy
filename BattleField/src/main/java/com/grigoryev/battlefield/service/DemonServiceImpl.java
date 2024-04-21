package com.grigoryev.battlefield.service;

import com.google.protobuf.Empty;
import com.grigoryev.battlefield.interceptor.credentials.TokenCredentials;
import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonService;
import com.grigoryev.demons.MutinyDemonServiceGrpc;
import com.grigoryev.id.IdRequest;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class DemonServiceImpl implements DemonService {

    @GrpcClient("demon-client")
    MutinyDemonServiceGrpc.MutinyDemonServiceStub demonClient;

    @Override
    public Uni<Demon> findById(IdRequest request) {
        return demonClient.withCallCredentials(TokenCredentials.create())
                .findById(request);
    }

    @Override
    public Multi<Demon> findAll(Empty request) {
        return demonClient.withCallCredentials(TokenCredentials.create())
                .findAll(request);
    }

    @Override
    public Multi<Demon> findAllByIds(Multi<IdRequest> request) {
        return demonClient.withCallCredentials(TokenCredentials.create())
                .findAllByIds(request);
    }

}
