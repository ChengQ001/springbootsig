package com.chengq.chengq.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

public class SyncUser2Job extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("2.quartz执行动态定时任务: " + LocalDateTime.now().toLocalTime() + "线程:" + Thread.currentThread().getId());

    }
}
