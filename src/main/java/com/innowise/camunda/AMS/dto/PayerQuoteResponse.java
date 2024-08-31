package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerQuoteResponse(String orderId,
                                 String payerAccountId,
                                 String payerAccountName,
                                 String payerLookupStatus,
                                 String payerQuoteLookupStatus) {
}
