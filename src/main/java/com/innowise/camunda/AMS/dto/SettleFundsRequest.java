package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record SettleFundsRequest( String orderId,
         String payerAccountId,
         String payeeAccountId){
}
