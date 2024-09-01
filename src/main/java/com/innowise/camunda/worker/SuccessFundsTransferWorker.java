package com.innowise.camunda.worker;

import com.innowise.camunda.handler.SuccessFundsTransferHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuccessFundsTransferWorker {

    private final SuccessFundsTransferHandler handler;

    @JobWorker(type = " intra-bank-successful-funds-transfer-event")
    public void successFundsTransferWorker(final JobClient client, final ActivatedJob job)
        throws Exception {
        handler.handle(client, job);
    }
}
