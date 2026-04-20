package com.campus.lostandfound.config;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.commands.ServerAddress;
import de.flapdoodle.embed.mongo.transitions.ImmutableMongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker.ReachedState;
import jakarta.annotation.PreDestroy;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongodb.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@AutoConfiguration(before = MongoDataAutoConfiguration.class)
@ConditionalOnClass(value = { MongoClient.class, ImmutableMongod.class })
public class EmbeddedMongoConfig {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedMongoConfig.class);

    private ReachedState<RunningMongodProcess> mongodState;

    @Value("${spring.data.mongodb.uri:}")
    private String mongodbUri;

    @Value("${spring.data.mongodb.database:campus_lostandfound}")
    private String mongodbDatabase;

    @Bean
    @Primary
    @Order(1)
    public MongoClient mongoClient() {
        if (hasExternalMongoUri() && isExternalMongoAvailable()) {
            log.info("Using external MongoDB at {}", mongodbUri);
            return MongoClients.create(mongodbUri);
        }

        log.info("Starting embedded MongoDB for local development");
        try {
            ReachedState<RunningMongodProcess> state = ImmutableMongod.builder().build().start(Version.V6_0_8);
            this.mongodState = state;
            RunningMongodProcess process = state.current();
            ServerAddress address = process.getServerAddress();
            String embeddedUri = String.format("mongodb://%s:%d/%s", address.getHost(), address.getPort(), mongodbDatabase);
            log.info("Embedded MongoDB started at {}", embeddedUri);
            return MongoClients.create(embeddedUri);
        } catch (Exception e) {
            log.error("Failed to start embedded MongoDB", e);
            throw new RuntimeException("Unable to start MongoDB (embedded or external)", e);
        }
    }

    private boolean hasExternalMongoUri() {
        return mongodbUri != null && !mongodbUri.isBlank() && !mongodbUri.contains("localhost");
    }

    private boolean isExternalMongoAvailable() {
        if (!hasExternalMongoUri()) {
            return false;
        }
        try {
            MongoClient testClient = MongoClients.create(mongodbUri);
            testClient.getDatabase(mongodbDatabase).runCommand(new Document("ping", 1));
            testClient.close();
            return true;
        } catch (MongoException ex) {
            log.debug("External MongoDB unavailable: {}", ex.getMessage());
            return false;
        }
    }

    @PreDestroy
    public void stopEmbeddedMongo() {
        if (mongodState != null) {
            try {
                mongodState.close();
                log.info("Embedded MongoDB stopped");
            } catch (Exception e) {
                log.warn("Failed to stop embedded MongoDB", e);
            }
        }
    }
}
