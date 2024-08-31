package com.innowise.camunda.worker;

import com.innowise.camunda.handler.SendErrorEventHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendErrorEventWorker {

    private final SendErrorEventHandler handler;

    @JobWorker(type = "send-error-event")
    public void sendErrorEvent(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
