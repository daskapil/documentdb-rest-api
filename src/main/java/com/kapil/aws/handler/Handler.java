package com.kapil.aws.handler;

import com.kapil.aws.DocDBRestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Handler {
    static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    static void configureSSL() throws IOException {
        LOGGER.info("Configuring SSL... ");

//        SsmClient ssmClient = DependencyFactory.ssmClient();
//        String trustStore = SsmParameterUtil.getParaValue(ssmClient, System.getenv("TRUST_STORE"));
//        String trustStorePassword = SsmParameterUtil.getParaValue(ssmClient, System.getenv("TRUST_STORE_PASSWORD"), true)));
        String trustStore = System.getenv("TRUST_STORE");
        String trustStorePassword = System.getenv("TRUST_STORE_PASSWORD");
        LOGGER.info(trustStore);
        LOGGER.info(trustStorePassword);
//        ssmClient.close();

        if (Files.notExists(Paths.get(trustStore)))
            throw new DocDBRestApiException("RDS CA Certificate file could not found. Aborting!");

        LOGGER.info("Trust Store exists!");
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        LOGGER.info("SSL trustStore: " + System.getProperty("javax.net.ssl.trustStore"));
        LOGGER.info("SSL trustStorePassword: " + System.getProperty("javax.net.ssl.trustStorePassword"));
    }
}
