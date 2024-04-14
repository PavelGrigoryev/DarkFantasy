package com.grigoryev.service;

import com.google.protobuf.Empty;
import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonService;
import com.grigoryev.demons.IdRequest;
import com.grigoryev.demons.MutinyDemonServiceGrpc;
import com.grigoryev.interceptor.LoggingClientInterceptor;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.RegisterClientInterceptor;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class DemonServiceImpl implements DemonService {

    @GrpcClient("demon-client")
    @RegisterClientInterceptor(LoggingClientInterceptor.class)
    MutinyDemonServiceGrpc.MutinyDemonServiceStub demonClient;

    @Override
    public Uni<Demon> findById(IdRequest request) {
        return demonClient.findById(request);
    }

    @Override
    public Multi<Demon> findAll(Empty request) {
        return demonClient.findAll(request);
    }

    @Override
    public Multi<Demon> findAllByIds(Multi<IdRequest> request) {
        return demonClient.findAllByIds(request);
    }

}
