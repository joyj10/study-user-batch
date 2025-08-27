package com.study.userbatch.application;

import com.study.userbatch.batch.Tasklet;
import com.study.userbatch.customer.Customer;
import com.study.userbatch.customer.CustomerRepository;
import com.study.userbatch.provider.EmailProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DormantBatchTasklet implements Tasklet {
    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchTasklet(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void execute() {
        int pageNo = 0;
        while (true) {
            // 1. 유저 조회 (배치 학습용으로 사이즈 1로 조회해서 체크하도록 작성, 이렇게 하면 X)
            final PageRequest pageRequest = PageRequest.of(pageNo, 1, Sort.by("id").ascending());
            final Page<Customer> page = customerRepository.findAll(pageRequest);

            final Customer customer;
            if (page.isEmpty()) {
                break;
            } else {
                pageNo++;
                customer = page.getContent().get(0);
            }

            // 2. 휴면 계정 대상 추출 및 변환
            final boolean isDormantTarget = LocalDate.now()
                    .minusDays(365)
                    .isAfter(customer.getLoginAt().toLocalDate());

            if (isDormantTarget) {
                customer.setStatus(Customer.Status.DORMANT);
            } else {
                continue;
            }

            // 3. 휴면 계정으로 상태 변경
            customerRepository.save(customer);

            // 4. 휴면 전환 안내 이메일 발송
            emailProvider.send(customer.getEmail(), "휴면전환 안내메입니다.", "내용");
        }
    }
}
