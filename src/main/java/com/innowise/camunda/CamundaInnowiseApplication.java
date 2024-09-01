package com.innowise.camunda;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class    CamundaInnowiseApplication {

    private final ZeebeClient zeebeClient;

    public static void main(String[] args) {
        SpringApplication.run(CamundaInnowiseApplication.class, args);
    }

}
