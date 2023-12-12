package com.example.common.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;

@Aspect
@Slf4j
@Configuration
@EnableConfigurationProperties(OptimisticLockUpdateProperties.class)
@RequiredArgsConstructor
public class OptimisticLockUpdateAspect {

    private final OptimisticLockUpdateProperties properties;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Around("@within(com.example.common.domain.OptimisticLockUpdate)")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        int i = 0;
        while (true) {
            try {
                i++;
                return joinPoint.proceed();
            } catch (OptimisticLockingFailureException e) {
                if (i == properties.getRetryLimit()) {
                    throw new OptimisticLockUpdateFailureException(e);
                }
                Thread.sleep(properties.getRetryTerm());
            }
        }
    }
}