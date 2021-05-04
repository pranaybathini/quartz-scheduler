package com.pranay.quartz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranay.quartz.models.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import javax.sql.DataSource;
import java.net.URISyntaxException;

@Service
public class CredentialsService {

    @Autowired
    private VaultTemplate vaultTemplate;

    public Credentials accessCredentials() throws URISyntaxException {

        VaultResponse response = vaultTemplate.read("secret/data/db");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(response.getData().get("data"),Credentials.class);
    }

    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        Credentials credentials = accessCredentials();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/quartz_scheduler");
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}