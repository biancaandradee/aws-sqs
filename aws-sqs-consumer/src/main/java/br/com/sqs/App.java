package br.com.sqs;

import br.com.sqs.services.SQSService;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Reading messages...");

        while (true) {
            SQSService.messageReader();
            // Thread.sleep(1000);
        }
    };
}
