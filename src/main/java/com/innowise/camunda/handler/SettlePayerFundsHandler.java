package com.innowise.camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SettlePayerFundsHandler implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> inputVariables = job.getVariablesAsMap();

        //TODO: add logic
        String releaseBlockFundsTransactionStatus = "releaseBlockSuccess"; // OR releaseBlockFailed

        Map<String, Object> outputVariables = new HashMap<>();
        outputVariables.put("releaseBlockFundsTransactionStatus", releaseBlockFundsTransactionStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
