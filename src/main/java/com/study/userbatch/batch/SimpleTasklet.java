package com.study.userbatch.batch;

//@Component
public class SimpleTasklet<I, O> implements Tasklet {
    private final ItemReader<I> iItemReader;
    private final ItemProcessor<I, O> iItemProcessor;
    private final ItemWriter<O> oItemWriter;

    public SimpleTasklet(ItemReader<I> iItemReader, ItemProcessor<I, O> iItemProcessor, ItemWriter<O> oItemWriter) {
        this.iItemReader = iItemReader;
        this.iItemProcessor = iItemProcessor;
        this.oItemWriter = oItemWriter;
    }

    @Override
    public void execute() {
        while (true) {
            final I read = iItemReader.read();
            if (read == null) break;

            final O process = iItemProcessor.process(read);
            if (process == null) continue;

            oItemWriter.write(process);
        }
    }
}
