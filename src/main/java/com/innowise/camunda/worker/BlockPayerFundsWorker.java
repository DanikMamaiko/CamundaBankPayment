package com.innowise.camunda.worker;

import com.innowise.camunda.handler.BlockPayerFundsHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockPayerFundsWorker {

    private final BlockPayerFundsHandler handler;

    @JobWorker(type = "intra-bank-block-payer-funds")
    public void blockPayerFunds(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
