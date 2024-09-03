package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record SettleFundsResponse(  String orderId,
         String settlementTransactionStatus) {
}
