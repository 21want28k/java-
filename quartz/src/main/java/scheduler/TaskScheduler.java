package scheduler;

import bean.MyJob;
import bean.MyTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {

    @Autowired
    private SchedulerFactoryBean factory;
    private Scheduler scheduler;
    private static final Logger LOGGER = LogManager.getLogger();
    private List<MyJob> jobs = new ArrayList<>();

    public List<MyJob> getJobs() {
        return jobs;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * 在应用程序成功启动的时候，通过Listener进行调度器的start操作
     */
    public void init() {
        this.scheduler = factory.getScheduler();
        try {
            this.scheduler.start();
            LOGGER.info("调度器被初始化");
            System.out.println(this);
        } catch (SchedulerException e) {
            LOGGER.error("初始化调度器失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 默认 持久化，可恢复
     *
     * @param job 自定义job
     */
    public void addJob(MyJob job, MyTrigger myTrigger) {
        try {
            List<MyTrigger> triggers = job.getTriggers();

            JobKey jobKey = getJobKey(job);
            TriggerKey triggerKey = getTriggerKey(myTrigger);
            Trigger newTrigger = buildTrigger(myTrigger);

            // 已经存在的话就在原来的基础上添加新的Trigger
            if (this.scheduler.checkExists(jobKey)) {
                JobDetail oldJobDetail = this.scheduler.getJobDetail(jobKey);

                // Job相同,trigger也相同就说明已经存在这样的一个任务了，不需要再重新调度了
                if (this.scheduler.checkExists(triggerKey)) {
                    return;
                }

                this.scheduler.scheduleJob(oldJobDetail, newTrigger);
                triggers.add(myTrigger);
                return;
            }

            Class jobClazz = Class.forName(job.getJobClassName());
            JobDetail jobDetail = buildJobDetail(job, jobClazz);
            this.scheduler.scheduleJob(jobDetail, newTrigger);
            triggers.add(myTrigger);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Job类没有找到: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SchedulerException e) {
            LOGGER.error("调度器异常" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private JobDetail buildJobDetail(MyJob job, Class jobClazz) {
        return JobBuilder.newJob(jobClazz)
                .withIdentity(job.getJobName(), job.getJobGroup())
                .storeDurably()
                .withDescription(job.getDescription())
                .requestRecovery()
                .build();
    }

    private Trigger buildTrigger(MyTrigger myTrigger) {
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(myTrigger.getCron());
        return TriggerBuilder.newTrigger()
                .withIdentity(myTrigger.getName(), myTrigger.getGroup())
                .withSchedule(builder)
                .startAt(myTrigger.getStartTime())
                .endAt(myTrigger.getEndTime())
                .build();
    }

    /**
     * 删除job
     *
     * @param job job
     */
    public void deleteJobDetail(MyJob job) {
        JobKey jobKey = getJobKey(job);
        try {
            // 务必先删除trigger再删除job，这个跟数据库的外键结构有关系
            this.scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private TriggerKey getTriggerKey(MyTrigger trigger) {
        return new TriggerKey(trigger.getName(), trigger.getGroup());
    }

    private JobKey getJobKey(MyJob job) {
        return new JobKey(job.getJobName(), job.getJobGroup());
    }

    /**
     * 给任务换上新的调度器
     *
     * @param job        旧job
     * @param newTrigger 新trigger
     */
    public void updateJobDetail(MyJob job, MyTrigger oldTrigger, MyTrigger newTrigger) {
        JobKey jobKey = getJobKey(job);
        try {
            if (!this.scheduler.checkExists(jobKey)) {
                return;
            }

            // 一个job 可以有多个trigger，多个job 不能对应一个trigger,一对多的关系
            List<? extends Trigger> triggers = this.scheduler.getTriggersOfJob(jobKey);
            if (triggers == null) {
                return;
            }

            for (Trigger trigger : triggers) {
                TriggerKey oldDbKey = trigger.getKey();
                TriggerKey oldKey = getTriggerKey(oldTrigger);
                if (oldDbKey.equals(oldKey)) {
                    Trigger t = buildTrigger(newTrigger);
                    // 会先删除旧的调度器使用新的
                    this.scheduler.rescheduleJob(oldDbKey, t);
                }
                // 如果job持久化了就不会删除job而只删除trigger
//                        this.scheduler.unscheduleJob(trigger.getKey());
            }
        } catch (SchedulerException e) {
            LOGGER.error("调度器异常");
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得所有的Job
     */
    public List<MyJob> getAll() {
        return this.jobs;
    }

    public void pauseJob(MyJob job) {
        JobKey jobKey = getJobKey(job);
        try {
            this.scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            LOGGER.error("暂停job失败 " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void resumeJob(MyJob job) {
        JobKey jobKey = getJobKey(job);
        try {
            this.scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            LOGGER.error("恢复任务失败" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}