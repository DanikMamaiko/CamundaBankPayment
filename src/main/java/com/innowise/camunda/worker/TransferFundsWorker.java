package com.innowise.camunda.worker;

import com.innowise.camunda.handler.TransferFundsHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferFundsWorker {

    private final TransferFundsHandler handler;

    @JobWorker(type = "intra-bank-payer-settle-funds")
    public void transferFunds(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
