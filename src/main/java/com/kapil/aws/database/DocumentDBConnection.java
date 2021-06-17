package com.kapil.aws.database;

import com.kapil.aws.config.AppConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentDBConnection {
    private static Logger LOGGER = LoggerFactory.getLogger(DocumentDBConnection.class);

    private static DocumentDBConnection connection;

    private MongoClient mongoClient = null;

    private DocumentDBConnection() {
        this.mongoClient = getMongoClient();
    }

    public static DocumentDBConnection getInstance() {
        if (connection == null) {
            connection = new DocumentDBConnection();
        }
        return connection;
    }

    private static MongoClient getMongoClient() {
        //	configureSSL();

        // Using full connection string for MongoDB Atlas
        MongoClient mongoClient = MongoClients.create(AppConfig.DB_CONNECTION_STRING.getValue());

//        Uncomment below for connecting use Connection String
//        String connectionString = String.format(
//                AppConfig.DB_CONNECTION_STRING_TEMPLATE.getValue(),
//                AppConfig.DB_USER.getValue(),
//                AppConfig.DB_PASSWORD.getValue(),
//                AppConfig.DB_CLUSTER_ENDPOINT.getValue(),
//                AppConfig.DB_READ_PREFERENCE.getValue());
//        MongoClient mongoClient = MongoClients.create(connectionString);



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
        LOGGER.info("From DocumentDBConnection");
        return mongoClient;
    }

    private static void configureSSL() {
        // Update the below variables with your trust store and password
        String truststore = "<truststore>";
        String truststorePassword = "<truststorePassword>";
        System.setProperty("javax.net.ssl.trustStore", truststore);
        System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
    }

    public MongoClient getClient() {
        return mongoClient;
    }

}