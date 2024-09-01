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
//        final String orderId = (String) inputVariables.get("orderId");
//        final Integer processId = (Integer) inputVariables.get("processId");
//        final String paymentId = (String) inputVariables.get("paymentId");
//        final String paymentType = (String) inputVariables.get("paymentType");
//        final String payerAccountId = (String) inputVariables.get("payerAccountId");
//        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");//?
//        final Integer amount = (Integer) inputVariables.get("amount");

        //TODO: add logic for blocking
        String blockFundsTransactionStatus = "blockSuccess"; // OR blockFailed

        Map<String, Object> outputVariables = job.getVariablesAsMap();

//        outputVariables.put("orderId", orderId);
//        outputVariables.put("paymentId", paymentId);
//        outputVariables.put("paymentType", paymentType);
//        outputVariables.put("payerAccountId", payerAccountId);
//        outputVariables.put("currencyCode", currencyCode);
//        outputVariables.put("amount", amount);
        outputVariables.put("blockFundsTransactionStatus", blockFundsTransactionStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
