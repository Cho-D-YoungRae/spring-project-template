package com.example.common.domain;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({JpaConfig.class, OptimisticLockUpdateAspect.class})
public class CommonDomainAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EnumMapperFactory enumMapperFactory() {
        return new EnumMapperFactory();
    }
}
