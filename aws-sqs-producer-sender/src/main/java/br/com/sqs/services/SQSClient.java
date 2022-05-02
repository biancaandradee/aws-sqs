package br.com.sqs.services;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

public class SQSClient {
    public static SqsClient returnSQSClient(AwsCredentialsProvider credentialsProvider) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider)
                .build();
        return sqsClient;
    };
}
