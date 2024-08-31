package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerQuoteRequest(String orderId,
                                String payment,
                                String payerAccountId) {
}
