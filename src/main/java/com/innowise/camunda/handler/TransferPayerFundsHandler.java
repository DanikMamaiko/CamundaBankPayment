package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.TransferFundsRequest;
import com.innowise.camunda.AMS.dto.TransferFundsResponse;
import com.innowise.camunda.AMS.service.TransferFundsService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferPayerFundsHandler implements JobHandler {

    private final TransferFundsService transferFundsService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final String blockFundsTransactionStatus = (String) inputVariables.get("blockFundsTransactionStatus");

        TransferFundsRequest transferFundsRequest = TransferFundsRequest.builder()
                .orderId(orderId)
                .payerAccountId(payerAccountId)
                .payeeAccountId(payeeAccountId)
                .blockFundsTransactionStatus(blockFundsTransactionStatus)
                .build();

        TransferFundsResponse releaseBlockFundsTransaction = transferFundsService.transferFunds(transferFundsRequest);

        Map<String, Object> outputVariables = new HashMap<>();
        outputVariables.put("command", null);
        outputVariables.put("releaseBlockFundsTransactionStatus", releaseBlockFundsTransaction.releaseBlockFundsTransactionStatus());
        outputVariables.put("fundsTransferTransactionId", null);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
