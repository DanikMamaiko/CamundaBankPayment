package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.dto.PayerLookupRequest;
import com.innowise.camunda.AMS.dto.PayerLookupResponse;
import com.innowise.camunda.AMS.service.LookupPayerService;
import com.innowise.camunda.AMS.service.LookupUserService;
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
public class PayerLookupHandler implements JobHandler {

    private final LookupPayerService lookupPayerService;


    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processPaymentId = (Integer) inputVariables.get("processPaymentId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails = (String) inputVariables.get("remittanceDetails");
        final String channel = (String) inputVariables.get("channel");
        final String startedAt = Instant.now().toString();

        PayerLookupRequest payerLookupRequest = PayerLookupRequest.builder()
                .orderId(orderId)
                .payment(paymentType)
                .payerAccountId(payerAccountId)
                .build();

        PayerLookupResponse payerLookupResponse = lookupPayerService.lookupPayer(payerLookupRequest);
        final String responseReceivedAt = Instant.now().toString();

        String payerLookupStatus = payerLookupResponse.payerLookupStatus();

        // Определение статуса транзакции
        String transactionStatus;
        if ("exist".equals(payerLookupStatus)) {
            transactionStatus = "PAYER_FOUND";  // Плательщик найден
        } else {
            transactionStatus = "PAYER_NOT_FOUND";  // Плательщик не найден
        }

        // Подготовка выходных переменных
        final Map<String, Object> outputVariables = new HashMap<>();
        outputVariables.put("processId", null);
        outputVariables.put("paymentOrderStartedAt", null);
        outputVariables.put("startedAt", startedAt);
        outputVariables.put("completedAt", null);
        outputVariables.put("responseSent", true);
        outputVariables.put("requestReceived", true);
        outputVariables.put("payerAccountName", null);
        outputVariables.put("payerLookupRetryNumber", null);
        outputVariables.put("ID", null);
        outputVariables.put("transactionStatus", transactionStatus);
        outputVariables.put("payerLookupStatus", payerLookupStatus);
        outputVariables.put("errorInformation", "USER WAS CHECKED: " + payerLookupStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
