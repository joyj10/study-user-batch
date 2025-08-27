package com.study.userbatch.application;

import com.study.userbatch.batch.JobExecution;
import com.study.userbatch.batch.JobExecutionListener;
import com.study.userbatch.provider.EmailProvider;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchJobExecutionListener implements JobExecutionListener {
    private final EmailProvider emailProvider;

    public DormantBatchJobExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // no-op
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // 배치 완료 후 메일 발송
        emailProvider.send(
                "admin@test.com",
                "배치 완료 알림",
                "DormantBatchJob이 완료되었습니다. 상태: " + jobExecution.getStatus());
    }
}
