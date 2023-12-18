package com.treemaswebapi.treemaswebapi.config;

import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.treemaswebapi.treemaswebapi.service.SchedulerService.CreateAbsenEntriesJob;

@Configuration
public class QuartzConfig {
     @Bean
    public JobDetailFactoryBean jobDetail(CreateAbsenEntriesJob job) {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CreateAbsenEntriesJob.class);
        factory.setDurability(true);
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setCronExpression("0 25 10 * * ?"); // Every day at 9:50 AM
        return factory;
    }
}
