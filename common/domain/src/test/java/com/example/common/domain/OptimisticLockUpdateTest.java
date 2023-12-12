package com.example.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.lang.management.ManagementFactory;

@ActiveProfiles("container")
//@Tag("container")
@Testcontainers
@Slf4j
@EntityScan
class OptimisticLockUpdateTest {

    private static final DockerImageName MYSQL_IMAGE = DockerImageName.parse(
            "aarch64".equals(ManagementFactory.getOperatingSystemMXBean().getArch()) ?
                    "arm64v8/mysql:8.0.34" : "mysql:8.0.34")
            .asCompatibleSubstituteFor("mysql");

    @Container
    public MySQLContainer<?> mysql = new MySQLContainer<>(MYSQL_IMAGE)
            .withExposedPorts(3306)
            .withLogConsumer(new Slf4jLogConsumer(log));

    @Test
    void raceCondition() {

    }

    @Getter
    @NoArgsConstructor
    @Entity
    @Table(name = "counting")
    static class Counting {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "counting_id")
        private Long id;

        @Version
        @Column(name = "version", nullable = false)
        private Integer version;

        @Column(name = "count", nullable = false)
        private Integer count = 0;

        public void increase() {
            count += 1;
        }
    }

    @Service
    @RequiredArgsConstructor
    static class CountingService {

        private final EntityManager em;

        @Transactional
        @OptimisticLockUpdate
        public void increase(final long id) {
            final Counting counting = em.find(Counting.class, id);
            counting.increase();
        }
    }
}
