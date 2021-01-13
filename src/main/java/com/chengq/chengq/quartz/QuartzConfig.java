package com.chengq.chengq.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    private static String JOB_GROUP_NAME = "PJB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "PJB_TRIGGERGROUP_NAME";

    /**
     * 定时任务1：
     * 同步用户信息Job（任务详情）
     */
    @Bean
    public JobDetail syncUserJobDetail()
    {
        JobDetail jobDetail = JobBuilder.newJob(SyncUserJob.class)
                .withIdentity("syncUserJobDetail",JOB_GROUP_NAME)
                .usingJobData("userName", "pan_junbiao的博客") //设置参数（键值对）
                .usingJobData("blogUrl","https://blog.csdn.net/pan_junbiao")
                .usingJobData("blogRemark","您好，欢迎访问 pan_junbiao的博客")
                .storeDurably() //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
        return jobDetail;
    }

    @Bean
    public JobDetail syncUserJobDetail2()
    {
        JobDetail jobDetail = JobBuilder.newJob(SyncUser2Job.class)
//                .withIdentity("syncUserJobDetail",JOB_GROUP_NAME+2)
//                .usingJobData("userName", "pan_junbiao的博客") //设置参数（键值对）
//                .usingJobData("blogUrl","https://blog.csdn.net/pan_junbiao")
//                .usingJobData("blogRemark","您好，欢迎访问 pan_junbiao的博客")
                .storeDurably() //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
        return jobDetail;
    }
    @Bean
    public Trigger syncUserJobTrigger2()
    {

        //每隔5秒执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");

        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(syncUserJobDetail2())//关联上述的JobDetail
//                .withIdentity("syncUserJobTrigger",TRIGGER_GROUP_NAME+2)//给Trigger起个名字
                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();
        return trigger;
    }
    /**
     * 定时任务1：
     * 同步用户信息Job（触发器）
     */
    @Bean
    public Trigger syncUserJobTrigger()
    {

        //每隔5秒执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");

        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(syncUserJobDetail())//关联上述的JobDetail
                .withIdentity("syncUserJobTrigger",TRIGGER_GROUP_NAME)//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }

}
