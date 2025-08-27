package com.study.userbatch.application;

import com.study.userbatch.batch.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(DormantBatchTasklet dormantBatchTasklet,
                               DormantBatchJobExecutionListener dormantBatchJobExecutionListener) {
        return new Job(dormantBatchTasklet, dormantBatchJobExecutionListener);
    }
}
