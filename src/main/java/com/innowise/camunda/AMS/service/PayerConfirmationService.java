package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.PayerConfirmationRequest;
import com.innowise.camunda.AMS.dto.PayerConfirmationResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PayerConfirmationService {

    private static final List<String> EXISTING_PAYERS_ACCOUNTS = List.of(
            "CM2110002000300277976315008",
            "AB2110002000300277976315009"
    );

    public PayerConfirmationResponse confirmPayer(PayerConfirmationRequest payerConfirmationRequest) {
        boolean isConfirmed = confirmPayerLogic(payerConfirmationRequest);

        if(isConfirmed){
        return PayerConfirmationResponse.builder()
                .orderId(payerConfirmationRequest.orderId())
                .payerAccountId(payerConfirmationRequest.payerAccountId())
                .payerConfirmationStatus("confirmed")
                .build();
        }
        else {
            return  PayerConfirmationResponse.builder()
                    .payerConfirmationStatus("rejected")
                    .build();
        }
    }

    private boolean confirmPayerLogic(PayerConfirmationRequest request) {
        if(EXISTING_PAYERS_ACCOUNTS.contains(request.payerAccountId())){
            return true;
        } else {
            return false;
        }
    }
}
