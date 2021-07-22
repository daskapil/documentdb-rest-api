
package com.kapil.aws.factory;

import com.kapil.aws.App;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.ssm.SsmClient;

/**
 * The module containing all dependencies required by the {@link App}.
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
//                       .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                       .region(Region.US_EAST_1)
                       .httpClientBuilder(UrlConnectionHttpClient.builder())
                       .build();
    }

    public static SsmClient ssmClient() {
        return SsmClient.builder()
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}
