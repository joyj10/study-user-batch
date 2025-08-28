package com.study.userbatch.application.dormant;

import com.study.userbatch.batch.ItemProcessor;
import com.study.userbatch.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PreDormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) {

        final LocalDate targetDate = LocalDate.now()
                .minusDays(365)
                .plusDays(7);

        if (targetDate.equals(customer.getLoginAt().toLocalDate())) {
            return customer;
        } else {
            return null;
        }

    }

}