package com.grigoryev.demons;

import com.grigoryev.demons.interceptor.AuthServerInterceptor;
import com.grigoryev.demons.interceptor.LoggingServerInterceptor;
import com.grigoryev.demons.service.DemonServiceImpl;
import com.grigoryev.demons.util.YamlUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DemonApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("Hello gRPC!");
        int port = args.length > 0 ? Integer.parseInt(args[0])
                : Integer.parseInt(YamlUtil.getYaml().get("server").get("port"));

        Server server = ServerBuilder.forPort(port)
                .addService(new DemonServiceImpl())
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