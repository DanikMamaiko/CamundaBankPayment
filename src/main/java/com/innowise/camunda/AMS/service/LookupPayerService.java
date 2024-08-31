package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.dto.PayerLookupRequest;
import com.innowise.camunda.AMS.dto.PayerLookupResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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
                    .payerAccountId(payerLookupRequest.payerAccountId()) // Здесь возможно лучше использовать payerAccountId
                    .payerAccountName(null) // Установите имя плательщика, если доступно
                    .payerLookupStatus("exist") // Смена на payerLookupStatus
                    .build();
        } else {
            return PayerLookupResponse.builder()
                    .payerLookupStatus("notFound") // Смена на payerLookupStatus
                    .build();
        }
    }
}
