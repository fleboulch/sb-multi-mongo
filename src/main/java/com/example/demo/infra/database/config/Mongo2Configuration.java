package com.example.demo.infra.database.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.example.demo.infra.database.room",
        mongoTemplateRef = "secondaryMongoTemplate"
)
@EnableConfigurationProperties
public class Mongo2Configuration {

    @Bean("secondaryMongoProperties")
    @ConfigurationProperties(prefix = "mongodb.secondary")
    public MongoProperties secondaryMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "secondaryMongoClient")
    public MongoClient secondaryMongoClient(
            @Qualifier("secondaryMongoProperties") MongoProperties mongoProperties) {

        ServerAddress serverAddress = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(List.of(serverAddress)))
//                .credential(MongoCredential.createCredential(
//                        mongoProperties.getUsername(),
//                        mongoProperties.getAuthenticationDatabase(),
//                        mongoProperties.getPassword()))
                .build();
        return MongoClients.create(settings);
    }

    @Bean(name = "secondaryMongoFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("secondaryMongoClient") MongoClient mongoClient,
            @Qualifier("secondaryMongoProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate mongoTemplate(
            @Qualifier("secondaryMongoFactory") MongoDatabaseFactory mongoDatabaseFactory) {
//        return new MongoTemplate(mongoDatabaseFactory);
        MongoTemplate template = new MongoTemplate(mongoDatabaseFactory);
        MappingMongoConverter mmc = (MappingMongoConverter)template.getConverter();
        mmc.setCustomConversions( new MongoCustomConversions(asList(
                new Converters.BinaryToUuidConverter(),
                new Converters.UuidToBinaryConverter()
        )));
        mmc.afterPropertiesSet();
        return template;
    }

}
