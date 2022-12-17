package com.example.batch.schedulers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpringBatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "dataJob")
    private Job job;

    @Scheduled(cron = "0 0/30 * 1/1 * ?")
    public void startJob() {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
