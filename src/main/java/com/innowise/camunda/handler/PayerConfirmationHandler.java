package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.PayerConfirmationRequest;
import com.innowise.camunda.AMS.dto.PayerConfirmationResponse;
import com.innowise.camunda.AMS.service.PayerConfirmationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayerConfirmationHandler implements JobHandler {

    private  final PayerConfirmationService payerConfirmationService;
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");//?
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails =  (String) inputVariables.get("remittanceDetails");
        final String channel =  (String) inputVariables.get("channel");
        final String paymentOrderStartedAt =  (String) inputVariables.get("paymentOrderStartedAt");

        PayerConfirmationRequest payerConfirmationRequest = PayerConfirmationRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .payerAccountId(payerAccountId)
                .build();

        PayerConfirmationResponse payerConfirmation = payerConfirmationService.confirmPayer(payerConfirmationRequest);

        final Map<String, Object> outputVariables = new HashMap<String, Object>();

        outputVariables.put("payerConfirmationStatus", payerConfirmation.payerConfirmationStatus());

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
