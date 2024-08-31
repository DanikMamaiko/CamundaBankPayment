package com.innowise.camunda.handler;

import com.innowise.camunda.service.PayerConfirmationResponseService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckChannelHandler implements JobHandler {

    private final PayerConfirmationResponseService payerConfirmationResponseService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        //TODO: Add logic
        final boolean isPayerConfirmationRequired = true;

        final boolean payerConfirmationResponseRequired =
            payerConfirmationResponseService.isPayerConfirmationResponseRequired(isPayerConfirmationRequired);

        final Map<String, Object> outputVariables = new HashMap<String, Object>();
        outputVariables.put("payerConfirmationResponseRequired", payerConfirmationResponseRequired);
    }
}
