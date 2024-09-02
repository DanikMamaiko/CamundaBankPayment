package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.dto.PayerConfirmationRequest;
import com.innowise.camunda.AMS.dto.PayerConfirmationResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PayerConfirmationService {

    private static final List<String> EXISTING_PAYERS_ACCOUNTS = List.of(
            "CM2110002000300277976315008",
            "AB2110002000300277976315009"
    );

    private static final int ACCOUNT_BALANCE = 10000; // Предположим, что баланс у всех аккаунтов одинаковый

    public PayerConfirmationResponse confirmPayer(PayerConfirmationRequest payerConfirmationRequest) {
        // Проверка плательщика

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
        if (!EXISTING_PAYERS_ACCOUNTS.contains(request.payerAccountId())) {
            return false;
        }
        if (request.amount() > ACCOUNT_BALANCE) {
            return false;
        }
        return true;
    }


}
