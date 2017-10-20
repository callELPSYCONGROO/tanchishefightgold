package com.wuhenjian.tanchishe_fight_gold.task;

import com.wuhenjian.tanchishe_fight_gold.task.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author SwordNoTrace
 * @date 2017/8/22 19:46
 */
public class MyTask {

    public static void main(String[] arg) {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();//创建Scheduler对象
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)//添加工作
                                        .withIdentity("gold_job", "gj1")//设置作业名称和作业组名称
                                        .build();//创建对象
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("gold_trigger", "gt1")//设置触发器名称和触发器组名称
                                        .startNow()//触发器开始时间，立即执行
                                        .withSchedule(
                                                CronScheduleBuilder.cronSchedule("* 0/5 * * * ? *")//设置触发时间
                                        )
                                        .build();//构建触发器
        try {
            scheduler.scheduleJob(jobDetail, trigger);//添加工作和触发器
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
