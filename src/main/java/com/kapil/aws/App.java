package com.kapil.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kapil.aws.database.DocCategoriesRefDao;
import com.kapil.aws.factory.DependencyFactory;
import com.kapil.aws.handler.Handler;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Lambda function entry point. You can change to use other pojo type or implement
 * a different RequestHandler.
 *
 * @see <a href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda Java Handler</a> for more information
 */
public class App implements RequestHandler<Object, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private final S3Client s3Client;

    public App() throws IOException {
        // Initialize the SDK client outside of the handler method so that it can be reused for subsequent invocations.
        // It is initialized when the class is loaded.
        Handler.configureSSL();
        s3Client = DependencyFactory.s3Client();
        // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
    }

    @Override
    public Object handleRequest(final Object input, final Context context) {
        // TODO: invoking the api call using s3Client.
        return input;
    }

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Logging from main method");
//        DocumentDBConnection.getInstance();

        DocCategoriesRefDao docCategoriesRefDao = new DocCategoriesRefDao();
        docCategoriesRefDao.getDocCategories().forEach(printConsumer);
        LOGGER.info(docCategoriesRefDao.getDocCategoryFields("employee").toJson());

    }
    // Print documents
    private static final Consumer<Document> printConsumer = document -> LOGGER.info(document.toJson());


}
