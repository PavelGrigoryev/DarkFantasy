package com.grigoryev.heroes;

import com.grigoryev.heroes.interceptor.AuthServerInterceptor;
import com.grigoryev.heroes.interceptor.LoggingServerInterceptor;
import com.grigoryev.heroes.service.HeroServiceImpl;
import com.grigoryev.heroes.util.YamlUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HeroApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("Hello gRPC!");
        int port = Integer.parseInt(YamlUtil.getYaml().get("server").get("port"));

        Server server = ServerBuilder.forPort(port)
                .addService(new HeroServiceImpl())
                .intercept(new AuthServerInterceptor())
                .intercept(new LoggingServerInterceptor())
                .build()
                .start();

        log.info("gRPC server started, listening on localhost:{}", port);

        Runtime.getRuntime()
                .addShutdownHook(new Thread(server::shutdown));

        server.awaitTermination();
    }

}