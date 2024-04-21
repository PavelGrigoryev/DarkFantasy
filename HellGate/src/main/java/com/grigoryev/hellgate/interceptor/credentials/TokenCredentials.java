package com.grigoryev.hellgate.interceptor.credentials;

import com.grigoryev.hellgate.interceptor.util.Constants;
import io.grpc.CallCredentials;
import io.grpc.Context;
import io.grpc.Metadata;
import lombok.AllArgsConstructor;

import java.util.concurrent.Executor;

@AllArgsConstructor
public class TokenCredentials extends CallCredentials {

    private String token;

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            Metadata headers = new Metadata();
            Metadata.Key<String> key = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
            headers.put(key, token != null ? token : "");
            metadataApplier.apply(headers);
        });
    }

    public static TokenCredentials create() {
        return new TokenCredentials(Constants.KEY.get(Context.current()));
    }

}
