package com.innowise.camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.springframework.stereotype.Component;

@Component
public class SendErrorEventBlockFundsHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
