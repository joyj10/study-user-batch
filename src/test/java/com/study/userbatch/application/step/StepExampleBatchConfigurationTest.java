package com.study.userbatch.application.step;

import com.study.userbatch.batch.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StepExampleBatchConfigurationTest {

    @Autowired
    private Job stepExampleBatchJob;

    @Test
    void test() {
        stepExampleBatchJob.execute();
    }
}