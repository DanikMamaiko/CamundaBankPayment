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
//        final Integer processPaymentId = (Integer) inputVariables.get("processPaymentId"); // Добавлено
//        final String paymentId = (String) inputVariables.get("paymentId"); // Добавлено
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId"); // Опционально
//        final String payeeAccountId = (String) inputVariables.get("payeeAccountId"); // Добавлено
//        final Integer currencyCode = (Integer) inputVariables.get("currencyCode"); // Добавлено
//        final Integer amount = (Integer) inputVariables.get("amount"); // Добавлено
//        final String remittanceDetails = (String) inputVariables.get("remittanceDetails"); // Опционально
//        final String channel = (String) inputVariables.get("channel"); // Добавлено
//        final String startedAt = (String) inputVariables.get("startedAt");
//        final String createdAt = Instant.now().toString();

        PayerLookupRequest payerLookupRequest = PayerLookupRequest.builder()
                .orderId(orderId)
                .payment(paymentType)
                .payerAccountId(payerAccountId)
                .build();

        final String requestSentAt = Instant.now().toString();
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

        outputVariables.put("orderId", orderId);
//        outputVariables.put("processPaymentId", processPaymentId);
//        outputVariables.put("paymentId", paymentId);
//        outputVariables.put("processId", processPaymentId);
//        outputVariables.put("paymentType", paymentType);
//        outputVariables.put("startedAt", startedAt);
//        outputVariables.put("completedAt", null);
//        outputVariables.put("responseSent", true);
//        outputVariables.put("requestReceived", true);
//        outputVariables.put("payerAccountId", payerAccountId);
//        outputVariables.put("payeeAccountId", payeeAccountId);
//        outputVariables.put("currencyCode", currencyCode);
//        outputVariables.put("amount", amount);
//        outputVariables.put("remittanceDetails", remittanceDetails);
//        outputVariables.put("channel", channel);
//        outputVariables.put("payerAccountName", null);
        outputVariables.put("payerLookupStatus", payerLookupStatus);
        outputVariables.put("payerLookupRetryNumber", null);
        outputVariables.put("ID", null);
        outputVariables.put("transactionStatus", transactionStatus);
        outputVariables.put("errorInformation", "USER WAS CHECKED: " + payerLookupStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
