package org.chimerax.prometheus.configuration;

import org.chimerax.prometheus.job.AuthorizationCodeCleanUpJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:44 PM
 */

@Configuration
public class QuartzConfiguration {

    @Bean("authorization-code-clean-up-job-detail")
    public JobDetail jobDetail() {
        return JobBuilder.newJob()
                .ofType(AuthorizationCodeCleanUpJob.class)
                .storeDurably()
                .withIdentity("code-deletion-job")
                .withDescription("Should delete the authentication codes that are not used in 24h")
                .build();
    }

    @Bean("authorization-code-clean-up-job-trigger")
    @DependsOn("authorization-code-clean-up-job-detail")
    public Trigger trigger(@Qualifier("authorization-code-clean-up-job-detail") final JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("authorization-code-clean-up-job-trigger")
                .withDescription("Trigger for the authentication codes clean up job")
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever())
                // .startNow()
                .build();
    }
}
