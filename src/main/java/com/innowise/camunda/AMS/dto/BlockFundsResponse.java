package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record BlockFundsResponse( String orderId,
         String payerAccountId,
         String blockFundsTransactionStatus) {
}
