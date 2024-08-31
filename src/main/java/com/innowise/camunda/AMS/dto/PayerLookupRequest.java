package com.innowise.camunda.AMS.dto;

import lombok.Builder;

@Builder
public record PayerLookupRequest(String orderId,
                                 String payment,
                                 String payerAccountId){

}

