package br.com.sqs.services;

import br.com.sqs.auth.AWSCredentials;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SQSService {

    public static void sendMessage(String message){

        SqsClient sqsClient = SQSClient.returnSQSClient(AWSCredentials.returnCredentials());

        GetQueueUrlRequest request = GetQueueUrlRequest.builder()
                //.queueName("fila-bianca.fifo") // The name of the queue whose URL must be fetched. In this case, FIFO
                 .queueName("bianca-andrade-teste") //In this case, Standard
                .queueOwnerAWSAccountId("755977887883").build(); // The Amazon Web Services account ID of the account that created the queue.
        
        GetQueueUrlResponse response = sqsClient.getQueueUrl(request);
        
        sendMessage(sqsClient, response.queueUrl(), message);

        sqsClient.close();
    }

    public static void sendMessage(SqsClient sqsClient, String queueUrl, String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            // .messageGroupId("grupo") //Para filas FIFO
            .messageBody(message)
            .build();
        sqsClient.sendMessage(sendMsgRequest);
    }
}