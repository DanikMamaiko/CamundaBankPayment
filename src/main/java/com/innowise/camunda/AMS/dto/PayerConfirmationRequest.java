package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerConfirmationRequest( String orderId,
         String payerAccountId,
         Integer amount) {
}
