package com.innowise.camunda.handler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendErrorEventToChannelHandler implements JobHandler {

    private final ZeebeClient zeebeClient;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        zeebeClient.newPublishMessageCommand()
            .messageName("ERROR TO THE CHANNEL")
            .correlationKey("ERROR TO THE CHANNEL")
            .send()
            .join();

        client.newCompleteCommand(job.getKey()).send().join();
    }
}
