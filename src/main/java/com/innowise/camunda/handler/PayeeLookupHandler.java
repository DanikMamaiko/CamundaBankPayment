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
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processPaymentId = (Integer) inputVariables.get("processPaymentId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId =  (String) inputVariables.get("payerAccountId");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails =  (String) inputVariables.get("remittanceDetails");
        final String channel =  (String) inputVariables.get("channel");
        final String startedAt =  (String) inputVariables.get("startedAt");

        AmsRequest amsRequest = AmsRequest.builder()
            .orderId(orderId)
            .payment(paymentType)
            .payeeAccountId(payeeAccountId)
            .build();

        AmsResponse amsResponse = lookupUserService.lookupUser(amsRequest);
        String payeeLookupStatus = amsResponse.payeeLookupStatus();

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("processId", processPaymentId);
        outputVariables.put("startedAt", startedAt);
        outputVariables.put("completedAt", null);
        outputVariables.put("responseSent", true);
        outputVariables.put("requestReceived", true);
        outputVariables.put("payeeAccountName", null);
        outputVariables.put("payeeLookupStatus", payeeLookupStatus);
        outputVariables.put("payeeLookupRetryNumber", null);
        outputVariables.put("ID", null);
        outputVariables.put("transactionStatus", null);
        outputVariables.put("errorInformation", "USER WAS CHECKED: amsResponse.payeeLookupStatus()");

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
