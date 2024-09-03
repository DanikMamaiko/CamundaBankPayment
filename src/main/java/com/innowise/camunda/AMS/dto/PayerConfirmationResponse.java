package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerConfirmationResponse( String orderId,
         String payerAccountId,
         String payerConfirmationStatus) {
}
