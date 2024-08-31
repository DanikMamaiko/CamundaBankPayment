package com.innowise.camunda.AMS.service;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocalQuoteService {

    public String isChannelExist(String channel) {
        if(List.of("MK4352").contains(channel)) {
            return "exist";
        } else {
            return "notFound";
        }
    }
}
