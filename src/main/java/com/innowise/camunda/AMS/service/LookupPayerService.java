package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.PayerLookupRequest;
import com.innowise.camunda.AMS.dto.PayerLookupResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LookupPayerService {
    public PayerLookupResponse lookupPayer(PayerLookupRequest payerLookupRequest) {

        // Список существующих счетов плательщиков
        final List<String> existedPayersAccounts =
                List.of("CM2110002000300277976315008", "AB2110002000300277976315009");

        // Проверка, существует ли указанный плательщик
        if (existedPayersAccounts.contains(payerLookupRequest.payerAccountId())) {
            return PayerLookupResponse.builder()
                    .orderId(payerLookupRequest.orderId())
                    .payerAccountId(payerLookupRequest.payerAccountId())
                    .payerAccountName(null)
                    .payerLookupStatus("exist")
                    .build();
        } else {
            return PayerLookupResponse.builder()
                    .payerLookupStatus("notFound")
                    .build();
        }
    }
}
