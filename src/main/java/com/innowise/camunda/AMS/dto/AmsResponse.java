package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record AmsResponse(String orderId,
                          String payeeAccountId,
                          String payeeAccountName,
                          String payeeLookupStatus,
                          String payeeQuoteLookupStatus) {
}
