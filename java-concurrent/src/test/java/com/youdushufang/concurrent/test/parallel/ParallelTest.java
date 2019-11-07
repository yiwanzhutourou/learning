package com.youdushufang.concurrent.test.parallel;

import com.youdushufang.concurrent.parallel.Parallel;
import com.youdushufang.concurrent.parallel.Pipeline;
import org.junit.jupiter.api.Test;

class ParallelTest {

    @Test
    void testPipeline() throws InterruptedException {
        Pipeline pipeline = new Pipeline();
        pipeline.doParallelWork(10);
        pipeline.doParallelWorkByRxJava(10);
    }

    @Test
    void testParallel() throws InterruptedException {
        Parallel parallel = new Parallel();
        parallel.checkAll();
    }
}
