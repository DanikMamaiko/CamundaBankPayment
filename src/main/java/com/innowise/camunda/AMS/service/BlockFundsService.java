package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.BlockFundsRequest;
import com.innowise.camunda.AMS.dto.BlockFundsResponse;
import org.springframework.stereotype.Component;

@Component
public class BlockFundsService {

    private final int PAYER_BALANCE = 1000;

    public BlockFundsResponse blockPayerFunds(BlockFundsRequest blockFundsRequest) {

        int transferAmount = blockFundsRequest.amount();

        if (PAYER_BALANCE - transferAmount > 0) {

            return BlockFundsResponse.builder()
                    .orderId(blockFundsRequest.orderId())
                    .payerAccountId(blockFundsRequest.payerAccountId())
                    .blockFundsTransactionStatus("blockSuccess")
                    .build();
        } else {
            return BlockFundsResponse.builder()
                    .blockFundsTransactionStatus("blockFailed")
                    .build();
        }
    }
}
