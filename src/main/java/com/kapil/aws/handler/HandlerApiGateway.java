package com.kapil.aws.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kapil.aws.database.DocCategoriesRefDao;
import com.kapil.aws.utils.LoggerUtil;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

public class HandlerApiGateway implements Handler, RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerApiGateway.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DocCategoriesRefDao docCategoriesRefDao;

    public HandlerApiGateway() throws IOException {
        Handler.configureSSL();
        docCategoriesRefDao = new DocCategoriesRefDao();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        //APIGateway code
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> responseBody = new HashMap<>();
        headers.put("Content-Type", "application/json");
        responseBody.put("Greeting", "Hello Kapil!");
        GSON.toJson(responseBody);
        responseEvent.setHeaders(headers);
        responseEvent.setBody(GSON.toJson(responseBody));
        docCategoriesRefDao.getDocCategories().forEach(printConsumer);
        LoggerUtil.logEnvironment(event, context, GSON);
        return responseEvent;
    }



    // Print documents
    private static final Consumer<Document> printConsumer = document -> LOGGER.info(document.toJson());

}
