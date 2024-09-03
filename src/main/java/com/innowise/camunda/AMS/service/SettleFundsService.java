package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.SettleFundsRequest;
import com.innowise.camunda.AMS.dto.SettleFundsResponse;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SettleFundsService {

    private static final List<String> EXISTING_ACCOUNTS = List.of(
            "CM2110002000300277976315008", "AB2110002000300277976315009"
    );

    public SettleFundsResponse settlePayerFunds(SettleFundsRequest settleFundsRequest) {

        boolean isSettled = settleFundsLogic(settleFundsRequest);

        if (isSettled) {
            return SettleFundsResponse.builder()
                    .orderId(settleFundsRequest.orderId())
                    .settlementTransactionStatus("releaseBlockSuccess")
                    .build();
        } else {
            return SettleFundsResponse.builder()
                    .settlementTransactionStatus("releaseBlockFailed")
                    .build();
        }
    }

    private boolean settleFundsLogic(SettleFundsRequest request) {
        if (!EXISTING_ACCOUNTS.contains(request.payerAccountId()) ||
                !EXISTING_ACCOUNTS.contains(request.payeeAccountId())) {
            return false;
        }
        return true;
    }
}
