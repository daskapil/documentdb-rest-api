package com.kapil.aws.database;

import com.kapil.aws.config.AppConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class DocDBConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocDBConnectionFactory.class);
    private static DocDBConnectionFactory connection;
    private final MongoClient mongoClient;

    private DocDBConnectionFactory() {
        this.mongoClient = getMongoClient();
    }

    public static DocDBConnectionFactory getInstance() {
        if (connection == null) {
            connection = new DocDBConnectionFactory();
        }
        return connection;
    }

    private static MongoClient getMongoClient() {
        // Set-up SSL
        configureSSL();
        // Using full connection string for MongoDB Atlas
//        MongoClient mongoClient = MongoClients.create(AppConfig.DB_CONNECTION_STRING.getValue());

        // Uncomment below for connecting use Connection String
        String connectionString = String.format(
                AppConfig.DB_CONNECTION_STRING_TEMPLATE.getValue(),
                AppConfig.DB_USER.getValue(),
                AppConfig.DB_PASSWORD.getValue(),
                AppConfig.DB_CLUSTER_ENDPOINT.getValue(),
                AppConfig.DB_SSL.getValue(),
                AppConfig.DB_REPLICATE_SET.getValue(),
                AppConfig.DB_READ_PREFERENCE.getValue(),
                AppConfig.DB_RETRY_WRITES.getValue());
        MongoClient mongoClient = MongoClients.create(connectionString);

//		MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());

//		MongoClientSettings settings = MongoClientSettings.builder()
//				.applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(clusterEndpoint, 27017))))
//				.applyToClusterSettings(builder -> builder.requiredClusterType(ClusterType.REPLICA_SET))
//				.applyToClusterSettings(builder -> builder.requiredReplicaSetName("rs0"))
//				.applyToClusterSettings(builder -> builder.mode(ClusterConnectionMode.MULTIPLE))
//				.readPreference(ReadPreference.secondaryPreferred())
//				.applyToSslSettings(builder -> builder.enabled(true))
//				.credential(credential)
//				.applyToConnectionPoolSettings(builder -> builder.maxSize(10))
////				.applyToConnectionPoolSettings(builder -> builder.maxWaitQueueSize(2))
//				.applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(10, TimeUnit.MINUTES))
//				.applyToConnectionPoolSettings(builder -> builder.maxWaitTime(2, TimeUnit.MINUTES))
//				.applyToClusterSettings(builder -> builder.serverSelectionTimeout(30, TimeUnit.SECONDS))
//				.applyToSocketSettings(builder -> builder.connectTimeout(10, TimeUnit.SECONDS))
//				.applyToSocketSettings(builder -> builder.readTimeout(0, TimeUnit.SECONDS))
//				.build();

//		MongoClient mongoClient = MongoClients.create(settings);
        mongoClient.listDatabaseNames().forEach(System.out::println);
        LOGGER.info("Connected to DocumentDB Cluster");
        return mongoClient;
    }

    private static void configureSSL() {
        LOGGER.info("Configuring SSL... ");

//        SsmClient ssmClient = DependencyFactory.ssmClient();
//        String trustStore = SsmParameterUtil.getParaValue(ssmClient, System.getenv("TRUST_STORE"));
//        String trustStorePassword = SsmParameterUtil.getParaValue(ssmClient, System.getenv("TRUST_STORE_PASSWORD"), true)));
        String trustStore = "TRUST_STORE";
        String trustStorePassword = "TRUST_STORE_PASSWORD";
        LOGGER.info(trustStore);
        LOGGER.info(trustStorePassword);
//        ssmClient.close();

//        if (Files.notExists(Paths.get(trustStore)))
//            throw new DocDBRestApiException("RDS CA Certificate file could not found. Aborting!");

        LOGGER.info("Trust Store exists!");
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        LOGGER.info("SSL trustStore: " + System.getProperty("javax.net.ssl.trustStore"));
        LOGGER.info("SSL trustStorePassword: " + System.getProperty("javax.net.ssl.trustStorePassword"));
    }

    private static final Consumer<Document> printConsumer = document -> LOGGER.info(document.toJson());

    public MongoClient getClient() {
        return mongoClient;
    }

}