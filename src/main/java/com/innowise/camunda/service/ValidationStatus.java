package com.innowise.camunda.service;

import lombok.Builder;

@Builder
public record ValidationStatus(String status,
                               String message) {
}
