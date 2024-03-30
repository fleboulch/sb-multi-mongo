package com.example.demo.containers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class Containers {

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer1() {
        return new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                .withLabel("com.testcontainers.desktop.service", "1")
//				.withEnv("MONGO_INITDB_DATABASE","local")
//				.withEnv("MONGO_INIT_ROOT_USERNAME","admin")
//				.withEnv("MONGO_INIT_ROOT_PASSWORD","admin")
                .withReuse(true);
    }

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDbContainer2() {
        return new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                .withLabel("com.testcontainers.desktop.service", "2")
                .withReuse(true);
    }

}
