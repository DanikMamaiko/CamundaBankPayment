package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.BlockFundsResponse;
import com.innowise.camunda.AMS.dto.BlockFundsRequest;
import com.innowise.camunda.AMS.dto.BlockFundsResponse;
import java.util.List;

import com.innowise.camunda.AMS.dto.PayerLookupResponse;
import org.springframework.stereotype.Component;

@Component
public class BlockFundsService {
    public BlockFundsResponse blockPayerFunds(BlockFundsRequest blockFundsRequest) {

        final List<String> existedPayersAccounts = List.of(
                "CM2110002000300277976315008",
                "AB2110002000300277976315009"
        );

        if (existedPayersAccounts.contains(blockFundsRequest.payerAccountId())) {

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
