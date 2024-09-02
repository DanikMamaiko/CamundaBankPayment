package com.innowise.camunda.worker;

import com.innowise.camunda.handler.SendErrorEventToChannelHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendErrorEventBlockFundsWorker {

    private final SendErrorEventToChannelHandler handler;

    @JobWorker(type = " intra-bank-error-event-block-funds")
    public void sendErrorEvent(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
