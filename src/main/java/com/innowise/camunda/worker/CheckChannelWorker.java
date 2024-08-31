package com.innowise.camunda.worker;

import com.innowise.camunda.handler.CheckChannelHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckChannelWorker {

    private final CheckChannelHandler handler;

    @JobWorker(type = "intra-bank-check-channel")
    public void checkChannelWorker(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
