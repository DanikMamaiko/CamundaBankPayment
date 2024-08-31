package com.innowise.camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BlockPayerFundsHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        //TODO: add logic for blocking
        String blockFundsTransactionStatus = "blockSuccess"; // OR blockFailed

        Map<String, Object> outputVariables = job.getVariablesAsMap();
        outputVariables.put("blockFundsTransactionStatus", blockFundsTransactionStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
