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
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static java.util.Arrays.asList;

//@AutoConfiguration
//@EnableConfigurationProperties(MongoCustomeProperties.class)

@Configuration
@EnableMongoRepositories(
        basePackages = "com.example.demo.infra.database.student",
        mongoTemplateRef = "primaryMongoTemplate"
)
@EnableConfigurationProperties
public class Mongo1Configuration {

    @Primary
    @Bean("primaryMongoProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    public MongoProperties primaryMongoProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "primaryMongoClient")
    public MongoClient primaryMongoClient(
//            MongoCustomeProperties mongoProperties,
            @Qualifier("primaryMongoProperties") MongoProperties mongoProperties
    ) {

        ServerAddress serverAddress = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(List.of(serverAddress)))
//                .credential(MongoCredential.createCredential(
//                        mongoProperties.getUsername(),
//                        mongoProperties.getAuthenticationDatabase(),
//                        mongoProperties.getPassword()))
                .build();
        return MongoClients.create(settings);
    }

    @Primary
    @Bean(name = "primaryMongoFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("primaryMongoClient") MongoClient mongoClient,
//            MongoCustomeProperties mongoProperties
            @Qualifier("primaryMongoProperties") MongoProperties mongoProperties
    ) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(
            @Qualifier("primaryMongoFactory") MongoDatabaseFactory mongoDatabaseFactory) {
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
