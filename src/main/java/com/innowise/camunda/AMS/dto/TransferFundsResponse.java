package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record TransferFundsResponse(  String orderId,
         String releaseBlockFundsTransactionStatus,
         String fundsTransferTransactionId) {
}
