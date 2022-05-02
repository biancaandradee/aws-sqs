package br.com.sqs;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.sqs.services.SQSService;

public class App {
    public static void main(String[] args) {

        SQSService.sendMessage("New Message: " + LocalDate.now() + LocalTime.now());

    }
}
