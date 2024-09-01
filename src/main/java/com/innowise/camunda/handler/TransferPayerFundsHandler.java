package com.innowise.camunda.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TransferPayerFundsHandler implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final String blockFundsTransactionId = (String) inputVariables.get("blockFundsTransactionStatus");


        //TODO: Add logic
        String releaseBlockFundsTransactionStatus = "success";

        Map<String, Object> outputVariables = new HashMap<>();

        outputVariables.put("command", null);
        outputVariables.put("releaseBlockFundsTransactionStatus", releaseBlockFundsTransactionStatus);
        outputVariables.put("fundsTransferTransactionId", null);
        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
