package com.etoak.job;

import com.etoak.service.EamilService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EmailJob extends QuartzJobBean {

    @Autowired
    EamilService eamilService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("执行邮件下发");
        eamilService.sendEmail();
    }

}
