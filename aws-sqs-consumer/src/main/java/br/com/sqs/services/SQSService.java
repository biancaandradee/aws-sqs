package br.com.sqs.services;

import java.util.List;

import br.com.sqs.auth.AWSCredentials;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class SQSService {

    public static void messageReader(){
       
        SqsClient sqsClient = SQSClient.returnSQSClient(AWSCredentials.returnCredentials());

        GetQueueUrlRequest request = GetQueueUrlRequest.builder()
                //.queueName("fila-bianca.fifo") // The name of the queue whose URL must be fetched. In this case, FIFO
                 .queueName("bianca-andrade-teste") //In this case, Standard
                .queueOwnerAWSAccountId("755977887883").build(); // The Amazon Web Services account ID of the account that created the queue.
        
        GetQueueUrlResponse response = sqsClient.getQueueUrl(request);

        
        List<Message> messages = receiveMessages(sqsClient, response.queueUrl());
        // System.out.println("Quantidade de mensagens: " + messages.size());
        for (Message message : messages) {
            System.out.println("Message: " + message.body());
        }

        deleteMessages(sqsClient, response.queueUrl(),  messages);

        sqsClient.close();
    }

    public static  List<Message> receiveMessages(SqsClient sqsClient, String queueUrl) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)  //The URL of the Amazon SQS queue from which messages are received.
            .waitTimeSeconds(20)   //The maximum number of messages to return.
            .maxNumberOfMessages(5)  //The duration (in seconds) for which the call waits for a message to arrive in the queue before returning (must be >= 0 and <= 20).
            .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        return messages;
    }

    public static void deleteMessages(SqsClient sqsClient, String queueUrl,  List<Message> messages) {
        for (Message message : messages) {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl) //The URL of the Amazon SQS queue from which messages are deleted.
                    .receiptHandle(message.receiptHandle()) //The receipt handle associated with the message to delete.
                    .build();

            sqsClient.deleteMessage(deleteMessageRequest);
        }
   }
}