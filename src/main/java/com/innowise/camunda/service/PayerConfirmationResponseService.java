package com.innowise.camunda.service;

import org.springframework.stereotype.Component;

@Component
public class PayerConfirmationResponseService {

    public boolean isPayerConfirmationResponseRequired(boolean isRequired) {
        if (isRequired) {
            return true;
        } else {
            return false;
        }
    }
}
