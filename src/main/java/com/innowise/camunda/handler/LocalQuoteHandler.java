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
        final String channel = (String) inputVariables.get("channel");

        final String localQuoteLookupStatus = localQuoteService.localQuoteRequest(channel);

        final Map<String, Object> outputVariables = new HashMap<>();
        outputVariables.put("localQuoteLookupStatus", localQuoteLookupStatus);

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
