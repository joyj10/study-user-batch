package com.study.userbatch.batch;

import com.study.userbatch.customer.Customer;
import com.study.userbatch.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class JobTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Job dormantBatchJob;


    @Test
    @DisplayName("로그인 1년 경과한 고객이 세명, 일년 이내 로그인 고객 다섯명이면, 3명 고객 휴면전환대상")
    void test1() {
        // given
        saveCustomer(366);
        saveCustomer(366);
        saveCustomer(366);

        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);

        // when
        JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        assertThat(dormantCount).isEqualTo(3);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }


    
    @Test
    @DisplayName("고객 10명 모두 다 휴면전환대상이면(1년 경과), 휴면전환대상은 10명")
    void test2() {
        // given
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);

        // when
        final JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        Assertions.assertThat(dormantCount).isEqualTo(10);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName("고객이 없는 경우에도 배치 정상동작 해야 함")
    void test3() {
        // given

        // when
        final JobExecution result = dormantBatchJob.execute();

        // then
        final long dormantCount = customerRepository.findAll()
                .stream()
                .filter(it -> it.getStatus() == Customer.Status.DORMANT)
                .count();

        Assertions.assertThat(dormantCount).isZero();
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("배치가 실패하면 BatchStatus FAILED 반환 함")
    void test4() {
        // given
        final Job batchJob = new TaskletJob(null);

        // when
        final JobExecution result = batchJob.execute();
    
        // then
        assertThat(result.getStatus()).isEqualTo(BatchStatus.FAILED);
    }

    @Test
    @DisplayName("358일전에 로그인한 고객에게 휴면계정 예정자라고 메일을 발송해야한다.")
    void test5() {

        // given
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(35);
        saveCustomer(35);

        // when
        // then
        dormantBatchJob.execute();

    }

    private void saveCustomer(long loginMinusDays) {
        final String uuid = UUID.randomUUID().toString();
        final Customer test = new Customer(uuid, uuid + "@test.com");
        test.setLoginAt(LocalDateTime.now().minusDays(loginMinusDays));
        customerRepository.save(test);
    }
}