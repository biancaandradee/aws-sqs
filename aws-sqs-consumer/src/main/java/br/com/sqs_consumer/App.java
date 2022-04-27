package br.com.sqs_consumer;

import br.com.sqs_consumer.services.SQSService;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println("Lendo mensagens ...");
        while(true){
            SQSService.messageReader();
            // Thread.sleep(1000); // Desabilitado por causa do Long Polling para econmizar $$
        }
    }
}

