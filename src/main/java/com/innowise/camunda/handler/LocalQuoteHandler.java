package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.service.LocalQuoteService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalQuoteHandler implements JobHandler {

    private final LocalQuoteService localQuoteService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final String currency = (String) inputVariables.get("currency");//?
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails =  (String) inputVariables.get("remittanceDetails");
        final String channel =  (String) inputVariables.get("channel");
        final String paymentOrderStartedAt =  (String) inputVariables.get("paymentOrderStartedAt");


        final String localQuoteLookupStatus = localQuoteService.localQuoteRequest(channel);

        final Map<String, Object> outputVariables = new HashMap<>();

        outputVariables.put("localQuoteLookupStatus", localQuoteLookupStatus);
        outputVariables.put("currencyCode", null);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
