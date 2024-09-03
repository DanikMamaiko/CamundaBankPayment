package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.TransferFundsRequest;
import com.innowise.camunda.AMS.dto.TransferFundsResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferFundsService {

    public TransferFundsResponse transferFunds(TransferFundsRequest request) {
        boolean isTransferSuccessful = transferFundsLogic(request.payerAccountId(), request.payeeAccountId());

        if (isTransferSuccessful) {
            String transactionId = UUID.randomUUID().toString();
            return TransferFundsResponse.builder()
                    .orderId(request.orderId())
                    .releaseBlockFundsTransactionStatus("success")
                    .fundsTransferTransactionId(transactionId)
                    .build();
        } else {
            return TransferFundsResponse.builder()
                    .orderId(request.orderId())
                    .releaseBlockFundsTransactionStatus("failed")
                    .fundsTransferTransactionId(null)
                    .build();
        }
    }

    private boolean transferFundsLogic(String payerAccountId, String payeeAccountId) {
        // TODO: write logic
        return true;
    }
}
