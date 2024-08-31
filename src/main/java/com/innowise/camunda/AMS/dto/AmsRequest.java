package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record AmsRequest(String orderId,
                         String payment,
                         String payeeAccountId) {
}
