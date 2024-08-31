package com.innowise.camunda.worker;

import com.innowise.camunda.handler.PayeeQuoteHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayeeQuoteWorker {

    private final PayeeQuoteHandler handler;

    @JobWorker(type="intra-bank-payee-user-quote")
    public void payeeQuoteWorker(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
