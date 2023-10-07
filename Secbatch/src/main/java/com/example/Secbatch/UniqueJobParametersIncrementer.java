package com.example.Secbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

/*
 * Adds a unique timestamp-based parameter to the job parameters, ensuring that each execution creates a new job instance.
 */
public class UniqueJobParametersIncrementer implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(parameters);
        jobParametersBuilder.addLong("run.id", System.currentTimeMillis()); // Unique parameter
        return jobParametersBuilder.toJobParameters();
    }
}
