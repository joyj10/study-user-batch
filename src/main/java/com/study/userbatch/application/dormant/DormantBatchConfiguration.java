package com.study.userbatch.application.dormant;

import com.study.userbatch.batch.Job;
import com.study.userbatch.batch.Step;
import com.study.userbatch.batch.StepJobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(Step preDormantBatchStep,
                               Step dormantBatchStep,
                               DormantBatchJobExecutionListener listener) {
        return new StepJobBuilder()
                .start(preDormantBatchStep)
                .next(dormantBatchStep)
                .build();
    }

    @Bean
    public Step preDormantBatchStep(AllCustomerItemReader itemReader,
                                    PreDormantBatchItemProcessor itemProcessor,
                                    PreDormantBatchItemWriter itemWriter) {
        return Step.builder()
                .itemReader(itemReader)
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .build();
    }

    @Bean
    public Step dormantBatchStep(AllCustomerItemReader itemReader,
                                 DormantBatchItemProcessor itemProcessor,
                                 DormantBatchItemWriter itemWriter) {
        return Step.builder()
                .itemReader(itemReader)
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .build();
    }

    // 휴면전환 예정 1주일전인 사람에게 이메일을 발송한다!
}