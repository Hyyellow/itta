package com.program.itta.common.config;

import com.program.itta.job.NewsJob;
import com.program.itta.job.ScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: itta
 * @description: Quartz配置文件
 * @author: Mr.Huang
 * @create: 2020-05-20 11:30
 **/
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail scheduleJobDetail() {
        return JobBuilder.newJob(ScheduleJob.class)
                .withIdentity("ScheduleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger scheduleJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(scheduleJobDetail())
                .withIdentity("scheduleJobService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail newsJobDetail(){
        return JobBuilder.newJob(NewsJob.class)
                .withIdentity("NewsJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger newsJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(newsJobDetail())
                .withIdentity("newsJobService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
