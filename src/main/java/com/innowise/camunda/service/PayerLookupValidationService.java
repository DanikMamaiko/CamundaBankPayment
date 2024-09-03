package com.innowise.camunda.service;

import java.util.Map;

public class PayerLookupValidationService {

    public ValidationStatus validatePayerLookupVariables(Map<String, Object> inputVariables) {
        String failedMessage = null;

        String [] requiredFields = {"orderId", "processPaymentId", "paymentId", "paymentType",
                "payerAccountId", "payeeAccountId", "currencyCode", "channel", "amount" , "remittanceDetails"};

        for (String field : requiredFields) {
            if (!inputVariables.containsKey(field) || inputVariables.get(field) == null) {
                failedMessage = "Validation failed: " + field + " is required and cannot be null.";
                break;
            }

            if (inputVariables.get(field) instanceof String && ((String) inputVariables.get(field)).isEmpty()) {
                failedMessage = "Validation failed: " + field + " cannot be empty.";
                break;
            }
        }

        try {
            final String orderId = (String) inputVariables.get("orderId");
            final Integer processPaymentId = (Integer) inputVariables.get("processPaymentId");
            final String paymentId = (String) inputVariables.get("paymentId");
            final String paymentType = (String) inputVariables.get("paymentType");
            final String payerAccountId = (String) inputVariables.get("payerAccountId");
            final String payeeAccountId = (String) inputVariables.get("payeeAccountId");
            final Integer currencyCode = (Integer) inputVariables.get("currencyCode");
            final Integer amount = (Integer) inputVariables.get("amount");
            final String remittanceDetails = (String) inputVariables.get("remittanceDetails");
            final String channel = (String) inputVariables.get("channel");


        } catch (ClassCastException e) {
            failedMessage = "Validation failed: Incorrect data type provided for one or more fields.";
        }

        ValidationStatus validationStatus = null;
        if (failedMessage != null) {
            validationStatus = new ValidationStatus("failed", failedMessage);
        } else {
            validationStatus = new ValidationStatus("success", "Validation passed");
        }

        System.out.println("VALIDATION: " + validationStatus.message());

        return validationStatus;
    }
}
