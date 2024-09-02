package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record TransferFundsRequest( String orderId,
         String payerAccountId,
         String payeeAccountId, String blockFundsTransactionStatus) {
}
