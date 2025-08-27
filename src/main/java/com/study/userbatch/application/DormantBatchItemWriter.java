package com.study.userbatch.application;

import com.study.userbatch.batch.ItemWriter;
import com.study.userbatch.customer.Customer;
import com.study.userbatch.customer.CustomerRepository;
import com.study.userbatch.provider.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchItemWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void write(Customer item) {
        // 3. 휴면 계정으로 상태 변경
        customerRepository.save(item);

        // 4. 휴면 전환 안내 이메일 발송
        emailProvider.send(item.getEmail(), "휴면전환 안내메입니다.", "내용");
    }
}
