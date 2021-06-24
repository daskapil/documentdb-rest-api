package com.kapil.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kapil.aws.database.DocumentDBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Lambda function entry point. You can change to use other pojo type or implement
 * a different RequestHandler.
 *
 * @see <a href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda Java Handler</a> for more information
 */
public class App implements RequestHandler<Object, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private final S3Client s3Client;

    public App() {
        // Initialize the SDK client outside of the handler method so that it can be reused for subsequent invocations.
        // It is initialized when the class is loaded.
        s3Client = DependencyFactory.s3Client();
        // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
    }

    @Override
    public Object handleRequest(final Object input, final Context context) {
        // TODO: invoking the api call using s3Client.
        return input;
    }

    public static void main(String[] args) {
        String trustStore = "/tmp/certs/rds-truststore.jks";
        String trustStorePassword = "trustStorePassword";

        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        LOGGER.info("trustStore: ", System.getProperty("javax.net.ssl.trustStore"));
        LOGGER.info("trustStorePassword: ", System.getProperty("javax.net.ssl.trustStorePassword"));

        LOGGER.info("Logging from main method");
        DocumentDBConnection.getInstance();
    }
}
