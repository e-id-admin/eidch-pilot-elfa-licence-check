package ch.admin.astra.elfa.trotti.domain.verifier.impl;

import ch.admin.astra.elfa.trotti.domain.verifier.UseCaseCache;
import ch.admin.astra.elfa.trotti.domain.verifier.exception.UseCaseNotFoundException;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
@Slf4j
public class UseCaseCacheImpl implements UseCaseCache {

    private final ConcurrentMap<UUID, UseCase> useCaseCache;

    @Override
    public List<UseCase> getUseCases() {
        return useCaseCache.values().stream().toList();
    }

    @Override
    public UseCase getUseCaseById(UUID useCaseId) {
        log.debug("Get UseCase by ID");
        if (!useCaseCache.containsKey(useCaseId)) {
            log.error("UseCase not found");
            throw new UseCaseNotFoundException(useCaseId);
        }
        return useCaseCache.get(useCaseId);
    }
}
