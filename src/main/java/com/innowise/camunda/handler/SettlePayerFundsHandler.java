package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.PayerLookupRequest;
import com.innowise.camunda.AMS.dto.PayerLookupResponse;
import com.innowise.camunda.AMS.dto.SettleFundsRequest;
import com.innowise.camunda.AMS.dto.SettleFundsResponse;
import com.innowise.camunda.AMS.service.SettleFundsService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlePayerFundsHandler implements JobHandler {

    private final SettleFundsService settleFundsService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final String fundsTransferTransactionId = (String) inputVariables.get("fundsTransferTransactionId");

        SettleFundsRequest settleFundsRequest = SettleFundsRequest.builder()
                .orderId(orderId)
                .payerAccountId(payerAccountId)
                .payeeAccountId(payeeAccountId)
                .build();

        SettleFundsResponse settlementTransaction = settleFundsService.settlePayerFunds(settleFundsRequest);

        Map<String, Object> outputVariables = new HashMap<>();
        outputVariables.put("settlementTransactionStatus", settlementTransaction.settlementTransactionStatus());

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
