package com.grigoryev.battlefield.config;

import com.grigoryev.demons.DemonServiceGrpc;
import com.grigoryev.heroes.HeroServiceGrpc;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("demon-client")
    private Channel demonChannel;

    @GrpcClient("hero-client")
    private Channel heroChannel;

    @Bean
    public DemonServiceGrpc.DemonServiceStub demonServiceStub() {
        return DemonServiceGrpc.newStub(demonChannel);
    }

    @Bean
    public HeroServiceGrpc.HeroServiceStub heroServiceStub() {
        return HeroServiceGrpc.newStub(heroChannel);
    }

}
