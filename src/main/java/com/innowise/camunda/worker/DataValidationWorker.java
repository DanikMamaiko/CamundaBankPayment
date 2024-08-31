package com.innowise.camunda.worker;

import com.innowise.camunda.handler.DataValidationHandler;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataValidationWorker {

    private final DataValidationHandler handler;

    @JobWorker(type="intra-bank-validate-transactional-data")
    public void validateDataWorker(final JobClient client, final ActivatedJob job) throws Exception {
        handler.handle(client, job);
    }
}
