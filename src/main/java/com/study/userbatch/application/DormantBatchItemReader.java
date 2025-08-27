package com.study.userbatch.application;

import com.study.userbatch.batch.ItemReader;
import com.study.userbatch.customer.Customer;
import com.study.userbatch.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemReader implements ItemReader<Customer> {

    private int pageNo = 0;
    private final CustomerRepository customerRepository;

    public DormantBatchItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer read() {
        // 1. 유저 조회 (배치 학습용으로 사이즈 1로 조회해서 체크하도록 작성, 이렇게 하면 X)
        final PageRequest pageRequest = PageRequest.of(pageNo, 1, Sort.by("id").ascending());
        final Page<Customer> page = customerRepository.findAll(pageRequest);

        if (page.isEmpty()) {
            pageNo = 0;
            return null;
        } else {
            pageNo++;
            return page.getContent().get(0);
        }
    }
}
