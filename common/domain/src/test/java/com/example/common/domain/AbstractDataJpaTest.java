package com.example.common.domain;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;

@DataJpaTest
public abstract class AbstractDataJpaTest {

    @Autowired
    protected EntityManager em;

    @MockBean
    protected AuditorAware<String> auditorAware;
}
