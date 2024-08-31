package com.innowise.camunda.AMS.service;

import com.innowise.camunda.AMS.dto.AmsRequest;
import com.innowise.camunda.AMS.dto.AmsResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QuoteUserService {

    public AmsResponse quoteUser(AmsRequest amsRequest) {

        final List<String> existedPayersAccounts =
            List.of("CM2110002000300277976315008", "AB2110002000300277976315009");

        if (existedPayersAccounts.contains(amsRequest.payeeAccountId())) {
            return AmsResponse.builder()
                .orderId(amsRequest.orderId())
                .payeeAccountId(amsRequest.payeeAccountId())
                .payeeAccountName(null)
                .payeeQuoteLookupStatus("exist")
                .build();
        } else {
            return AmsResponse.builder()
                .payeeQuoteLookupStatus("notFound")
                .build();
        }
    }
}
