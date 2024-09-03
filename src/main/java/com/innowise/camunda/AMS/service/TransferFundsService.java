package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.TransferFundsRequest;
import com.innowise.camunda.AMS.dto.TransferFundsResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferFundsService {

    public TransferFundsResponse transferFunds(TransferFundsRequest request) {
        String failedBlockStatus = "blockFailed";
        String blockStatus = request.blockFundsTransactionStatus();

        if (failedBlockStatus.equals(blockStatus)) {
            return TransferFundsResponse.builder()
                    .orderId(request.orderId())
                    .releaseBlockFundsTransactionStatus("failed")
                    .fundsTransferTransactionId(null)
                    .build();
        }

        boolean isTransferSuccessful = transferFundsLogic(request.payerAccountId(), request.payeeAccountId());

        if (isTransferSuccessful) {
            String transactionId = UUID.randomUUID().toString(); // Генерация уникального ID для транзакции
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

    //  перевод средств
    private boolean transferFundsLogic(String payerAccountId, String payeeAccountId) {

        // Сейчас просто имитирует успешный перевод
        return true;
    }
}
