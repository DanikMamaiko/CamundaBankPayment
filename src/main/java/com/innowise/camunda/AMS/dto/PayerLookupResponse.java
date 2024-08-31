package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerLookupResponse(String orderId,  String payerAccountId,
                                  String payerAccountName,
                                  String payerLookupStatus) {
}
