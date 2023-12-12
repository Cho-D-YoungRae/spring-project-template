package com.example.common.domain;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@Getter
@ConfigurationProperties("app.optimistic-lock-update")
public class OptimisticLockUpdateProperties {

    private final int retryLimit;

    private final int retryTerm;

    @ConstructorBinding
    public OptimisticLockUpdateProperties(final Integer retryLimit, final Integer retryTerm) {
        this.retryLimit = Optional.ofNullable(retryLimit).orElse(10);
        this.retryTerm = Optional.ofNullable(retryTerm).orElse(50);
    }
}
