package com.innowise.camunda.handler;

import com.google.type.DateTime;
import com.innowise.camunda.service.DataValidationService;
import com.innowise.camunda.service.ValidationStatus;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataValidationHandler implements JobHandler {

    private final DataValidationService dataValidationService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processPaymentId = (Integer) inputVariables.get("processPaymentId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId =  (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
        final String currencyName = (String) inputVariables.get("currencyName");
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails =  (String) inputVariables.get("remittanceDetails");
        final String channel =  (String) inputVariables.get("channel");

        String startedAt = LocalDateTime.now().toString();

        final ValidationStatus validationStatus = dataValidationService.validateInputVariables(
            (HashMap<String, Object>) inputVariables);

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("orderId", orderId);
        outputVariables.put("processPaymentId", processPaymentId);
        outputVariables.put("paymentId", paymentId);
        outputVariables.put("processId", null);
        outputVariables.put("paymentType", paymentType);
        outputVariables.put("startedAt", startedAt);
        outputVariables.put("completedAt", null);
        outputVariables.put("paymentStep", null);
        outputVariables.put("payerAccountId", payerAccountId);
        outputVariables.put("payeeAccountId", payeeAccountId);
        outputVariables.put("currencyCode", currencyCode);
        outputVariables.put("amount", amount);
        outputVariables.put("remittanceDetails", remittanceDetails);
        outputVariables.put("channel", channel);
        outputVariables.put("validationStatus", validationStatus.status());
        outputVariables.put("validationMessage", validationStatus.message());

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
