package com.study.userbatch.application.dormant;

import com.study.userbatch.batch.ItemProcessor;
import com.study.userbatch.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DormantBatchItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        // 2. 휴면 계정 대상 추출 및 변환
        final boolean isDormantTarget = LocalDate.now()
                .minusDays(365)
                .isAfter(item.getLoginAt().toLocalDate());

        if (isDormantTarget) {
            item.setStatus(Customer.Status.DORMANT);
            return item;
        } else {
            return null;
        }
    }

}
