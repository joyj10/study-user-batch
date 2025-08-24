package com.study.userbatch.provider;

import lombok.extern.slf4j.Slf4j;

public interface EmailProvider {
    void send(String emailAddress, String title, String body);

    @Slf4j
    class Fake implements EmailProvider {

        @Override
        public void send(String emailAddress, String title, String body) {
            log.info("Send email to {}, title: {}, body: {}", emailAddress, title, body);
        }
    }
}
