package com.study.userbatch.application.step;

import com.study.userbatch.batch.Job;
import com.study.userbatch.batch.Step;
import com.study.userbatch.batch.StepJobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepExampleBatchConfiguration {

    @Bean
    public Job stepExampleBatchJob(Step step1, Step step2, Step step3) {
        return new StepJobBuilder()
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    @Bean
    public Step step1() {
        return new Step(() -> System.out.println("Step 1 was executed"));
    }

    @Bean
    public Step step2() {
        return new Step(() -> System.out.println("Step 2 was executed"));
    }

    @Bean
    public Step step3() {
        return new Step(() -> System.out.println("Step 3 was executed"));
    }
}
