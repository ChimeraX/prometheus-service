package org.chimerax.prometheus.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:44 PM
 */
@Component
public class AuthorizationCodeCleanUpJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
