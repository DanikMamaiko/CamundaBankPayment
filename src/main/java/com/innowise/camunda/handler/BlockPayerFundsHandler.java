package com.innowise.camunda.handler;

import com.innowise.camunda.AMS.dto.BlockFundsRequest;
import com.innowise.camunda.AMS.dto.BlockFundsResponse;
import com.innowise.camunda.AMS.service.BlockFundsService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockPayerFundsHandler implements JobHandler {

    private final BlockFundsService blockFundsService;

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String orderId = (String) inputVariables.get("orderId");
        final Integer processId = (Integer) inputVariables.get("processId");
        final String paymentId = (String) inputVariables.get("paymentId");
        final String paymentType = (String) inputVariables.get("paymentType");
        final String payerAccountId = (String) inputVariables.get("payerAccountId");
        final Integer currencyCode = (Integer) inputVariables.get("currencyCode");//?
        final Integer amount = (Integer) inputVariables.get("amount");

        BlockFundsRequest blockFundsRequest = BlockFundsRequest.builder()
            .orderId(orderId)
            .payerAccountId(payerAccountId)
            .amount(amount)
            .build();

        BlockFundsResponse blockFundsTransaction =
            blockFundsService.blockPayerFunds(blockFundsRequest);

        Map<String, Object> outputVariables = job.getVariablesAsMap();
        outputVariables.put("payeeAccountId", 1);
        outputVariables.put("command", null);
        outputVariables.put("blockFundsTransactionStatus",
            blockFundsTransaction.blockFundsTransactionStatus());

        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();
    }
}
