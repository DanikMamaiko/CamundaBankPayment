package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.service.LookupUserService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayeeLookupHandler implements JobHandler {

    private final LookupUserService lookupUserService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        final String orderId = (String) inputVariables.get("orderId"); // Unique identifier for the payment order
        final Integer processId = (Integer) inputVariables.get("processId"); // ID of the process in Camunda
        final String paymentId = (String) inputVariables.get("paymentId"); // Unique identifier for the payment transaction
        final String payerAccountId = (String) inputVariables.get("payerAccountId"); // Identifier of the payer's IBAN
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId"); // Identifier of the payee's IBAN
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode"); // Currency of the instruction (transfer)
        final Integer amount = (Integer) inputVariables.get("amount"); // Amount of the instruction (transfer)
        final String remittanceDetails = (String) inputVariables.get("remittanceDetails"); // Information about the purpose or nature of the payment
        final String channel = (String) inputVariables.get("channel"); // Communication channel through which the payment order was received
        final String paymentOrderStartedAt = (String) inputVariables.get("paymentOrderStartedAt");

        final String validationStatus = (String) inputVariables.get("validationStatus"); // Статус валидации
        final String validationMessage = (String) inputVariables.get("validationMessage"); // Сообщение о валидации




        AmsRequest amsRequest = AmsRequest.builder()
            .orderId(orderId)
            .payeeAccountId(payeeAccountId)
            .build();

        AmsResponse amsResponse = lookupUserService.lookupUser(amsRequest);
        String payeeLookupStatus = amsResponse.payeeLookupStatus();

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("orderId", orderId);
        outputVariables.put("paymentId", paymentId);
        outputVariables.put("payeeAccountId", payeeAccountId);


        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
