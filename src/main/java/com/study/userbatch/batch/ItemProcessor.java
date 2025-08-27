package com.study.userbatch.batch;

public interface ItemProcessor<I, O> {
    O process(I item);
}
