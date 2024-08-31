package com.innowise.camunda.worker;

import com.innowise.camunda.handler.SettlePayerFundsHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlePayerFundsWorker {

    private final SettlePayerFundsHandler handler;

    @JobWorker(type = "intra-bank-payer-settle-funds")
    public void settlePayerFunds(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
