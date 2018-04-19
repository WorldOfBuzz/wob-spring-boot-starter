package com.worldofbooks.autoconfigure.util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CompletableFutureUtils {

    public static <T> CompletableFuture<List<T>> waitForAllFutures(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> completedFutureResult =
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return completedFutureResult
            .thenApply(ignored -> futures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }
}
