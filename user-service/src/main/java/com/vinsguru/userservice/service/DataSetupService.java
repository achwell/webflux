package com.vinsguru.userservice.service;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static org.springframework.util.StreamUtils.copyToString;

@Service
public class DataSetupService implements CommandLineRunner {

    private final Resource initSql;

    private final R2dbcEntityTemplate entityTemplate;

    public DataSetupService(@Value("classpath:h2/init.sql") Resource initSql, R2dbcEntityTemplate entityTemplate) {
        this.initSql = initSql;
        this.entityTemplate = entityTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String query = copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);
        this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

    }
}
