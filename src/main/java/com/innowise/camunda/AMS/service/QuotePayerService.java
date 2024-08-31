package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import com.innowise.camunda.AMS.dto.PayerQuoteRequest;
import com.innowise.camunda.AMS.dto.PayerQuoteResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuotePayerService {

    public PayerQuoteResponse quotePayer(PayerQuoteRequest payerQuoteRequest) {

        final List<String> existedPayersAccounts =
                List.of("CM2110002000300277976315008", "AB2110002000300277976315009");

        if (existedPayersAccounts.contains(payerQuoteRequest.payerAccountId())) {
            return PayerQuoteResponse.builder()
                    .orderId(payerQuoteRequest.orderId())
                    .payerAccountId(payerQuoteRequest.payerAccountId())
                    .payerAccountName(null)
                    .payerQuoteLookupStatus("exist")
                    .build();
        } else {
            return PayerQuoteResponse.builder()
                    .payerQuoteLookupStatus("notFound")
                    .build();
        }
    }
}
