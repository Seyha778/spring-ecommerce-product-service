package com.apextech.productservice.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan({"com.apextech.productservice.entity"})
@EnableJpaRepositories({"com.apextech.productservice.repository"})
public class PersistenceConfiguration {}
