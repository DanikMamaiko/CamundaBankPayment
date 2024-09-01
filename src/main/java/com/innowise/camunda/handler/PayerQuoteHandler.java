package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.dto.PayerQuoteRequest;
import com.innowise.camunda.AMS.dto.PayerQuoteResponse;
import com.innowise.camunda.AMS.service.QuotePayerService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayerQuoteHandler implements JobHandler {

    final private QuotePayerService quotePayerService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
//        final Integer processId = (Integer) inputVariables.get("processId");
//        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
//        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
//        final Integer amount = (Integer) inputVariables.get("amount");
//        final String remittanceDetails = (String) inputVariables.get("remittanceDetails");
//        final String channel = (String) inputVariables.get("channel");
//        final String paymentOrderStartedAt = (String) inputVariables.get("paymentOrderStartedAt");

        PayerQuoteRequest payerQuoteRequest = PayerQuoteRequest.builder()
                .orderId(orderId)
                .payment(paymentType)
                .payerAccountId(payerAccountId)
                .build();

        PayerQuoteResponse payerQuoteResponse = quotePayerService.quotePayer(payerQuoteRequest);
        String payerQuoteStatus = payerQuoteResponse.payerQuoteLookupStatus();

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("orderId", orderId);
//        outputVariables.put("paymentId", paymentId);
//        outputVariables.put("paymentType", paymentType);
//        outputVariables.put("payerAccountId", payerAccountId);
//        outputVariables.put("currencyCode", currencyCode);
//        outputVariables.put("amount", amount);
//        outputVariables.put("paymentOrderStartedAt", paymentOrderStartedAt);
//        outputVariables.put("remittanceDetails", remittanceDetails);
//        outputVariables.put("channel", channel);
//        outputVariables.put("transactionStatus", null);
        outputVariables.put("errorInformation", "USER WAS CHECKED: " + payerQuoteStatus);
        outputVariables.put("payerQuoteLookupStatus", payerQuoteStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
