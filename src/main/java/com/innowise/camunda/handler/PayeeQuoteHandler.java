package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.service.QuoteUserService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayeeQuoteHandler implements JobHandler {

    private final QuoteUserService quoteUserService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
        final Integer amount = (Integer) inputVariables.get("amount");
        final String remittanceDetails =  (String) inputVariables.get("remittanceDetails");
        final String channel =  (String) inputVariables.get("channel");
        final String paymentOrderStartedAt =  (String) inputVariables.get("paymentOrderStartedAt");

        AmsRequest amsRequest = AmsRequest.builder()
            .orderId(orderId)
            .payment(paymentType)
            .payeeAccountId(payeeAccountId)
            .build();

        AmsResponse amsResponse = quoteUserService.quoteUser(amsRequest);
        String payeeQuoteLookupStatus = amsResponse.payeeQuoteLookupStatus();

        final Map<String, Object> outputVariables = new HashMap<String, Object>();

        outputVariables.put("currency", null);
        outputVariables.put("payerAccountId", null);
        outputVariables.put("payeeQuoteLookupStatus", payeeQuoteLookupStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
