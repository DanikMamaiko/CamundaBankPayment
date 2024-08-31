package com.innowise.camunda.worker;

import com.innowise.camunda.handler.LocalQuoteHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalQuoteWorker {

    private final LocalQuoteHandler handler;

    @JobWorker(type = "intra-bank-local-quote")
    public void checkLocalQuote(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
