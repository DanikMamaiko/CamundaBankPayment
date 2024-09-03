package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record BlockFundsRequest( String orderId,
         String paymentId,
         String paymentType,
         String payerAccountId,
         Integer currencyCode,
         Integer amount) {
}
