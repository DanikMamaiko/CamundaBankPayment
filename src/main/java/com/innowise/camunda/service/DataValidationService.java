package com.innowise.camunda.service;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DataValidationService {

    public ValidationStatus validateInputVariables(Map<String, Object> inputVariables) {
        String failedMessage = null;

        String [] requiredFields = {"orderId", "processPaymentId", "paymentId", "paymentType",
            "payerAccountId", "payeeAccountId", "currencyName", "channel" };

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
            final String currencyName = (String) inputVariables.get("currencyName");
            final Integer amount = (Integer) inputVariables.get("amount");
            final String remittanceDetails = (String) inputVariables.get("remittanceDetails");
            final String channel = (String) inputVariables.get("channel");

            // Validate numeric fields
            if (amount == null || amount < 0) {
                failedMessage = "Validation failed: Amount should be a positive integer.";
            }

            // Example: Validate currencyCode if it is within a certain range (assuming range between 1-999)
            if (currencyCode == null || currencyCode < 1 || currencyCode > 999) {
                failedMessage = "Validation failed: CurrencyCode should be an integer between 1 and 999.";
            }

            // Validate IBAN format for payerAccountId and payeeAccountId (if applicable)
            if (payerAccountId != null && (payerAccountId.length() < 15 || payerAccountId.length() > 34)) {
                failedMessage = "Validation failed: PayerAccountId (IBAN) should be between 15 and 34 characters.";
            }

            if (payeeAccountId != null && (payeeAccountId.length() < 15 || payeeAccountId.length() > 34)) {
                failedMessage = "Validation failed: PayeeAccountId (IBAN) should be between 15 and 34 characters.";
            }

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

