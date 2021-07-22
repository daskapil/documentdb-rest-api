package com.kapil.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kapil.aws.database.DocCategoriesRefDao;
import com.kapil.aws.factory.DependencyFactory;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

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

    public App() {
        // Initialize the SDK client outside of the handler method so that it can be reused for subsequent invocations.
        // It is initialized when the class is loaded.
        s3Client = DependencyFactory.s3Client();
        // Consider invoking a simple api here to pre-warm up the application, eg: dynamodb#listTables
    }

    @Override
    public Object handleRequest(final Object input, final Context context) {
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(bucket -> System.out.println(bucket.name()));
        System.out.println("********* INPUT ***************");
        System.out.println(input);
        System.out.println("********* Context ***************");
        System.out.println(context);
        System.out.println("Welcome to Lambda function");
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
