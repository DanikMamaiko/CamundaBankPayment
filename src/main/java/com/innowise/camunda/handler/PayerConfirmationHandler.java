package com.innowise.camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PayerConfirmationHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        //TODO: add logic for adding confirmation
        String payerConfirmationStatus = "confirmed"; // "OR rejected"

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("payerConfirmationStatus", payerConfirmationStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
